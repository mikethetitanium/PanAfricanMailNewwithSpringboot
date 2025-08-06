package com.example.PanAfricanMail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PanAfricanMail.model.CreateStory;

public interface CreateStoryRepository extends JpaRepository<CreateStory, Long> {
    // This interface will automatically have CRUD methods for CreateStory entity
    // No additional methods are needed unless specific queries are required

}
