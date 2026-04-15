package S.user.registration;
import S.user.model.User;
import S.communication_sender.CommunicationSender;
import S.user.user_confirmation.UserConfirmationService;
import S.user.user_validator.UserValidator;

import java.util.Objects;

public class RegistrationService {
    private final UserValidator userValidator;
    private final CommunicationSender communicationSender;
    private final UserConfirmationService userConfirmation;

    public RegistrationService (UserValidator userValidator, CommunicationSender communicationSender, UserConfirmationService userConfirmation) {
        this.userValidator = Objects.requireNonNull(userValidator, "userValidator can not be null");
        this.communicationSender = Objects.requireNonNull(communicationSender, "communicationSender cannot be null");
        this.userConfirmation = Objects.requireNonNull(userConfirmation, "userConfirmation cannot be null");
    }

    public void registerUser (User user) {
        Objects.requireNonNull(user, "User cannot be null");
        try {
            userValidator.validateUserData(user);
            communicationSender.sendCommunicationToUser(user);
            userConfirmation.checkUserConfirmation();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error in registration process:" + e.getMessage(), e);
        }
    }
}
