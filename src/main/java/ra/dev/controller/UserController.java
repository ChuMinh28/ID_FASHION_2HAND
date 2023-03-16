package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.dev.jwt.JwtTokenProvider;
import ra.dev.model.entity.ERole;
import ra.dev.model.entity.Roles;
import ra.dev.model.entity.User;
import ra.dev.model.service.RoleService;
import ra.dev.model.service.UserService;
import ra.dev.payload.request.LoginRequest;
import ra.dev.payload.request.SignupRequest;
import ra.dev.payload.response.JwtResponse;
import ra.dev.payload.response.MessageResponse;
import ra.dev.security.CustomUserDetails;
import ra.dev.security.CustomUserDetailsService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    }
    User user = new User();
    user.setUserName(signupRequest.getUserName());
    user.setPassword(encoder.encode(signupRequest.getPassword()));
    user.setFullName(signupRequest.getFullName());
    user.setEmail(signupRequest.getEmail());
    user.setPhoneNumber(signupRequest.getPhoneNumber());
    user.setAddress(signupRequest.getAddress());
    user.setUserStatus(true);
    Set<String> strRoles = signupRequest.getListRoles();
    Set<Roles> listRoles = new HashSet<>();
    if (strRoles == null) {
        //User quyen mac dinh
        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        listRoles.add(userRole);
    } else {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                    listRoles.add(adminRole);
                case "moderator":
                    Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                    listRoles.add(modRole);
                case "user":
                    Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                    listRoles.add(userRole);
            }
        });
    }
    user.setListRoles(listRoles);
    userService.saveOrUpdate(user);
    return ResponseEntity.ok(user);
}

    @PostMapping("/signin2")
    public ResponseEntity<?> loginUser2(@RequestBody LoginRequest loginRequest) {
      return ResponseEntity.ok(userService.login(loginRequest));
    }
}
