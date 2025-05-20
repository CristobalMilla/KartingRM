package milla.frequency_discount_service.Controllers;


import milla.frequency_discount_service.Entities.FrequencyDiscountEntity;
import milla.frequency_discount_service.Services.FrequencyDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/frequencyDiscount")
public class FrequencyDiscountController {
    @Autowired
    FrequencyDiscountService frequencyDiscountService;

    //Getters
    @GetMapping("/all")
    public ResponseEntity<List<FrequencyDiscountEntity>> getAll() {
        List<FrequencyDiscountEntity> frequencyDiscountEntities = frequencyDiscountService.getAll();
        if (frequencyDiscountEntities == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(frequencyDiscountEntities);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<FrequencyDiscountEntity> getById(@PathVariable int id) {
        FrequencyDiscountEntity frequencyDiscountEntity = frequencyDiscountService.getById(id);
        if (frequencyDiscountEntity == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(frequencyDiscountEntity);
        }
    }
    //Save
    @PostMapping("/")
    public ResponseEntity<FrequencyDiscountEntity> save(@RequestBody FrequencyDiscountEntity frequencyDiscountEntity) {
        FrequencyDiscountEntity frequencyDiscountEntitySaved = frequencyDiscountService.save(frequencyDiscountEntity);
        return ResponseEntity.ok(frequencyDiscountEntitySaved);
    }
    //Update
    @PutMapping("/")
    public ResponseEntity<FrequencyDiscountEntity> update(@RequestBody FrequencyDiscountEntity frequencyDiscountEntity) {
        FrequencyDiscountEntity frequencyDiscountEntityUpdated = frequencyDiscountService.update(frequencyDiscountEntity);
        return ResponseEntity.ok(frequencyDiscountEntityUpdated);
    }

    //Controller de Funcion para obtener una entidad descuento-frecuencia segun numero de visitas ingresado
    @GetMapping("/getFrequencyByNumber/{number}")
    public ResponseEntity<FrequencyDiscountEntity> getFrequencyByNumber(@PathVariable int number) {
        FrequencyDiscountEntity frequencyDiscountEntity = frequencyDiscountService.getFrequencyByClientFrequency(number);
        if (frequencyDiscountEntity == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(frequencyDiscountEntity);
        }
    }
    //Controller de misma funcion, pero devolviendo el descuento
    @GetMapping("/getDiscountByNumber/{number}")
    public ResponseEntity<BigDecimal> getDiscountByNumber(@PathVariable int number) {
        FrequencyDiscountEntity frequencyDiscountEntity = frequencyDiscountService.getFrequencyByClientFrequency(number);
        if (frequencyDiscountEntity == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(frequencyDiscountEntity.getDiscount());
        }
    }
}
