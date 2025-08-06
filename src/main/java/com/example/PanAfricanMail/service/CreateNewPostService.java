package com.example.PanAfricanMail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PanAfricanMail.model.CreateNewpost;
import com.example.PanAfricanMail.repository.CreateNewpostRep;
import com.example.PanAfricanMail.model.CreateStory;
import com.example.PanAfricanMail.repository.CreateStoryRepository;

@Service
public class CreateNewPostService {

    @Autowired
    private CreateNewpostRep createNewPostRepository;

    public CreateNewpost createPost(CreateNewpost post) {
        return createNewPostRepository.save(post);
    }

    @Autowired
    private CreateStoryRepository createStoryRepository;
    public CreateStory createStory(CreateStory story) {
        return createStoryRepository.save(story);
    }
}
