package milla.rent_receipt_service.Services;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.People_Discount;
import milla.rent_receipt_service.Repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    RestTemplate restTemplate;

    //Getters
    public List<RentEntity> getAll(){
        return rentRepository.findAll();
    }
    public RentEntity getById(int id){
        return rentRepository.findById(id).orElse(null);
    }
    //Get la lista de rentas de un solo cliente
    public List<RentEntity> getRentsByClientName(String clientName){
        return rentRepository.findByMain_client(clientName);
    }
    //Get la lista de rentas de una sola fecha
    public List<RentEntity> getRentsByRentDate(Date rentDate){
        return rentRepository.findByRent_date(rentDate);
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
    public List<RentEntity> getRentsBetweenDates(Date startDate, Date endDate) {
        return rentRepository.findRentsBetweenDates(startDate, endDate);
    }

}
