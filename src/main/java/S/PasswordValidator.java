package S;


public class PasswordValidator implements UserValidator {

    public void validateUserData(User user) {
        if (user.getPassword() == null || user.getPassword().length() < 8 || !user.getPassword().matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain an uppercase letter.");
        }
    }
}
