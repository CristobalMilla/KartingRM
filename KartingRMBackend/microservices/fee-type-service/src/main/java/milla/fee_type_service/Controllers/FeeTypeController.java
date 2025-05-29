package milla.fee_type_service.Controllers;

import milla.fee_type_service.Entities.FeeTypeEntity;
import milla.fee_type_service.Services.FeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feeType")
public class FeeTypeController {
    @Autowired
    private FeeTypeService feeTypeService;

    //Getters
    @GetMapping("/all")
    public ResponseEntity<List<FeeTypeEntity>> getAll() {
        List<FeeTypeEntity> feeTypes = feeTypeService.getAll();
        if (feeTypes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(feeTypes);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<FeeTypeEntity> getById(@PathVariable int id) {
        FeeTypeEntity feeType = feeTypeService.getFeeTypeById(id);
        if (feeType == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(feeType);
        }
    }
    //Add
    @PostMapping("/")
    public ResponseEntity<FeeTypeEntity> save(@RequestBody FeeTypeEntity feeType) {
        FeeTypeEntity feeTypeEntity = feeTypeService.save(feeType);
        return ResponseEntity.ok(feeTypeEntity);
    }
    //Update
    @PutMapping("/")
    public ResponseEntity<FeeTypeEntity> update(@RequestBody FeeTypeEntity feeType) {
        FeeTypeEntity feeTypeEntity = feeTypeService.update(feeType);
        return ResponseEntity.ok(feeTypeEntity);
    }
}
