package S;


public class EmailValidator implements UserValidator {

    @Override
    public void validateUserData(User user){
        if (user.getEmail() == null || !user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }
}
