package milla.rent_receipt_service.Services;

import milla.rent_receipt_service.Entities.ReceiptEntity;
import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.Frequency_Discount;
import milla.rent_receipt_service.Repositories.ReceiptRepository;
import milla.rent_receipt_service.Repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ReceiptService {
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    RentService rentService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private RentRepository rentRepository;

    //Getters
    public List<ReceiptEntity> getAll(){
        return receiptRepository.findAll();
    }
    public ReceiptEntity getReceiptById(int id){
        return receiptRepository.findById(id).orElse(null);
    }
    //Get de la lista de receipts de una sola renta por su id
    public List<ReceiptEntity> getReceiptsByRentId(int id){
        return receiptRepository.getReceiptsByRent_id(id);
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
            int rentId = receipt.getRent_id();
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
            int rentId = receipt.getRent_id();
            return rentService.getPeopleDiscountByRentId(rentId).getDiscount();
        }
    }
    //Funcion que devuelve el descuento por frecuencia o por dia especial
    //FALTA LOGICA PARA DIA ESPECIAL
    public BigDecimal getSpecialDiscountPriceByReceiptId(int id){
        ReceiptEntity receipt = receiptRepository.findById(id).orElse(null);
        if(receipt == null){
            return null;
        }
        else {
            int rentId = receipt.getRent_id();
            int peopleAmount = rentService.getById(rentId).getPeople_number();
            Frequency_Discount frequencyDiscount = restTemplate.getForObject("http://frequency_discount/frequencyDiscount//getFrequencyByNumber/" + peopleAmount, Frequency_Discount.class);
            if (frequencyDiscount == null){
                return null;
            }
            else {
                return frequencyDiscount.getDiscount();
            }
        }
    }

    //Funcion que crea un recibo sin completar (usando save)
    public ReceiptEntity saveIncomplete(ReceiptEntity receiptEntity){
        return receiptRepository.saveIncomplete(receiptEntity);
    }
    //Funcion que calcula los distintos campos de un recibo creado
    public ReceiptEntity createFullReceipt(ReceiptEntity receipt){
        int receiptId = receipt.getReceipt_id();
        BigDecimal base_tariff = getFeePriceByReceiptId(receiptId);
        BigDecimal people_discount = getPeopleDiscountPriceByReceiptId(receiptId);
        BigDecimal special_discount = getSpecialDiscountPriceByReceiptId(receiptId);
        BigDecimal aggregated_price = base_tariff.multiply(people_discount).multiply(special_discount);
        BigDecimal iva_price = aggregated_price.multiply(BigDecimal.valueOf(0.21));
        BigDecimal final_price = aggregated_price.add(iva_price);
       //Guardar los datos calculados
       receipt.setBase_tariff(base_tariff);
       receipt.setSize_discount(people_discount);
       receipt.setSpecial_discount(special_discount);
       receipt.setAggregated_price(aggregated_price);
       receipt.setIva_price(iva_price);
       receipt.setFinal_price(final_price);
       return receiptRepository.save(receipt);
    }
    //Obtener el total_price de una renta, segun su id, y guardar la renta
    public RentEntity saveTotalPrice(int id){
        RentEntity rent = rentRepository.findById(id).orElse(null);
        if (rent == null) {return null;}
        else {
            int rentId = rent.getRent_id();
            List<ReceiptEntity> receiptList = receiptRepository.getReceiptsByRent_id(rentId);
            if(receiptList == null){
                return null;
            }
            else {
                BigDecimal total_price = BigDecimal.ZERO;
                for (ReceiptEntity receipt : receiptList) {
                    if (receipt.getFinal_price() != null) {
                        total_price = total_price.add(receipt.getFinal_price());
                    }
                }
                rent.setTotal_price(total_price);
                return rentRepository.save(rent);
            }
        }
    }
}
