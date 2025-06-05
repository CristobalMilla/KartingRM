package milla.rent_receipt_service.Services;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.People_Discount;
import milla.rent_receipt_service.Repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RestTemplate restTemplate;

    //Getters
    public List<RentEntity> getAll(){
        return rentRepository.findAll();
    }
    public RentEntity getById(int id){
        return rentRepository.findById(id).orElse(null);
    }
    //Get la lista de rentas de un solo cliente
    public List<RentEntity> getRentsByClientName(String clientName){
        return rentRepository.findByMainClient(clientName);
    }
    //Get la lista de rentas de una sola fecha
    public List<RentEntity> getRentsByRentDate(LocalDate rentDate){
        return rentRepository.findByRentDate(rentDate);
    }
    //Save
    public RentEntity save(RentEntity rent){
        return rentRepository.save(rent);
    }
    //Update
    public RentEntity update(RentEntity rent){
        return rentRepository.save(rent);
    }
    //Obtener el fee type de la renta segun su id
    public Fee_Type getFeeTypeByRentId(int id){
        RentEntity rent = rentRepository.findById(id).orElse(null);
        if (rent == null) {return null;}
        else {
            int feeTypeId = rent.getFee_type_id();
            return restTemplate.getForObject("http://fee_type_service/feeTypes/" + feeTypeId, Fee_Type.class);
        }
    }
    //Obtener el people_discount de la renta segun su id
    public People_Discount getPeopleDiscountByRentId(int id){
        RentEntity rent = rentRepository.findById(id).orElse(null);
        if (rent == null) {return null;}
        else {
            int peopleAmount = rent.getPeople_number();
            return restTemplate.getForObject("http://people_discount_service/peopleDiscount/getPeopleDiscountByAmount/" + peopleAmount, People_Discount.class);
        }
    }
    //Obtener la duracion de la renta segun su fee_type, segun el id de la renta
    public int getDurationByRentId(int id){
        RentEntity rent = rentRepository.findById(id).orElse(null);
        if (rent == null) {return 0;}
        else {
            Fee_Type feeType = getFeeTypeByRentId(rent.getFee_type_id());
            if (feeType == null) {return 0;}
            else {
                return feeType.getDuration();
            }
        }
    }
    //Obtener todas las rentas entre 2 fechas
    public List<RentEntity> getRentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return rentRepository.findRentsBetweenDates(startDate, endDate);
    }
    //Obtener el total de ventas de un mes
    public BigDecimal calculateTotalPriceForMonth(String month) {
        // Parse the month to YearMonth
        YearMonth yearMonth = YearMonth.parse(month); // e.g., "2025-01"

        // Define the start and end dates for the month
        LocalDate startDate = yearMonth.atDay(1); // First day of the month
        LocalDate endDate = yearMonth.atEndOfMonth(); // Last day of the month

        // Fetch rents for the month
        List<RentEntity> rents = rentRepository.findByRentDateBetween(
                startDate,
                endDate
        );

        // Calculate the total price
        return rents.stream()
                .map(RentEntity::getTotal_price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    //Booleano que checkea si un dia es fin de semana o festivo
    private boolean isWeekendOrHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || isHoliday(date);
    }
    //Booleano que checkea si un dia es festivo, se puede customizar
    private boolean isHoliday(LocalDate date) {
        //Se aplicara el descuento a traves de ReceiptService, por lo que este metodo por si no es necesario
        return false;
    }

    //Funciones para calendar
    //Funcion que genera slots de tiempo para un dia, con una hora de apertura, cierre, y tiempo de intervalo
    private List<LocalTime> generateTimeSlots(LocalTime start, LocalTime end, int intervalMinutes) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = start;
        while (current.isBefore(end)) {
            slots.add(current);
            current = current.plusMinutes(intervalMinutes);
        }
        return slots;
    }
    //Funcion que obtiene los slots de tiempo abiertos para una nueva renta
    public List<LocalTime> getOpenTimeSlots(LocalDate rentDate, int intervalMinutes) {
        // Determine opening and closing hours
        LocalTime openingTime = isWeekendOrHoliday(rentDate) ? LocalTime.of(10, 0) : LocalTime.of(14, 0);
        LocalTime closingTime = LocalTime.of(22, 0);

        // Generate 15-minute slots
        List<LocalTime> allSlots = generateTimeSlots(openingTime, closingTime, intervalMinutes);

        // Fetch existing rents for the day
        List<RentEntity> rents = getRentsByRentDate(rentDate);

        // Get unavailable slots
        Set<LocalTime> unavailableSlots = new HashSet<>();
        for (RentEntity rent : rents) {
            // Get rent duration from fee_type
            Integer duration = restTemplate.getForObject(
                    "http://fee_type_service/getDurationById/" + rent.getFee_type_id(),
                    Integer.class
            );
            if (duration == null) continue; // Skip if no duration found

            // Calculate rent occupied slots
            LocalTime rentStart = rent.getRent_time();
            LocalTime rentEnd = rentStart.plusMinutes(duration);
            unavailableSlots.addAll(generateTimeSlots(rentStart, rentEnd, intervalMinutes));
        }

        // Filter available slots
        return allSlots.stream()
                .filter(slot -> !unavailableSlots.contains(slot))
                .collect(Collectors.toList());
    }
    //Funcion que filtra la lista de slots para solo devolver slots de corrido que puedan contener la duracion de la nueva renta
    public List<LocalTime> filterSlotsForDuration(List<LocalTime> openSlots, int requiredDuration, int intervalMinutes) {
        int requiredSlots = requiredDuration / intervalMinutes;

        // Ensure there are enough consecutive slots
        List<LocalTime> filteredSlots = new ArrayList<>();
        for (int i = 0; i <= openSlots.size() - requiredSlots; i++) {
            boolean canFit = true;
            for (int j = 0; j < requiredSlots; j++) {
                if (!openSlots.get(i + j).equals(openSlots.get(i).plusMinutes((long) j * intervalMinutes))) {
                    canFit = false;
                    break;
                }
            }
            if (canFit) {
                filteredSlots.add(openSlots.get(i));
            }
        }

        return filteredSlots;
    }
    //Funcion principal que obtiene la lista de slots segun una duracion y dia dada por la solicitud de renta nueva
    public List<LocalTime> getAvailableTimeSlots(LocalDate rentDate, int rentDuration) {
        // Step 1: Fetch open slots for the day
        List<LocalTime> openSlots = getOpenTimeSlots(rentDate, 15);

        // Step 2: Filter slots based on rent duration
        return filterSlotsForDuration(openSlots, rentDuration, 15); // Interval size is 15 minutes
    }

}
