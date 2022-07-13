package com.example.surveyformapi.service.repository;
import com.example.surveyformapi.service.model.CompositeID;
import com.example.surveyformapi.service.model.FormData;
import org.springframework.data.repository.CrudRepository;

public interface FormDataRepository extends CrudRepository<FormData, CompositeID>  {

}
