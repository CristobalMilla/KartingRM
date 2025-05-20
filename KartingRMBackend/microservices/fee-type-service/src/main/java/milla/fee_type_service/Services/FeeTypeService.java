package milla.fee_type_service.Services;

import milla.fee_type_service.Entities.FeeTypeEntity;
import milla.fee_type_service.Repositories.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import milla.fee_type_service.config.RestTemplateConfig

import java.util.List;

@Service
public class FeeTypeService {
    @Autowired
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

}
