package milla.fee_type_service.Repositories;

import milla.fee_type_service.Entities.FeeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface FeeTypeRepository extends JpaRepository<FeeTypeEntity,Long> {
=======
public interface FeeTypeRepository extends JpaRepository<FeeTypeEntity, Integer> {
>>>>>>> 62bdd90 (FeeType sin eureka)
}
