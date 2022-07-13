package com.example.surveyformapi.service.model;

import com.example.surveyformapi.common.model.ServiceSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ServiceSchemaConverter implements AttributeConverter<ServiceSchema, String> {
    @Override
    public String convertToDatabaseColumn(ServiceSchema serviceSchema) {

        ObjectMapper objectMapper = new ObjectMapper();
        String service = null;
        try {
            service = objectMapper.writeValueAsString(serviceSchema);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public ServiceSchema convertToEntityAttribute(String service) {

        ServiceSchema serviceSchema = new ServiceSchema();
        try {
            serviceSchema = new ObjectMapper().readValue(service, ServiceSchema.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serviceSchema;
    }
}