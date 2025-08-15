package com.example.PanAfricanMail.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stories")
public class CreateStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "type")
    private String type;
}
