package com.example.PanAfricanMail.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="posts")
public class CreateNewpost {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;

    @Column (name="JOB_TITLE")
    private String jobTitle;

    @Column (name="COMPANY")
    private String company;

    @Column (name="LOCATION")
    private String location;

    @Column (name="CATEGORY")
    private String category;

    @Column (name="SALARY")
    private String salary;

   @Column(name="JOB_DESCRIPTION", columnDefinition = "TEXT")
private String jobDescription;


    @Column (name="type")
    private String type;
    
}

