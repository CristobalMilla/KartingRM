package milla.rent_receipt_service.Controllers;

import milla.rent_receipt_service.DTO.RentRequestDTO;
import milla.rent_receipt_service.Entities.ReceiptEntity;
import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    //Getters
    @GetMapping("/all")
    public ResponseEntity<List<ReceiptEntity>> getAllReceipts() {
        List<ReceiptEntity> receiptList = receiptService.getAll();
        if (receiptList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(receiptList);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReceiptEntity> getReceiptById(@PathVariable("id") int id) {
        ReceiptEntity receipt = receiptService.getReceiptById(id);
        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(receipt);
        }
    }
    //Save completo
    @PostMapping("/saveComplete")
    public ResponseEntity<ReceiptEntity> saveComplete(@RequestBody ReceiptEntity receipt) {
        ReceiptEntity savedReceipt = receiptService.save(receipt);
        if (savedReceipt == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedReceipt);
    }
    //Update
    @PutMapping("/update")
    public ResponseEntity<ReceiptEntity> updateReceipt(@RequestBody ReceiptEntity receipt) {
        ReceiptEntity updatedReceipt = receiptService.update(receipt);
        if (updatedReceipt == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedReceipt);
    }
    //Get para obtener la tarifa correspondiente al recibo, segun su renta
    @GetMapping("/feePriceByReceiptId/{id}")
    public ResponseEntity<BigDecimal> getFeePriceByReceiptId(@PathVariable("id") int receiptId) {
        BigDecimal feePrice = receiptService.getFeePriceByReceiptId(receiptId);
        if (feePrice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feePrice);
    }
    //Get para obtener el people_discount price del recibo, segun su renta
    @GetMapping("/peopleDiscountPriceByReceiptId/{id}")
    public ResponseEntity<BigDecimal> getPeopleDiscountPriceByReceiptId(@PathVariable("id") int receiptId) {
        BigDecimal peopleDiscountPrice = receiptService.getPeopleDiscountPriceByReceiptId(receiptId);
        if (peopleDiscountPrice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(peopleDiscountPrice);
    }

    //Funcion que calcula los distintos campos de un recibo creado, y la crea en la base de datos
    @PostMapping("/calcAndSave")
    public ResponseEntity<RentEntity> saveCalc(@RequestBody RentRequestDTO request) {
        RentEntity savedCalcReceipt = receiptService.saveRentWithReceipts(request.getRent(), request.getSubClients());
        if (savedCalcReceipt == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedCalcReceipt);
    }

}
