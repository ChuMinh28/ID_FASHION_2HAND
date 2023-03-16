package ra.dev.model.serviceImp;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.ERole;
import ra.dev.model.entity.Roles;
import ra.dev.model.entity.User;
import ra.dev.model.repository.RoleRepository;
import ra.dev.model.repository.UserRepository;
import ra.dev.model.service.UserService;
import ra.dev.dto.request.LoginRequest;
import ra.dev.dto.request.SignupRequest;
import ra.dev.dto.respone.JwtResponse;
import ra.dev.security.CustomUserDetails;
import ra.dev.validation.Validate;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value(("${ra.jwt.expiration}"))
    private int JWT_EXPIRATION;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email) ;
    }

    @Override
    public User findByID(int userID) {
        return userRepository.findById(userID).get();
    }

    @Override
    public boolean saveOrUpdate(SignupRequest signUpRequest) {
        User user = new User();
        user.setUserStatus(true);
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
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
                    case "moderator":
                        Roles moderatorRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(moderatorRole);
                    case "user":
                        Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
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


    @Override
    public void delete(int userID) {
    }

    @Override
    public List<User> searchByName(String userName) {
        return null;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        User users = userRepository.findByEmail(customUserDetail.getEmail());
        if (!customUserDetail.isUserStatus()) {
            return null;
        } else {

        String jwt = generateToken(customUserDetail);
            List<String> listRoles = customUserDetail.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());
            return new JwtResponse(jwt, users.getUserID(), customUserDetail.getUsername(), customUserDetail.getEmail(),
                    customUserDetail.getPhone(), customUserDetail.getFullName(), customUserDetail.getAddress(), listRoles);
        }
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + JWT_EXPIRATION);
        //Tao chuoi JWT tu userName
        return Jwts.builder().setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
}
