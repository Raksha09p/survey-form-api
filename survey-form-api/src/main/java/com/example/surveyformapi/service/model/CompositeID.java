package com.example.surveyformapi.service.model;

import java.io.Serializable;
import java.util.Objects;


public class CompositeID implements Serializable {
    private String formName;
    private String userId;

    public CompositeID(){

    }

    public CompositeID(String formName, String userId){
        this.formName = formName;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeID survey = (CompositeID) o;
        return formName.equals(survey.formName) &&
                userId.equals(survey.userId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(formName, userId);
    }

}
