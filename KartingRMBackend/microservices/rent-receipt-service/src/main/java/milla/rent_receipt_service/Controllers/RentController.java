package milla.rent_receipt_service.Controllers;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.People_Discount;
import milla.rent_receipt_service.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {
    @Autowired
    RentService rentService;

    //Getters
    @GetMapping("/all")
    public ResponseEntity<List<RentEntity>> getAllRents() {
        List<RentEntity> rentList = rentService.getAll();
        if (rentList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(rentList);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<RentEntity> getRentById(@PathVariable int id) {
        RentEntity rent = rentService.getById(id);
        if (rent == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(rent);
        }
    }
    //Save
    @PostMapping("/complete")
    public ResponseEntity<RentEntity> saveCompleteRent(@RequestBody RentEntity rentEntity) {
        RentEntity savedRent = rentService.save(rentEntity);
        if (savedRent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedRent);
    }
    //Update
    @PutMapping("/update")
    public ResponseEntity<RentEntity> updateRent(@RequestBody RentEntity rentEntity) {
        RentEntity updatedRent = rentService.update(rentEntity);
        if (updatedRent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedRent);
    }
    @GetMapping("/feeTypeByRentId/{id}")
    //Obtener el fee type de la renta segun su id
    public ResponseEntity<Fee_Type> getFeeTypeByRentId(@PathVariable("id") int rentId) {
        Fee_Type feeType = rentService.getFeeTypeByRentId(rentId);
        if (feeType == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(feeType);
    }
    //Obtener el people_discount de la renta segun su id
    @GetMapping("/peopleDiscountByRentId/{id}")
    public ResponseEntity<People_Discount> getPeopleDiscountByRentId(@PathVariable("id") int rentId) {
        People_Discount peopleDiscount = rentService.getPeopleDiscountByRentId(rentId);
        if (peopleDiscount == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(peopleDiscount);
    }
    //Obtener la duracion de la renta segun su fee_type, segun el id de la renta
    @GetMapping("/getRentDurationById/{id}")
    public ResponseEntity<Integer> getRentDurationById(@PathVariable("id") int rentId) {
        int duration = rentService.getDurationByRentId(rentId);
        if (duration == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(duration);
    }
    //Obtener todas las rentas entre 2 fechas
    @GetMapping("/getRentsBetweenDates")
    public ResponseEntity<List<RentEntity>> getRentsBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RentEntity> rents = rentService.getRentsBetweenDates(startDate, endDate);
        if (rents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rents);
    }
    //Obtener el total de ventas de un mes
    @GetMapping("/getTotalPriceForMonth")
    public ResponseEntity<BigDecimal> getTotalPriceForMonth(@RequestParam String month) {
        BigDecimal totalPrice = rentService.calculateTotalPriceForMonth(month);
        if (totalPrice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totalPrice);
    }

}
