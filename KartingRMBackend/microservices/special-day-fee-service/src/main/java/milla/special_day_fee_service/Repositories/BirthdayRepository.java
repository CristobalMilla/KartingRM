package milla.special_day_fee_service.Repositories;

import milla.special_day_fee_service.Entities.BirthdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayRepository extends JpaRepository<BirthdayEntity, Integer> {

    BirthdayEntity findFirstByName(String name);
}
