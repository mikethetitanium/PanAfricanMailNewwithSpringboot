package com.example.PanAfricanMail.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="stories")
public class CreateStory {
 @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;

    @Column (name="TITLE")
    private String title;

    @Column (name="AUTHOR")
    private String author;

    @Column (name="CONTENT", columnDefinition = "NVARCHAR(MAX)")
    private String content;

     @Column (name="type")
    private String type;
}
