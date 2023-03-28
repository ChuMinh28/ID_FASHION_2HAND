package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.request.UpdateUserRequest;
import ra.dev.dto.respone.UserResponse;
import ra.dev.dto.respone.WishListResponse;
import ra.dev.model.entity.User;
import ra.dev.model.service.UserService;

import ra.dev.dto.request.LoginRequest;
import ra.dev.dto.request.SignupRequest;
import ra.dev.validation.Validate;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getToken")
    public ResponseEntity<?> sendEmail(@RequestParam("email") String email) {
        boolean gettoken = userService.getToken(email);
        if (gettoken) {
            return ResponseEntity.ok("Send email successfully");
        } else {
            return ResponseEntity.ok("Failed");
        }

    }

    @PostMapping("/resetPass")
    public User resetPass(@RequestParam("token") String token, @RequestBody String newPass) {
        return userService.resetPass(token, newPass);
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

    @PatchMapping("UpdateUserAddress")
    public ResponseEntity<?> updateUserAddress(@RequestBody UpdateUserRequest updateUserRequest) {
        boolean check = userService.updateUserAddress(updateUserRequest);
        if (check) {
            return ResponseEntity.ok("Update address successfully!");
        } else {
            return ResponseEntity.badRequest().body("Update failed!");
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        if (userService.existsByUserName(loginRequest.getUserName())) {
            User user = userService.findByUserName(loginRequest.getUserName());
            if (user.isUserStatus()) {
                return ResponseEntity.ok(userService.login(loginRequest));
            } else {
                return ResponseEntity.ok("Account has been lock!");
            }
        } else {
            return ResponseEntity.badRequest().body("Incorrect account or password! Please try again!");
        }
    }

    @GetMapping("logOut")
    public ResponseEntity<?> logOut() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("You have been logged out.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Action failed!");
        }
    }


    @PatchMapping("changeUserStatus/{userID}")
    public ResponseEntity<?> changeUserStatus(@PathVariable("userID") int userID, @RequestParam("action") String action) {
        try {
            boolean check = userService.delete(userID, action);
            if (check) {
                return ResponseEntity.ok("Unlock user successfully!");
            } else {
                return ResponseEntity.ok("Lock user successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Action failed!");
        }
    }

    @GetMapping("getUserByID/{userID}")
    public ResponseEntity<?> getUserByID(@PathVariable("userID") int userID) {
        UserResponse userResponse = userService.getUserByID(userID);
        if (userResponse == null) {
            return ResponseEntity.badRequest().body("Account doesn't exist!");
        } else {
            return ResponseEntity.ok(userResponse);
        }
    }

    @GetMapping("getAllUser")
    public ResponseEntity<?> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> list = userService.findAll(pageable);
        return ResponseEntity.ok(list);
    }


    @GetMapping("searchAndSortAndPaging")
    public ResponseEntity<?> searchAndSortAndPaging(
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "direction", defaultValue = "none") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort.Order order;
        Pageable pageable;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "FullName");
            pageable = PageRequest.of(page, size, Sort.by(order));
        } else if (direction.equals("desc")) {
            order = new Sort.Order(Sort.Direction.DESC, "FullName");
            pageable = PageRequest.of(page, size, Sort.by(order));
        } else {
            pageable = PageRequest.of(page,size);
        }
        Map<String, Object> list = userService.searchByName(fullName, pageable);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("addToWishList/{productID}")
    public ResponseEntity<?> addToWishList(@PathVariable("productID") int productID) {
        boolean check = userService.addToWishList(productID);
        if (check) {
            return ResponseEntity.ok("Add product to wishlist successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @PatchMapping("removeFromWishList/{productID}")
    public ResponseEntity<?> removeFromWishList(@PathVariable("productID") int productID) {
        boolean check = userService.removeFromWishList(productID);
        if (check) {
            return ResponseEntity.ok("Remove product from wishlist successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @GetMapping("getUserWishList")
    public ResponseEntity<?> getUserWishList() {
        List<WishListResponse> list = userService.getUserWishList();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.ok("There isn't any product in your wishlist!");
        }
    }
}
