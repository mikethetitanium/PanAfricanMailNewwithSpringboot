package com.example.PanAfricanMail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PanAfricanMail.model.CreateNewpost;
import java.util.List;

public interface CreateNewpostRep extends JpaRepository<CreateNewpost, Long> {

    // Custom query to find posts by type
    List<CreateNewpost> findByType(String type);
    
    // Additional methods can be defined here as needed
}
