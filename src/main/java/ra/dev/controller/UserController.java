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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
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

@PostMapping("/signup")
public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
    if (userService.existsByUserName(signupRequest.getUserName())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));
    }
    if (userService.existsByEmail(signupRequest.getEmail())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));

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
