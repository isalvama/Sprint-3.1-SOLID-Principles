# SOLID Principles Refactoring Showcase

This project is a comprehensive guide to implementing the SOLID principles in Java. It demonstrates how to identify "code smells" and architectural flaws, and how to transform them into a scalable, maintainable, and testable codebase.

The project is organized into five packages, each corresponding to a specific principle:

* **`S`**: Single Responsibility Principle
* **`O`**: Open-Closed Principle
* **`L`**: Liskov Substitution Principle
* **`I`**: Interface Segregation Principle
* **`D`**: Dependency Inversion Principle

# Project Structure

```
src/
 └── D/ (Dependency Inversion Principle)
     └── db 
     └── person
         └── model
             └── Person
         └── service
             └── ServicePerson
 └── I/ (Interface Segregation Principle)
     └── machines
         └── actions
             └── AirActions
             └── MachineActions
             └── WashingActions
         └── models
             └── AirConditioner
             └── WashingMachine
 └── L/ (Liskov Substitution Principle)
     └── character
         └── damage_strategy
             └── Damage
             └── NotPermittedDamage
             └── PermittedDamage
         └── models
             └── Character
             └── Ghost
             └── Warrior
 └── O/ (Open-Closed Principle)
     └── concert_service
         └── ConcertService
     └── instrument_player
         └── models
             └── DrumsPlayer
             └── GuitarPlayer
             └── PianoPlayer
         └── InstrumentPlayer
 └── S/ (Single Responsibility Principle)
     └── communication_sender
         └── CommunicationSender
         └── EmailSenderService
     └── user
         └── model
             └── User
         └── registration
             └── RegistrationService
         └── user_confirmation
             └── UserConfirmationService
         └── user_validator
             └── CompositeUserValidator
             └── EmailValidator
             └── PasswordValidator
             └── UserValidator
```

# Analysis of Principles

## S - Single Responsibility Principle (SRP)

"A class should have one, and only one, reason to change."

* **The Issue**: The User class was a "God Object," handling data modeling, email validation, password rules, and communication logic.
* **The Solution**: Decomposed the logic into specialized classes:
   * **`UserValidator`** (Interface) & **`CompositeUserValidator`** for data rules.
   * **`CommunicationSender`** (Interface) for notifying users.
   * **`RegistrationService`** to orchestrate the process using Dependency Injection.
* **Benefit**: Highly modular and testable components.

## O - Open-Closed Principle (OCP)

"Software entities should be open for extension, but closed for modification."

* **The Issue**: `InstrumentPlayer` used a rigid if-else chain to play different instruments. Adding a new instrument required modifying the existing source code.
* **The Solution**: Applied Polymorphism.
   * Created an `InstrumentPlayer` interface.
   * Implemented specific classes (`GuitarPlayer`, `PianoPlayer`, etc.).

* **Benefit: New instruments can be added by creating new classes without touching the `ConcertService` logic. 

## L - Liskov Substitution Principle (LSP)

"Subtypes must be substitutable for their base types."

* **The Issue**: The `Ghost` class inherited from `Character` but threw an exception in `takeDamage()` because ghosts are immune. This broke the contract of the parent class.
* **The Solution**: Implemented the **Strategy Pattern**.
   * Extracted damage logic into a `Damage` interface.
   * `Character` now holds a `DamageTakingStrategy`.
   * `Warrior` uses a `PermittedDamage` strategy, while `Ghost` uses a `NotPermittedDamage` strategy.
* **Benefit**: True substitutability; the system no longer crashes when treating a `Ghost` as a `Character`.

## I - Interface Segregation Principle (ISP)

"No client should be forced to depend on methods it does not use."

* **The Issue**: `MachineActions` was a "Fat Interface" forcing `WashingMachine` to implement `cool()` and `AirConditioner` to implement `wash()`.
* **The Solution**: Segregated the interface into smaller, specific contracts:
   * `AirActions` (heat/cool).
   * `WashingActions` (wash).
   * Both inherit from a basic `MachineActions` (turnOn/turnOff).
* **Benefit**: Classes only implement methods relevant to their real-world behavior.

## D - Dependency Inversion Principle (DIP)

"Depend upon abstractions, not concretions."

* **The Issue**: `ServicePerson` was tightly coupled to a concrete `MySql` class, making it impossible to switch databases or perform unit testing.
* **The Solution**: Introduced an abstraction layer.
   * Created a `DataBase` interface.
   * `ServicePerson` now receives any DataBase implementation via its constructor.
* **Benefit**: The high-level business logic is decoupled from low-level storage details.
   

# Requirements

* **Java SDK**: 17 or higher.
* **IDE**: IntelliJ IDEA / Eclipse / VS Code.

