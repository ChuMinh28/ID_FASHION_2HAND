package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.service.UserService;
import ra.dev.payload.request.LoginRequest;
import ra.dev.payload.request.SignupRequest;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        boolean check = userService.saveOrUpdate(signupRequest);
        if (check) {
            return ResponseEntity.ok("Register successfully!");
        } else {
            return ResponseEntity.badRequest().body("Register failed!");
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<?> loginUser2(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
