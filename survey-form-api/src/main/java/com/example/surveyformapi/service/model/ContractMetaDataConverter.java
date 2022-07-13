package com.example.surveyformapi.service.model;

import com.example.surveyformapi.common.model.ContractMetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ContractMetaDataConverter implements AttributeConverter<ContractMetaData, String> {
    @Override
    public String convertToDatabaseColumn(ContractMetaData contractMetaData) {
        ObjectMapper objectMapper = new ObjectMapper();
        String contract = null;
        try {
            contract = objectMapper.writeValueAsString(contractMetaData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return contract;
    }

    @Override
    public ContractMetaData convertToEntityAttribute(String contract) {
        ContractMetaData contractMetaData = new ContractMetaData();
        try {
            contractMetaData = new ObjectMapper().readValue(contract, ContractMetaData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return contractMetaData;
    }
}