package milla.special_day_fee_service.Controllers;

import milla.special_day_fee_service.Entities.BirthdayEntity;
import milla.special_day_fee_service.Entities.HolidayEntity;
import milla.special_day_fee_service.Services.SpecialDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/specialDay")
public class SpecialDayController {
    @Autowired
    private SpecialDayService specialDayService;

    //Getters
    @GetMapping("/birthday/all")
    public ResponseEntity<List<BirthdayEntity>> getAllBirthdays(){
        List<BirthdayEntity> birthdays = specialDayService.findAllBirthday();
        if(birthdays.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(birthdays);
    }
    @GetMapping("/holiday/all")
    public ResponseEntity<List<HolidayEntity>> getAllHolidays(){
        List<HolidayEntity> holidays = specialDayService.findAllHolidays();
        if(holidays.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(holidays);
    }
    @GetMapping("/birthday/{id}")
    public ResponseEntity<BirthdayEntity> getBirthdayById(@PathVariable int id){
        BirthdayEntity birthday = specialDayService.findByBirthdayId(id);
        if(birthday == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(birthday);
    }
    @GetMapping("/holiday/{id}")
    public ResponseEntity<HolidayEntity> getHolidayById(@PathVariable int id){
        HolidayEntity holiday = specialDayService.findHolidayById(id);
        if(holiday == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(holiday);
    }
    //Save
    @PostMapping("/birthday/save")
    public ResponseEntity<BirthdayEntity> saveBirthday(@RequestBody BirthdayEntity birthdayEntity){
        BirthdayEntity savedBirthday = specialDayService.saveBirthday(birthdayEntity);
        return ResponseEntity.ok(savedBirthday);
    }
    @PostMapping("/holiday/save")
    public ResponseEntity<HolidayEntity> saveHoliday(@RequestBody HolidayEntity holidayEntity){
        HolidayEntity savedHoliday = specialDayService.saveHoliday(holidayEntity);
        return ResponseEntity.ok(savedHoliday);
    }
    //Update
    @PutMapping("/birthday/update")
    public ResponseEntity<BirthdayEntity> updateBirthday(@RequestBody BirthdayEntity birthdayEntity){
        BirthdayEntity updatedBirthday = specialDayService.updateBirthday(birthdayEntity);
        return ResponseEntity.ok(updatedBirthday);
    }
    @PutMapping("/holiday/update")
    public ResponseEntity<HolidayEntity> updateHoliday(@RequestBody HolidayEntity holidayEntity){
        HolidayEntity updatedHoliday = specialDayService.updateHoliday(holidayEntity);
        return ResponseEntity.ok(updatedHoliday);
    }
    //Get date from name
    @GetMapping("/birthday/name")
    public ResponseEntity<LocalDate> getBirthdayDateByName(@RequestBody String name){
        LocalDate date = specialDayService.findBirthdayDateByName(name);
        if(date == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(date);
    }
    //Boolean of its birthday
    @PostMapping("/birthday/isItBirthday")
    public ResponseEntity<Boolean> isItsBirthday(@RequestBody String name, @RequestBody LocalDate date){
        boolean isItsBirthday = specialDayService.isItsBirthday(name, date);
        return ResponseEntity.ok(isItsBirthday);
    }
    //Is it holiday
    @PostMapping("/holiday/isItHoliday")
    public ResponseEntity<Boolean> isItHoliday(@RequestBody LocalDate date){
        boolean isItHoliday = specialDayService.isItHoliday(date);
        return ResponseEntity.ok(isItHoliday);
    }
    //Get discount
    @GetMapping("/birthday/discount")
    public ResponseEntity<BigDecimal> getBirthdayDiscountByName(@RequestBody String name){
        BigDecimal discount = specialDayService.findBirthdayDiscountByName(name);
        return ResponseEntity.ok(discount);
    }
    @GetMapping("/holiday/discount")
    public ResponseEntity<BigDecimal> getHolidayDiscountByDate(@RequestBody LocalDate date){
        BigDecimal discount = specialDayService.findHolidayDiscountByDate(date);
        return ResponseEntity.ok(discount);
    }
}
