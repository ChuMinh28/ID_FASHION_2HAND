package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.entity.User;
import ra.dev.model.service.UserService;
import ra.dev.dto.request.LoginRequest;
import ra.dev.dto.request.SignupRequest;
import ra.dev.validation.Validate;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getToken")
    public ResponseEntity<?> sendEmail(@RequestParam("email") String email) {
        boolean gettoken = userService.getToken(email);
        if (gettoken){
            return ResponseEntity.ok("Send email successfully");
        }else {
            return ResponseEntity.ok("Failed");
        }

    }
    @PostMapping("/resetPass")
    public User resetPass(@RequestParam("token") String token, @RequestBody String newPass) {
        return userService.resetPass(token,newPass);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body("UserName is already!");
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already!");
        }
        if (!Validate.checkEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Enter email format please!");
        }
        if (!Validate.checkPassword(signupRequest.getPassword())) {
            return ResponseEntity.badRequest().body("Password consists of 6 characters, including lowercase, uppercase and number!");
        }
        boolean check = userService.saveOrUpdate(signupRequest);
        if (check) {
            return ResponseEntity.ok("Register successfully!");
        } else {
            return ResponseEntity.badRequest().body("Register failed!");
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<?> loginUser2(@RequestBody LoginRequest loginRequest) {
        if (userService.existsByUserName(loginRequest.getUserName())) {
            return ResponseEntity.ok(userService.login(loginRequest));
        } else {
            return ResponseEntity.badRequest().body("Incorrect account or password! Please try again!");
        }
    }

    @GetMapping("logOut")
    public ResponseEntity<?> logOut() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("You have been logged out.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Action failed!");
        }
    }
}
