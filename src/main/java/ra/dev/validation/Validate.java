package ra.dev.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static final String FORMAT_EMAIL = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    public static final String FORMAT_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,39}$";
    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(FORMAT_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(FORMAT_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
