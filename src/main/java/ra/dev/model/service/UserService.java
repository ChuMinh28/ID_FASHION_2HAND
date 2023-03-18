package ra.dev.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.dev.dto.request.UpdateUserRequest;
import ra.dev.dto.respone.UserResponse;
import ra.dev.model.entity.User;
import ra.dev.dto.request.LoginRequest;
import ra.dev.dto.request.SignupRequest;
import ra.dev.dto.respone.JwtResponse;

import java.util.Map;

public interface UserService {

    boolean existsByEmail(String email);
    boolean saveOrUpdate(SignupRequest signupRequest);
    boolean updateUserAddress(UpdateUserRequest userRequest);
    Map<String, Object> findAll(Pageable pageable);
    Map<String, Object> softByName(String direction, int size, int page);
    Map<String, Object> searchByName(String userName, Pageable pageable);
    UserResponse getUserByID(int userID);
    boolean delete(int userID, String action);
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    JwtResponse login(LoginRequest loginRequest);
    boolean getToken(String email);
    User resetPass(String token, String newPass);

}
