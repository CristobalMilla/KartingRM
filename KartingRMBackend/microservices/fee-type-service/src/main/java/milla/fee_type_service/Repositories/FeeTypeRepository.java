package milla.fee_type_service.Repositories;

import milla.fee_type_service.Entities.FeeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeTypeEntity,Long> {
}
