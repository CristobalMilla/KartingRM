package milla.rent_receipt_service.Repositories;

import milla.rent_receipt_service.Entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Integer> {
    List<ReceiptEntity> getReceiptsByRent_id(int rent_id);
}
