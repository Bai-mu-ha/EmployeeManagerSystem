package com.hengbai.utils;

import java.util.regex.Pattern;

public class InputValidator {

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidAge(String age) {
        return age.matches("\\d+");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^\\d{11}$");
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public static boolean isValidSalary(String salary) {
        return salary.matches("^\\d+(\\.\\d+)?$");
    }

    public static boolean isValidEntryTime(String time) {
        return time.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
}
