package com.example.PanAfricanMail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PanAfricanMail.model.CreateNewpost;

public interface CreateNewpostRep extends JpaRepository<CreateNewpost, Long> {

    // This interface will automatically provide CRUD operations for createNewpost entity
    // No additional methods are needed unless you want to define custom queries

}
