package com.example.PanAfricanMail.controller;

import com.example.PanAfricanMail.model.CreateNewpost;
import com.example.PanAfricanMail.model.CreateStory;
import com.example.PanAfricanMail.service.CreateNewPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private CreateNewPostService createNewPostService;

    
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/create-post")
    public String createpost() {
        return "create-post";
    }

    @PostMapping("/create-post")
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody Map<String, Object> payload) {
        String type = (String) payload.get("type");

        if ("job".equalsIgnoreCase(type)) {
            CreateNewpost jobPost = new CreateNewpost();
            jobPost.setJobTitle((String) payload.get("jobTitle"));
            jobPost.setCompany((String) payload.get("company"));
            jobPost.setLocation((String) payload.get("location"));
            jobPost.setCategory((String) payload.get("category"));
            jobPost.setSalary((String) payload.get("salary"));
            jobPost.setJobDescription((String) payload.get("jobDescription"));
            jobPost.setType("job");
            createNewPostService.createPost(jobPost);
            return ResponseEntity.ok("Job post created successfully");
        } else if ("STORY".equalsIgnoreCase(type)) {
            CreateStory storyPost = new CreateStory();
            storyPost.setTitle((String) payload.get("title"));
            storyPost.setAuthor((String) payload.get("author"));
            storyPost.setContent((String) payload.get("content"));
            storyPost.setType("STORY");
            createNewPostService.createStory(storyPost);
            return ResponseEntity.ok("Story post created successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid post type");
        }
    }
}
