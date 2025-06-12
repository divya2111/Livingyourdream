package com.livingyourdream.controller;


import com.livingyourdream.model.User;
import com.livingyourdream.service.Customtripplanservice;
import com.livingyourdream.service.Defaulttripplanservice;
import com.livingyourdream.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/register")
public class Usercontroller {

    @Autowired
    private Userservice userservice;

    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userservice.registerUser(user));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        try {
            User created = userservice.registerAdmin(user);
            return ResponseEntity.ok("Admin created with username: " + created.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<User> getuserById(@PathVariable Long id){
        return userservice.getuserbyid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{user_id}")
    public ResponseEntity<User> DeletebyId(@PathVariable Long user_id){
        userservice.DeleteById(user_id);
        return ResponseEntity.noContent().build();
    }
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login"; // JSP or Thymeleaf login page
//    }

//    // User dashboard after login
//    @GetMapping("/user/dashboard")
//    public String userDashboard(Authentication authentication, Model model) {
//        String username = authentication.getName();
//        User user = userService.getUserByUsername(username);
//        model.addAttribute("user", user);
//        return "user-dashboard"; // JSP or Thymeleaf
//    }
//
//    // Admin dashboard
//    @GetMapping("/admin/dashboard")
//    public String adminDashboard() {
//        return "admin-dashboard"; // JSP or Thymeleaf
//    }


}
