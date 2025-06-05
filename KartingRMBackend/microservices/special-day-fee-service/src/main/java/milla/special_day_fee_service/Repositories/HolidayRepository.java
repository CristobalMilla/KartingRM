package milla.special_day_fee_service.Repositories;

import milla.special_day_fee_service.Entities.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {
    HolidayEntity findByDate(LocalDate date);
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END " +
            "FROM HolidayEntity h WHERE MONTH(h.date) = :month AND DAY(h.date) = :day")
    boolean existsByMonthAndDay(@Param("month") int month, @Param("day") int day);

}
