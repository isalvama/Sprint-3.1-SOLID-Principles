package S.user.user_validator;


import S.user.model.User;

public class EmailValidator implements UserValidator {

    @Override
    public void validateUserData(User user){
        if (user.getEmail() == null ) throw new NullPointerException("User.email can not be null");
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) throw new IllegalArgumentException("Invalid email address: The address must contain a valid domain");
    }
}
