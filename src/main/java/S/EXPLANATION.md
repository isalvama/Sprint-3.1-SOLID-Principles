# Case S: Applied Changes

Case S illustrates a scenario where the **Single Responsibility (SIP)** is clearly violated. Below is a detailed analysis of the problems identified and the refactorings performed to resolve them.

## 1. The Core Issue: What was wrong and why does it violate the principle? 🚩

The primary architectural errors were concentrated in the one and only existing class in the program: `User` class.

Specifically, the `User` class was handling multiple responsibilities: it acted as a **model** (domain responsibility) and contained the **logic** behind the registration of a `User` instance (service responsibility)

Moreover, the `register()` method itself also broke the Single Responsibility Principle. It performed three different tasks:
**Validation**: it validated the data format of the `User` instance `email` and `password` attributes.
**Communication**: it handled sending a confirmation email once validations passed.
**Process Logic**: it held the responsibility of checking if the user had confirmed the registration.

```java
public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void register() {

        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address.");
        }

        if (password == null || password.length() < 8 || !password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain an uppercase letter.");
        }

        System.out.println("📧 Sending confirmation email to: " + email);

        boolean userConfirmed = true;
        if (!userConfirmed) {
            System.out.println("⚠️ User did not confirm registration.");
            return;
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
```

## 2. What solution was applied and why? ✅

The refactoring focuses on decoupling the responsibilities held by the `User` class and redistributing them into specialized classes. We also abstracted some of the actions performed by the `register()` method to make the system more flexible and open to future changes.

Here is the breakdown of the changes:

1.  **Validation Responsabilities**:

Several classes and interfaces were created to hold the validation logic previously residing in `User` class:

* **`UserValidator` interface as an abstraction of the data validation**: It defines a `validateUserData(User user)` method. This acts as an abstraction that allows establishing different validation implementations according to the type of data being validated and the requirements needed.

```java
public interface UserValidator {
   void validateUserData(User user) throws IllegalArgumentException, NullPointerException;
}
```

* **`EmailValidator` class**: Specifically handles email validation by implementing the `UserValidator` interface by implementing the `validateUserData()` method.

```java
public class EmailValidator implements UserValidator {


   @Override
   public void validateUserData(User user){
       if (user.getEmail() == null ) throw new NullPointerException("User.email can not be null");
       if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) throw new IllegalArgumentException("Invalid email address: The address must contain a valid domain");
   }
}
```

* **`PasswordValidator` class**: In the same way, `PasswordValidator` establishes the validation logic for user passwords.
```java
public class PasswordValidator implements UserValidator {


   public void validateUserData(User user) {
       if (user.getPassword() == null) throw new IllegalArgumentException("Password can not be null");
       if (user.getPassword().length() < 8)
           throw new IllegalArgumentException("Password must be at least 8 characters long.");
       if (!user.getPassword().matches(".*[A-Z].*"))
           throw new IllegalArgumentException("Password must contain an uppercase letter.");
   }
}
```

* **`CompositeUserValidator` class as the orchestrator of all validators**: Acts as an orchestrator (Composite pattern) by holding a list of `UserValidator` objects and iterating through them, ensuring all validation rules are met.
```java
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
```

2. **Communication Sending Responsibility**:

The `CommunicationSender` interface and `EmailSender` class were created to isolate the confirmation logic.

* **`CommunicationSender` interface **: Abstracts the action of notifying a user (with its `sendCommunicationToUser()` method) allowing for different channels (Email, SMS, Push notifications) in the future.

```java
public interface CommunicationSender {
   void sendCommunicationToUser(User user);
}
```

* **`EmailSenderService` class **: is a class that implements the `CommunicationSender` interface by establishing the logic of sending the confirmation mail to the user.

```java
public class EmailSenderService implements CommunicationSender {


   public void sendCommunicationToUser(User user) {
       System.out.println("📧 Sending confirmation email to: " + user.getEmail());
   }
}
```

3. **User Confirmation Responsibility**

This logic is now handled by `UserConfirmationService` class, which focuses solely on the status of the registration confirmation with its `checkUserConfirmation()` method.

```java
public class UserConfirmationService {


   public void checkUserConfirmation() {
       boolean userConfirmed = true;
       if (!userConfirmed) {
           System.out.println("⚠️ User did not confirm registration.");
       }
   }
}
```

4. **Creation of `RegistrationService`**:

The RegistrationService class was created to orchestrate the entire process. It uses Dependency Injection through its constructor to receive the necessary components (UserValidator, CommunicationSender, and UserConfirmationService).


```java
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
```

> **Why this is better?** By leveraging Single Responsibility, Abstraction, and Polymorphism, we have created a more modular system. This allows the registration process to be extended or modified without altering the core logic of the `registerUser` method, resulting in a codebase that is both easier to maintain and highly scalable.
