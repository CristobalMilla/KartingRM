package milla.fee_type_service.Controllers;

import milla.fee_type_service.Entities.FeeTypeEntity;
import milla.fee_type_service.Services.FeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/feeType")
public class FeeTypeController {
    @Autowired
    private FeeTypeService feeTypeService;

    //Getters
    @GetMapping("/all")
    public ResponseEntity<List<FeeTypeEntity>> getAll() {
        List<FeeTypeEntity> list = feeTypeService.getAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(list);
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
    //Save
    @PostMapping("/")
    public ResponseEntity<FeeTypeEntity> add(@RequestBody FeeTypeEntity feeType) {
        FeeTypeEntity feeTypeNew = feeTypeService.save(feeType);
        return ResponseEntity.ok(feeTypeNew);
    }
    //Update
    @PutMapping("/")
    public ResponseEntity<FeeTypeEntity> update(@RequestBody FeeTypeEntity feeType) {
        FeeTypeEntity feeTypeUpdate = feeTypeService.update(feeType);
        return ResponseEntity.ok(feeTypeUpdate);
    }

}
