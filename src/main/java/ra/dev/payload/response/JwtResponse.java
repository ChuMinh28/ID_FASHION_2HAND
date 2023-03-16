package ra.dev.payload.response;

import java.util.List;

public class JwtResponse {
    private int userId;
    private String token;
    private String type = "Bearer";
    private String userName;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private List<String> listRoles;

    public JwtResponse(String token, Integer userId, String userName, String email, String phone, String fullName, String address, List<String> listRoles) {
        this.userId = userId;
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.listRoles = listRoles;

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserID(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<String> listRoles) {
        this.listRoles = listRoles;
    }
}
