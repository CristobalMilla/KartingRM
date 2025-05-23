package milla.rent_receipt_service.Repositories;

import milla.rent_receipt_service.Entities.RentEntity;
import milla.rent_receipt_service.Model.Fee_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Integer> {

    List<RentEntity> findByMain_client(String mainClient);
    List<RentEntity> findByRent_date(Date date);
}
