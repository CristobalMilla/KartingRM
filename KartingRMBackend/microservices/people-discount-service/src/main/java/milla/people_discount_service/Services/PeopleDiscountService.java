package milla.people_discount_service.Services;


import milla.people_discount_service.Entities.PeopleDiscountEntity;
import milla.people_discount_service.Repositories.PeopleDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleDiscountService {
    @Autowired
    private PeopleDiscountRepository peopleDiscountRepository;

    //Getters
    public List<PeopleDiscountEntity> getAll(){
        return peopleDiscountRepository.findAll();
    }
    public PeopleDiscountEntity getPeopleDiscountById(int id){
        return peopleDiscountRepository.findById(id).get();
    }
    //Save
    public PeopleDiscountEntity save(PeopleDiscountEntity peopleDiscountEntity){
        return peopleDiscountRepository.save(peopleDiscountEntity);
    }
    //Update
    public PeopleDiscountEntity update(PeopleDiscountEntity peopleDiscountEntity){
        return peopleDiscountRepository.save(peopleDiscountEntity);
    }
    //Funcion que retorna un PeopleDiscountEntity segun un numero de personas de entrada
    public  PeopleDiscountEntity findByPeopleAmount(int peopleAmount){
        return peopleDiscountRepository.findByPeopleAmount(peopleAmount);
    }
}
