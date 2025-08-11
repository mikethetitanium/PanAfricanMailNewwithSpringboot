package com.example.PanAfricanMail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.stereotype.Service;
import com.example.PanAfricanMail.model.CreateNewpost;
import com.example.PanAfricanMail.repository.CreateNewpostRep;
import com.example.PanAfricanMail.model.CreateStory;
import com.example.PanAfricanMail.repository.CreateStoryRepository;
import com.example.PanAfricanMail.repository.UserRepository;
import com.example.PanAfricanMail.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;


@Service
public class CreateNewPostService {

    @Autowired
    private CreateNewpostRep createNewPostRepository;

    public CreateNewpost createPost(CreateNewpost post) {
        return createNewPostRepository.save(post);
    }

    public List<CreateNewpost> getAllJobs() {
    return createNewPostRepository.findByType("job");
}


    @Autowired
    private CreateStoryRepository createStoryRepository;

    public CreateStory createStory(CreateStory story) {
        return createStoryRepository.save(story);
    }
    public List<CreateStory> getAllStories() {
        return createStoryRepository.findByType("story");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 🔐 Inject BCrypt encoder

    public boolean userExists(String email, String username) {
        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username);
    }

    public User register(User user) {
        // 🔐 Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List <User> getAllUsers() {
        return userRepository.findAll();
    }
}
