package ra.dev.model.serviceImp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.UpdateUserRequest;
import ra.dev.dto.respone.*;
import ra.dev.model.entity.ERole;
import ra.dev.model.entity.Roles;
import ra.dev.model.entity.User;
import ra.dev.model.repository.RoleRepository;
import ra.dev.model.repository.UserRepository;
import ra.dev.model.service.UserService;
import ra.dev.dto.request.LoginRequest;
import ra.dev.dto.request.SignupRequest;
import ra.dev.security.CustomUserDetails;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value(("${ra.jwt.expiration}"))
    private int JWT_EXPIRATION;

    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        try {
            Page<User> listUser = userRepository.findAll(pageable);
            List<GetAllUserResponse> list = new ArrayList<>();
            for (User user : listUser) {
                GetAllUserResponse userResponse = new GetAllUserResponse();
                userResponse.setUserID(user.getUserID());
                userResponse.setUserName(user.getUserName());
                userResponse.setEmail(user.getEmail());
                userResponse.setFullName(user.getFullName());
                userResponse.setPhoneNumber(user.getPhoneNumber());
                userResponse.setAddress(user.getAddress());
                userResponse.setZipCode(user.getZipCode());
                list.add(userResponse);
            }
            Map<String, Object> listUserResponse = new HashMap<>();
            listUserResponse.put("listUser", list);
            listUserResponse.put("totalItems", listUser.getTotalElements());
            listUserResponse.put("totalPage", listUser.getTotalPages());
            return listUserResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> softByName(String direction, int size, int page) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "FullName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "FullName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<User> listUser = userRepository.findAll(pageable);
        List<GetAllUserResponse> list = new ArrayList<>();
        for (User user : listUser) {
            GetAllUserResponse userResponse = new GetAllUserResponse();
            userResponse.setUserID(user.getUserID());
            userResponse.setUserName(user.getUserName());
            userResponse.setEmail(user.getEmail());
            userResponse.setFullName(user.getFullName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            userResponse.setZipCode(user.getZipCode());
            list.add(userResponse);
        }
        Map<String, Object> listUserResponse = new HashMap<>();
        listUserResponse.put("listUser", list);
        listUserResponse.put("totalItems", listUser.getTotalElements());
        listUserResponse.put("totalPage", listUser.getTotalPages());
        return listUserResponse;
    }

    @Override
    public UserResponse getUserByID(int userID) {
        try {
            User user = userRepository.findById(userID).get();
            UserResponse userResponse = new UserResponse();
            userResponse.setUserID(user.getUserID());
            userResponse.setUserName(user.getUserName());
            userResponse.setEmail(user.getEmail());
            userResponse.setCreated(user.getCreated());
            userResponse.setFullName(user.getFullName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            userResponse.setZipCode(user.getZipCode());
            return userResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveOrUpdate(SignupRequest signUpRequest) {
        User user = new User();
        user.setUserStatus(true);
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setCreated(LocalDate.now());
        user.setAddress(signUpRequest.getAddress());
        user.setFullName(signUpRequest.getFullName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setZipCode(signUpRequest.getZipCode());
        Set<String> strRoles = signUpRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            //User quyen mac dinh
            Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles moderatorRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserAddress(UpdateUserRequest userRequest) {
        try {
            CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findById(customUserDetail.getUserId()).get();
            user.setAddress(userRequest.getAddress());
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(int userID, String action) {
        User user = userRepository.findById(userID).get();
        if (action.equals("lock")) {
            user.setUserStatus(false);
            userRepository.save(user);
            return false;
        } else {
            user.setUserStatus(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public Map<String, Object> searchByName(String userName, Pageable pageable) {
        Page<User> listUser = userRepository.searchUserByFullNameContainingIgnoreCase(userName, pageable);
        List<GetAllUserResponse> list = new ArrayList<>();
        for (User user : listUser) {
            GetAllUserResponse userResponse = new GetAllUserResponse();
            userResponse.setUserID(user.getUserID());
            userResponse.setUserName(user.getUserName());
            userResponse.setEmail(user.getEmail());
            userResponse.setFullName(user.getFullName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            userResponse.setZipCode(user.getZipCode());
            list.add(userResponse);
        }
        Map<String, Object> listUserResponse = new HashMap<>();
        listUserResponse.put("listUser", list);
        listUserResponse.put("totalItems", listUser.getTotalElements());
        listUserResponse.put("totalPage", listUser.getTotalPages());
        return listUserResponse;
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

    @Override
    public boolean getToken(String email) {
        try {
            String jwt = generateTokenEmail(email);
            sendSimpleMessage(email, "Token", jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User resetPass(String token, String newPass) {
        String userName = getUserNameFromJwt(token);
        User users = findByUserName(userName);
        users.setPassword(encoder.encode(newPass));
        return userRepository.save(users);
    }

    @Override
    public List<NewUserByDays> newUserByDate(int days) {
        try {
                LocalDate end = LocalDate.now();
                LocalDate start = end.minusDays(days);
                List<User> listUser = userRepository.findAllByCreatedBetween(start, end);
                return changeData(listUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<NewUserByDays> changeData(List<User> list) {
        List<NewUserByDays> listUserResponse = new ArrayList<>();
        for (User user : list) {
            NewUserByDays userResponse = new NewUserByDays();
            userResponse.setUserID(user.getUserID());
            userResponse.setUserName(user.getUserName());
            userResponse.setEmail(user.getEmail());
            userResponse.setCreated(user.getCreated());
            userResponse.setFullName(user.getFullName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            listUserResponse.add(userResponse);
        }
        List<NewUserByDays> listResponse = listUserResponse.stream()
                .sorted(Comparator.comparing(NewUserByDays::getCreated).reversed())
                .collect(Collectors.toList());
        return listResponse;
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

    public String generateTokenEmail(String email) {
        User users = userRepository.findByEmail(email);
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + JWT_EXPIRATION);
        //Tao chuoi JWT tu userName
        return Jwts.builder().setSubject(users.getUserName())
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("daohung.rks@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        //tra lai thong tin username
        return claims.getSubject();
    }
}
