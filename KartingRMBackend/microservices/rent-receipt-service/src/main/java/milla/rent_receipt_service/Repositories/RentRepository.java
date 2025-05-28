package milla.rent_receipt_service.Repositories;

import milla.rent_receipt_service.Entities.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Integer> {

    List<RentEntity> findByMain_client(String mainClient);
    List<RentEntity> findByRent_date(LocalDate date);
    @Query("SELECT r FROM RentEntity r WHERE r.rent_date BETWEEN :startDate AND :endDate")
    List<RentEntity> findRentsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    // Fetch rents between two dates
    List<RentEntity> findByRent_dateBetween(LocalDate startDate, LocalDate endDate);
}
