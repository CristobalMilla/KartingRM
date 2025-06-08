package milla.rent_receipt_service.Services;

import jakarta.transaction.Transactional;
import milla.rent_receipt_service.Entities.ReceiptEntity;
import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.Frequency_Discount;
import milla.rent_receipt_service.Repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private RentService rentService;
    @Autowired
    private RestTemplate restTemplate;

    //Getters
    public List<ReceiptEntity> getAll(){
        return receiptRepository.findAll();
    }
    public ReceiptEntity getReceiptById(int id){
        return receiptRepository.findById(id).orElse(null);
    }
    //Get de la lista de receipts de una sola renta por su id
    public List<ReceiptEntity> getReceiptsByRentId(int id){
        return receiptRepository.getReceiptsByRentId(id);
    }
    //Save
    public ReceiptEntity save(ReceiptEntity receiptEntity){
        return receiptRepository.save(receiptEntity);
    }
    //Update
    public ReceiptEntity update(ReceiptEntity receiptEntity){
        return receiptRepository.save(receiptEntity);
    }

    //Get para obtener la tarifa correspondiente al recibo, segun su renta
    public BigDecimal getFeePriceByReceiptId(int id){
        ReceiptEntity receipt = receiptRepository.findById(id).orElse(null);
        if(receipt == null){
            return null;
        }
        else {
            int rentId = receipt.getRentId();
            Fee_Type feeType = rentService.getFeeTypeByRentId(rentId);
            return feeType.getPrice();
        }
    }
    //Get para obtener el people_discount price del recibo, segun su renta
    public BigDecimal getPeopleDiscountPriceByReceiptId(int id){
        ReceiptEntity receipt = receiptRepository.findById(id).orElse(null);
        if(receipt == null){
            return null;
        }
        else {
            int rentId = receipt.getRentId();
            return rentService.getPeopleDiscountByRentId(rentId).getDiscount();
        }
    }

    //Seccion para crear renta y recibo
    //Funciones principales para crear una renta y recibos segun datos iniciales mandados por frontend
    //Estos datos son una renta completa excepto por precio final, y una lista de sub_clientes como strings
    //Este calculo se realiza como transaccion atomica

    //Funcion que obtiene el valor del la tarifa base
    public BigDecimal calculateBaseTariff(int rentId) {
        return rentService.getFeeTypeByRentId(rentId).getPrice();
    }
    //Funcion que obtiene el descuento de gente segun renta
    public BigDecimal calculatePeopleDiscount(int rentId) {
        return rentService.getPeopleDiscountByRentId(rentId).getDiscount();
    }
    //Funcion que calcula el descuento especial
    //Se calculan y obtienen, si existen, descuento por frecuencia, cumpleaños (Se asume del cliente principal, se puede cambiar) y por festivo
    //Se utiliza el numero menor obtenido, osea, el mejor descuento entre los 3
    //Se utiliza LocalDate.now(), por lo que se obtiene la fecha de hoy desde la maquina. Esto puede generar discrepancias dependiendo de desde donde se este corriendo la aplicacion
    public BigDecimal calculateSpecialDiscount(int rentId) {
        RentEntity rent = rentService.getById(rentId);
        int peopleAmount = rent.getPeople_number();

        // Get frequency discount
        Frequency_Discount frequencyDiscount = restTemplate.getForObject(
                "http://FREQUENCY-DISCOUNT-SERVICE/frequencyDiscount/getFrequencyByNumber/" + peopleAmount,
                Frequency_Discount.class
        );
        BigDecimal f_discount;
        if (frequencyDiscount == null) {
            f_discount = BigDecimal.ONE;
        }
        else {
            f_discount = frequencyDiscount.getDiscount();
        }

        LocalDate currentDate = LocalDate.now();

        // Check if it's the client's birthday and get birthday discount
        BigDecimal b_discount = BigDecimal.ONE;
        Boolean isItsBirthday = restTemplate.getForObject(
                "http://SPECIAL-DAY-FEE-SERVICE/specialDay/birthday/isItBirthday?name={name}&date={date}",
                Boolean.class, rent.getMainClient(), currentDate);

        if (Boolean.TRUE.equals(isItsBirthday)) {
            BigDecimal birthdayDiscount = restTemplate.getForObject(
                    "http://SPECIAL-DAY-FEE-SERVICE/specialDay/birthday/discount?name={name}",
                    BigDecimal.class, rent.getMainClient());
            if (birthdayDiscount != null) {
                b_discount = birthdayDiscount;
            }
        }

        // Get holiday discount
        BigDecimal h_discount = restTemplate.getForObject(
                "http://SPECIAL-DAY-FEE-SERVICE/specialDay/holiday/discount?date={date}",
                BigDecimal.class, currentDate);

        if (h_discount == null) {
            h_discount = BigDecimal.ONE;
        }

        // Return the minimum discount (maximum benefit for the client)
        return f_discount.min(h_discount).min(b_discount);
    }
    //Funcion que calcula los distintos campos de un recibo creado
    //Se asume que descuento por cantidad de personas y el especial son multiplicativos
    private ReceiptEntity createFullReceipt(ReceiptEntity receipt) {
        int rentId = receipt.getRentId();
        //Calculo de los atributos por separados
        BigDecimal baseTariff = calculateBaseTariff(rentId);
        BigDecimal peopleDiscount = calculatePeopleDiscount(rentId);
        BigDecimal specialDiscount = calculateSpecialDiscount(rentId);
        BigDecimal aggregatedPrice = baseTariff
                .multiply(peopleDiscount)
                .multiply(specialDiscount);
        BigDecimal ivaPrice = aggregatedPrice.multiply(BigDecimal.valueOf(0.21));
        BigDecimal finalPrice = aggregatedPrice.add(ivaPrice);
        //Set de los atributos calculados
        receipt.setBase_tariff(baseTariff);
        receipt.setSize_discount(peopleDiscount);
        receipt.setSpecial_discount(specialDiscount);
        receipt.setAggregated_price(aggregatedPrice);
        receipt.setIva_price(ivaPrice);
        receipt.setFinal_price(finalPrice);
        //Return
        return receiptRepository.save(receipt);
    }
    //Funcion principal que, con una renta incompleta (sin precio final) y lista de clientes
    //Guarda, transaccional y atomicamente, una renta con sus respectivos recibos
    //Al ser transaccional y con muchos calculos, es posible la perdida de datos, tener en cuenta
    @Transactional
    public RentEntity saveRentWithReceipts(RentEntity rent, List<String> subClients) {
        // Valida datos de renta
        validateRentData(rent);
        // Guarda la renta incompleta
        RentEntity savedRent = rentService.save(rent);
        // Genera recibos para cada sub cliente y los guarda en la base de datos
        List<ReceiptEntity> receiptList = subClients.stream()
                .map(subClient -> {
                    //Para cada recibo, se crea uno vacio, se setea el id de renta, se setea el subcliente
                    //Y se calcula y guarda el recibo con estos valores
                    ReceiptEntity receipt = new ReceiptEntity();
                    receipt.setRentId(savedRent.getRent_id());
                    receipt.setSub_client(subClient);
                    return createFullReceipt(receipt); // Calculate and save receipt
                })
                .toList();
        //Calcula el precio total de la renta, segun la lista de recibos
        BigDecimal totalPrice = receiptList.stream()
                .map(ReceiptEntity::getFinal_price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        savedRent.setTotal_price(totalPrice);
        //Se actualiza la renta con el precio total calculado
        return rentService.update(savedRent);
    }
    //Validar datos validos de renta, modificables a gusto
    private void validateRentData(RentEntity rent) {
        if (rent.getPeople_number() < 1 || rent.getPeople_number() > 15) {
            throw new IllegalArgumentException("People number must be between 1 and 15");
        }
        if (rent.getFee_type_id() == 0 || rent.getMainClient() == null) {
            throw new IllegalArgumentException("Mandatory fields are missing");
        }
    }
}