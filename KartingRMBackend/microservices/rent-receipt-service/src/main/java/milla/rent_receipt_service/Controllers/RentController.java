package milla.rent_receipt_service.Controllers;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.People_Discount;
import milla.rent_receipt_service.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;

@Controller
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
        return ResponseEntity.ok(savedRent);
    }
    //Update
    @PutMapping("/update")
    public ResponseEntity<RentEntity> updateRent(@RequestBody RentEntity rentEntity) {
        RentEntity updatedRent = rentService.update(rentEntity);
        return ResponseEntity.ok(updatedRent);
    }
    @GetMapping("/feeTypeByRentId/{id}")
    //Obtener el fee type de la renta segun su id
    public ResponseEntity<Fee_Type> getFeeTypeByRentId(@PathVariable("id") int rentId) {
        Fee_Type feeType = rentService.getFeeTypeByRentId(rentId);
        return ResponseEntity.ok(feeType);
    }
    //Obtener el people_discount de la renta segun su id
    @GetMapping("/peopleDiscountByRentId/{id}")
    public ResponseEntity<People_Discount> getPeopleDiscountByRentId(@PathVariable("id") int rentId) {
        People_Discount peopleDiscount = rentService.getPeopleDiscountByRentId(rentId);
        return ResponseEntity.ok(peopleDiscount);
    }
    //Obtener la duracion de la renta segun su fee_type, segun el id de la renta
    @GetMapping("/getRentDurationById/{id}")
    public ResponseEntity<Integer> getRentDurationById(@PathVariable("id") int rentId) {
        int duration = rentService.getDurationByRentId(rentId);
        return ResponseEntity.ok(duration);
    }
    //Obtener todas las rentas entre 2 fechas
    @GetMapping("/getRentsBetweenDates")
    public ResponseEntity<List<RentEntity>> getRentsBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<RentEntity> rents = rentService.getRentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(rents);
    }


}
