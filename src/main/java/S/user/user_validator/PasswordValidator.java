package S.user.user_validator;

import S.user.model.User;

public class PasswordValidator implements UserValidator {

    public void validateUserData(User user) {
        if (user.getPassword() == null) throw new IllegalArgumentException("Password can not be null");
        if (user.getPassword().length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        if (!user.getPassword().matches(".*[A-Z].*"))
            throw new IllegalArgumentException("Password must contain an uppercase letter.");
    }
}
