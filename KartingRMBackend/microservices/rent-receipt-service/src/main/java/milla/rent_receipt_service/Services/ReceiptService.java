package milla.rent_receipt_service.Services;

import milla.rent_receipt_service.Entities.ReceiptEntity;
import milla.rent_receipt_service.Model.Fee_Type;
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
    //Get de la lista de receipts de una sola renta
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
    //Funcion que calcula los distintos campos de un recibo creado
    public ReceiptEntity createFullReceipt(ReceiptEntity receipt){
        receipt.setBase_tariff(getFeePriceByReceiptId(receipt.getReceipt_id()));
        receipt.setSize_discount(getPeopleDiscountPriceByReceiptId(receipt.getReceipt_id()));

    }

}
