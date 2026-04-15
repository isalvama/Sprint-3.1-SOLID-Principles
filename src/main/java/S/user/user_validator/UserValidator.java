package S.user.user_validator;

import S.user.model.User;

public interface UserValidator {
    void validateUserData(User user) throws IllegalArgumentException, NullPointerException;
}
