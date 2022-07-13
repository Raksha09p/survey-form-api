package com.example.surveyformapi.service.model;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity(name = "FormData")
@Table
@IdClass(CompositeID.class)
public class FormData implements Serializable {

    @Id
    @Column
    private String formName;

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "FormData", columnDefinition = "text")
    private String formdata;
}
