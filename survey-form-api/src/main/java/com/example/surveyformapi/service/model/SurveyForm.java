package com.example.surveyformapi.service.model;
import com.example.surveyformapi.common.model.ContractMetaData;
import com.example.surveyformapi.common.model.ServiceSchema;
import lombok.*;

import javax.persistence.*;


@Data
@Entity(name = "SurveyForm")
@Table
public class SurveyForm {

    @Id
    @Column
    private String surveyName;

    @Convert(converter = ContractMetaDataConverter.class)
    @Column(name = "contractMetadata", columnDefinition = "text")
    private ContractMetaData contractMetaData;

    @Convert(converter = ServiceSchemaConverter.class)
    @Column(name = "serviceSchema", columnDefinition = "text")
    private ServiceSchema serviceSchema;

    private String lastUpdatedBy;

}

