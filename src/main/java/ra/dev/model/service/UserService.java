package ra.dev.model.service;


import ra.dev.model.entity.User;
import ra.dev.payload.request.SignUpRequest;

public interface UserService {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean saveOrUpdate(SignUpRequest userRequest);
}
