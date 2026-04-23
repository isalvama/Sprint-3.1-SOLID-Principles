package S;

import S.communication_sender.CommunicationSender;
import S.communication_sender.EmailSenderService;
import S.user.registration.RegistrationService;
import S.user.model.User;
import S.user.user_confirmation.UserConfirmationService;
import S.user.user_validator.CompositeUserValidator;
import S.user.user_validator.EmailValidator;
import S.user.user_validator.PasswordValidator;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest implements WithAssertions {
    private final EmailValidator emailValidator = new EmailValidator();
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final CompositeUserValidator compositeUserValidator = new CompositeUserValidator(List.of(emailValidator, passwordValidator));
    private final CommunicationSender emailSender = new EmailSenderService();
    private final UserConfirmationService userConfirmationService = new UserConfirmationService();
    private final RegistrationService registrationService = new RegistrationService(compositeUserValidator, emailSender, userConfirmationService);
    private User user;

    @Test
    void constructor_whenInitializedWithNullUserValidator_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {RegistrationService registrationService = new RegistrationService(null, emailSender, userConfirmationService);});
        assertTrue(exception.getMessage().contains("userValidator can not be null"));
    }

    @Test
    void constructor_whenInitializedWithNullCommunicationSender_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {RegistrationService registrationService = new RegistrationService(compositeUserValidator, null, userConfirmationService);});
        assertTrue(exception.getMessage().contains("communicationSender cannot be null"));
    }

    @Test
    void constructor_whenInitializedWithUserConfirmationService_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {RegistrationService registrationService = new RegistrationService(compositeUserValidator, emailSender, null);});
        assertTrue(exception.getMessage().contains("userConfirmation cannot be null"));
    }

    @Test
    void registerUser_whenPassingNullAsArgument_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {registrationService.registerUser(null);});
        assertTrue(exception.getMessage().contains("User cannot be null"));
    }

    @Test
    void registerUser_whenPassingUserWithNullPassword_throwsNullPointerException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {registrationService.registerUser(new User("Albert", "albert@gmail.com", null));});
        assertTrue(exception.getMessage().contains("Password can not be null"));
    }

    @Test
    void registerUser_whenPassingUserWithPasswordOfLessThan8Letters_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {registrationService.registerUser(new User("Albert", "albert@gmail.com", "Hello"));});
        assertTrue(exception.getMessage().contains("Password must be at least 8 characters long."));
    }

    @Test
    void registerUser_whenPassingUserWithPasswordWithoutUpperCaseLetters_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {registrationService.registerUser(new User("Albert", "albert@gmail.com", "hellopassword"));});
        assertTrue(exception.getMessage().contains("Password must contain an uppercase letter."));
    }

    @Test
    void registerUser_whenPassingUserWithInvalidEmail_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {registrationService.registerUser(new User("Albert", "albertgmail.com", "Hellopassword"));});
        assertTrue(exception.getMessage().contains("Invalid email address: The address must contain a valid domain"));
    }
    @Test
    void registerUser_whenPassingUserWithNullEmail_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {registrationService.registerUser(new User("Albert", null, "Hellopassword"));});
        assertTrue(exception.getMessage().contains("User.email can not be null"));
    }

    @ParameterizedTest
    @CsvSource({"Maria,maria@gmail.com, EasyPassword", "Albert, alb456@gmail.com, HelloWorld!", "Alex Q, alexq45@gmail.com, DifficultPw"})
    void registerUser_whenPassingUserWithValidData_doesNotThrowException(String userName, String userMail, String userPassword) {
        assertDoesNotThrow(() -> {registrationService.registerUser(new User(userName, userMail, userPassword));});
    }
}



