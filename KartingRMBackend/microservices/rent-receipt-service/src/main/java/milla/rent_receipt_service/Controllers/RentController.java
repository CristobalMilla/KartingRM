package milla.rent_receipt_service.Controllers;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import milla.rent_receipt_service.Model.People_Discount;
import milla.rent_receipt_service.Services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Fee_Type getFeeTypeByRentId(@PathVariable("id") int rentId) {
        return rentService.getFeeTypeByRentId(rentId);
    }
    //Obtener el people_discount de la renta segun su id
    @GetMapping("/peopleDiscountByRentId/{id}")
    public People_Discount getPeopleDiscountByRentId(@PathVariable("id") int rentId) {
        return rentService.getPeopleDiscountByRentId(rentId);
    }



}
