package com.example.surveyformapi.common.model;

import java.io.Serializable;

import com.example.surveyformapi.common.util.RawJsonObjectDeserializer;
import com.example.surveyformapi.common.util.RawJsonSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Slf4j
public class ServiceSchema implements Serializable {
    private static final long serialVersionUID = -3424143040229939052L;

    private String type;

    @JsonProperty("@class")
    private String classPath;

    @JsonSerialize(using = RawJsonSerializer.class)
    @JsonDeserialize(using = RawJsonObjectDeserializer.class)
    private String properties;

    @JsonCreator
    public ServiceSchema(@JsonProperty(value = "@class", required = true) String classPath,
                         @JsonProperty(value = "type", required = true) String type) {
        this.classPath = classPath;
        this.type = type;
    }

}