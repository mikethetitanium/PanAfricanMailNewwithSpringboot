package com.example.PanAfricanMail.controller;

import com.example.PanAfricanMail.model.CreateNewpost;
import com.example.PanAfricanMail.model.CreateStory;
import com.example.PanAfricanMail.service.CreateNewPostService;
import com.example.PanAfricanMail.model.User;
import com.example.PanAfricanMail.repository.UserRepository;
import com.example.PanAfricanMail.model.LoginRequest;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.springframework.ui.Model;

import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private CreateNewPostService createNewPostService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** ------------------- Helper Method ------------------- **/
    private String convertRtfToHtmlAndUnescape(String content) {
        if (content == null) return null;
        String result = content;
        try {
            // Detect & convert RTF to HTML
            if (result.trim().startsWith("{\\rtf")) {
                RTFEditorKit rtfParser = new RTFEditorKit();
                Document doc = rtfParser.createDefaultDocument();
                rtfParser.read(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)), doc, 0);

                StringWriter writer = new StringWriter();
                new HTMLEditorKit().write(writer, doc, 0, doc.getLength());
                result = writer.toString();
            }
        } catch (Exception e) {
            System.err.println("RTF->HTML conversion failed: " + e.getMessage());
        }
        // Apply HTML unescape twice to fix nested escaped HTML
        result = HtmlUtils.htmlUnescape(result);
        result = HtmlUtils.htmlUnescape(result);
        return result;
    }

    /** ------------------- Page Routes ------------------- **/
    @GetMapping("/")
public String home(Model model) {
    List<CreateNewpost> jobs = createNewPostService.getAllJobs();
    jobs.forEach(j -> j.setJobDescription(convertRtfToHtmlAndUnescape(j.getJobDescription())));  // <-- convert here
    model.addAttribute("jobs", jobs);

    List<CreateStory> stories = createNewPostService.getAllStories();
    stories.forEach(s -> s.setContent(convertRtfToHtmlAndUnescape(s.getContent())));
    model.addAttribute("stories", stories);

    return "index";
}


    @GetMapping("/login")
    public String login() {
        return "login";
    }

   @GetMapping("/admin")
public String admin(Model model) {
    List<User> users = createNewPostService.getAllUsers();
    model.addAttribute("users", users);

    List<CreateNewpost> jobs = createNewPostService.getAllJobs();
    jobs.forEach(j -> j.setJobDescription(convertRtfToHtmlAndUnescape(j.getJobDescription())));
    model.addAttribute("jobs", jobs);

    List<CreateStory> stories = createNewPostService.getAllStories();
    stories.forEach(s -> s.setContent(convertRtfToHtmlAndUnescape(s.getContent())));
    model.addAttribute("stories", stories);

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

    /** ------------------- Post Creation ------------------- **/
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
        }

        return ResponseEntity.badRequest().body("Invalid post type");
    }

    /** ------------------- Auth ------------------- **/
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (createNewPostService.userExists(user.getEmail(), user.getUsername())) {
            return ResponseEntity.badRequest().body("User with this email or username already exists");
        }
        createNewPostService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            session.setAttribute("user", user);
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
    }

    @GetMapping("/api/current-user")
    public ResponseEntity<?> currentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return (user != null)
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }

    @GetMapping("/api/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    /** ------------------- APIs ------------------- **/
    @GetMapping("/api/jobs")
    @ResponseBody
    public ResponseEntity<?> getAllJobs() {
        return ResponseEntity.ok(createNewPostService.getAllJobs());
    }

    @GetMapping("/api/stories")
    @ResponseBody
    public ResponseEntity<?> getAllStories() {
        List<CreateStory> stories = createNewPostService.getAllStories();
        stories.forEach(s -> s.setContent(convertRtfToHtmlAndUnescape(s.getContent())));
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(createNewPostService.getAllUsers());
    }

    /** ------------------- Delete APIs ------------------- **/
    @DeleteMapping("/api/jobs/{postId}")
    @ResponseBody
    public ResponseEntity<String> deleteJob(@PathVariable Long postId) {
        createNewPostService.deleteJob(postId);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @DeleteMapping("/api/stories/{postId}")
    @ResponseBody
    public ResponseEntity<String> deleteStory(@PathVariable Long postId) {
        createNewPostService.deleteStory(postId);
        return ResponseEntity.ok("Story deleted successfully");
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        createNewPostService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
