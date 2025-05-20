package milla.fee_type_service.Services;

import milla.fee_type_service.Entities.FeeTypeEntity;
import milla.fee_type_service.Repositories.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeTypeService {
    @Autowired
    private FeeTypeRepository feeTypeRepository;

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
}
