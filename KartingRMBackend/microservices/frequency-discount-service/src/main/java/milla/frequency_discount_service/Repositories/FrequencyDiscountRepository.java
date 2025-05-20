package milla.frequency_discount_service.Repositories;

import milla.frequency_discount_service.Entities.FrequencyDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyDiscountRepository extends JpaRepository<FrequencyDiscountEntity, Integer> {

    @Query(value = "SELECT * FROM frequency_discount_entity WHERE :clientFrequency BETWEEN min_frequency AND max_frequency LIMIT 1", nativeQuery = true)
    FrequencyDiscountEntity findByClientFrequency(@Param("clientFrequency") int clientFrequency);

}
