package com.example.PanAfricanMail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PanAfricanMail.model.CreateStory;
import java.util.List;

public interface CreateStoryRepository extends JpaRepository<CreateStory, Long> {

    List<CreateStory> findByType(String type);

}
