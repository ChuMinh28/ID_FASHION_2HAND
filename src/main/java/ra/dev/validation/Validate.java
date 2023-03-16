package ra.dev.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(Const.FORMAT_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(Const.FORMAT_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
