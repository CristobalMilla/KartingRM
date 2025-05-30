package milla.special_day_fee_service.Services;

import milla.special_day_fee_service.Entities.BirthdayEntity;
import milla.special_day_fee_service.Entities.HolidayEntity;
import milla.special_day_fee_service.Repositories.BirthdayRepository;
import milla.special_day_fee_service.Repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SpecialDayService {
    @Autowired
    private BirthdayRepository birthdayRepository;
    @Autowired
    private HolidayRepository holidayRepository;

    //Birthday
    //Getters
    public List<BirthdayEntity> findAllBirthday(){
        return birthdayRepository.findAll();
    }
    public BirthdayEntity findByBirthdayId(int id){
        return birthdayRepository.findById(id).orElse(null);
    }
    //Save
    public BirthdayEntity saveBirthday(BirthdayEntity birthdayEntity){
        return birthdayRepository.save(birthdayEntity);
    }
    //Update
    public BirthdayEntity updateBirthday(BirthdayEntity birthdayEntity){
        return birthdayRepository.save(birthdayEntity);
    }
    //Get date from name
    public LocalDate findBirthdayDateByName(String name){
        BirthdayEntity birthday = birthdayRepository.findFirstByName(name);
        if(birthday == null){
            return null;
        }
        return birthday.getDate();
    }
    //Boolean of its birthday
    public boolean isItsBirthday(String name, LocalDate date){
        LocalDate birthdayDate = findBirthdayDateByName(name);
        if (birthdayDate != null) {
            if (birthdayDate.getDayOfMonth() == date.getDayOfMonth()
                    && birthdayDate.getMonth() == date.getMonth()) {
                return true;
            }
        } return false;

    }
    public BigDecimal findBirthdayDiscountByName(String name){
        BirthdayEntity birthday = birthdayRepository.findFirstByName(name);
        if(birthday == null){
            return BigDecimal.ONE;
        }
        return birthday.getDiscount();
    }
    //Holiday
    //Getters
    public List<HolidayEntity> findAllHolidays(){
        return holidayRepository.findAll();
    }
    public HolidayEntity findHolidayById(int id){
        return holidayRepository.findById(id).orElse(null);
    }
    public HolidayEntity findHolidayByDate(LocalDate date){
        return holidayRepository.findByDate(date);
    }
    //Save
    public HolidayEntity saveHoliday(HolidayEntity holidayEntity){
        return holidayRepository.save(holidayEntity);
    }
    //Update
    public HolidayEntity updateHoliday(HolidayEntity holidayEntity){
        return holidayRepository.save(holidayEntity);
    }
    //Is it holiday
    public boolean isItHoliday(LocalDate date) {
        return holidayRepository.existsByMonthAndDay(date.getMonthValue(), date.getDayOfMonth());
    }
    //Get discount
    public BigDecimal findHolidayDiscountByDate(LocalDate date){
        HolidayEntity holiday = findHolidayByDate(date);
        if(holiday == null){
            return BigDecimal.ONE;
        }
        return holiday.getDiscount();
    }
}
