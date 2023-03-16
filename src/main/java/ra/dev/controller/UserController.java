package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.service.UserService;
import ra.dev.payload.request.SignUpRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> Register(@RequestBody SignUpRequest signUpRequest) {
        boolean check = userService.saveOrUpdate(signUpRequest);
        if (check) {
            return ResponseEntity.ok("Register successfully");
        } else {
            return ResponseEntity.badRequest().body("Register failed!");
        }
    }
}
