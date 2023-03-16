package ra.dev.model.service;


import ra.dev.model.entity.User;
import ra.dev.payload.request.LoginRequest;
import ra.dev.payload.response.JwtResponse;

import java.util.List;

public interface UserService {

    //    boolean existsByEmail(String email);
//    boolean saveOrUpdate(SignUpRequest userRequest);
    List<User> findAll();

    User findByEmail(String email);

    User findByID(int userID);

    User saveOrUpdate(User user);

    void delete(int userID);

    List<User> searchByName(String userName);

    User findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
    JwtResponse login(LoginRequest loginRequest);
    boolean getToken(String email);
    User resetPass(String token, String newPass);

}
