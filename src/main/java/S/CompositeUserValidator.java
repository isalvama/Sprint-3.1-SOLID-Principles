package S;

import java.util.List;
import java.util.Objects;

public class CompositeUserValidator implements UserValidator {

    private List<UserValidator> validators;

    public CompositeUserValidator (List<UserValidator> validators){
        if (validators.isEmpty()){throw new IllegalArgumentException("userValidators cannot be empty");}
        this.validators = Objects.requireNonNull(validators, "validators List can not be null");
    }

    @Override
    public void validateUserData(User user) {
        validators.forEach(v -> v.validateUserData(user));
    }
}
