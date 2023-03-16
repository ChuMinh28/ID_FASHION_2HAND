package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.ERole;
import ra.dev.model.entity.Roles;
import ra.dev.model.entity.User;
import ra.dev.model.repository.RoleRepository;
import ra.dev.model.repository.UserRepository;
import ra.dev.model.service.UserService;
import ra.dev.payload.request.SignUpRequest;
import ra.dev.validation.Validate;

import java.util.HashSet;
import java.util.Set;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean saveOrUpdate(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return false;
        }
        User user = new User();
        if (Validate.checkEmail(signUpRequest.getEmail())) {
            user.setEmail(signUpRequest.getEmail());
        } else {
            return false;
        }
        if (Validate.checkPassword(signUpRequest.getPassword())) {
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
        } else {
            return false;
        }
        user.setUserStatus(true);
        user.setAddress(signUpRequest.getAddress());
        user.setFullName(signUpRequest.getFullName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setZipCode(signUpRequest.getZipCode());
        Set<String> strRoles = signUpRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin":
                        Roles adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "user":
                        Roles moderatorRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(moderatorRole);
                }
            });
        }
        user.setListRoles(listRoles);
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
