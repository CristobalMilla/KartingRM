package milla.fee_type_service.Services;

import milla.fee_type_service.Entities.FeeTypeEntity;
import milla.fee_type_service.Repositories.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.web.client.RestTemplate;
import milla.fee_type_service.config.RestTemplateConfig
=======
>>>>>>> 62bdd90 (FeeType sin eureka)

import java.util.List;

@Service
public class FeeTypeService {
    @Autowired
<<<<<<< HEAD
    private FeeTypeRepository feeTypeRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<FeeTypeEntity> getAll(){
        return feeTypeRepository.findAll();
    }
    public FeeTypeEntity getFeeTypeById(long id){
        return feeTypeRepository.findById(id).orElse(null);
    }
    public Li
    public FeeTypeEntity save(FeeTypeEntity feeType){
        FeeTypeEntity feeTypeEntity = feeTypeRepository.save(feeType);
        return feeTypeEntity;
    }

=======
    FeeTypeRepository feeTypeRepository;

    //Getters
    public List<FeeTypeEntity> getAll(){
        return feeTypeRepository.findAll();
    }
    public FeeTypeEntity getFeeTypeById(int id){
        return feeTypeRepository.findById(id).orElse(null);
    }
    //Save
    public FeeTypeEntity save(FeeTypeEntity feeType){
        return feeTypeRepository.save(feeType);
    }
    //Update
    public FeeTypeEntity update(FeeTypeEntity feeType){
        return feeTypeRepository.save(feeType);
    }
>>>>>>> 62bdd90 (FeeType sin eureka)
}
