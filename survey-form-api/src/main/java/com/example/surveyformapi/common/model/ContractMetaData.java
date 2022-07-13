package com.example.surveyformapi.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonPropertyOrder({
        "name",
        "displayName",
        "version",
        "isDeprecated",
        "controlType",
        "description",
        "documentationLink"
})
public class ContractMetaData implements Serializable {

    private static final long serialVersionUID = -5238302049391887055L;

    private String            name;
    private String            displayName;
    private String            version;
    private Boolean           isDeprecated;
    private String            controlType;
    private String            description;
    private String            documentationLink;

    @JsonCreator
    public ContractMetaData(@JsonProperty(value = "name", required = true) String name,
                            @JsonProperty(value = "displayName", required = true) String displayName,
                            @JsonProperty(value = "version", required = true) String version,
                            @JsonProperty(value = "isDeprecated", required = true) Boolean isDeprecated,
                            @JsonProperty(value = "controlType", required = true) String controlType,
                            @JsonProperty(value = "description", required = true) String description,
                            @JsonProperty(value = "documentationLink", required = true) String documentationLink) {

        this.name = name;
        this.displayName = displayName;
        this.version = version;
        this.isDeprecated = isDeprecated;
        this.controlType = controlType;
        this.description = description;
        this.documentationLink = documentationLink;
    }
}