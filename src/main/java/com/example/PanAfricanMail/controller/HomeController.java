package com.example.PanAfricanMail.controller;

import com.example.PanAfricanMail.model.CreateNewpost;
import com.example.PanAfricanMail.model.CreateStory;
import com.example.PanAfricanMail.service.CreateNewPostService;
import com.example.PanAfricanMail.model.User;
import com.example.PanAfricanMail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.example.PanAfricanMail.model.LoginRequest;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (createNewPostService.userExists(user.getEmail(), user.getUsername())) {
            return ResponseEntity.badRequest().body("User with this email or username already exists");
        }
        createNewPostService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
private PasswordEncoder passwordEncoder;

@PostMapping("/api/login")
@ResponseBody
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
    User user = userRepository.findByEmail(loginRequest.getEmail());

    if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        session.setAttribute("user", user); // ✅ store user in session
        return ResponseEntity.ok(Map.of("message", "Login successful"));
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(Map.of("error", "Invalid email or password"));
    }
}

@GetMapping("/api/current-user")
public ResponseEntity<?> currentUser(HttpSession session) {
    User user = (User) session.getAttribute("user");

    if (user != null) {
        return ResponseEntity.ok(user);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }
}

@GetMapping("/api/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate(); // Clears session
    return ResponseEntity.ok("Logged out successfully");
}


}
