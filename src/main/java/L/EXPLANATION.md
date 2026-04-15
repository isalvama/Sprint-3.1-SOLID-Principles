# Case L: Applied Changes

Case L illustrates a scenario where the **Liskov Substitution Principle (DIP)** is clearly violated. Below is a detailed analysis of the problems identified and the refactorings performed to resolve them.

---

## 1. The Core Issue: What was wrong?

The primary architectural errors were concentrated in the `Character` super class.

Specifically, the `Character` super-class holded the `takeDamage()` method that could only be implemented by `Warrior` class but not by `Ghost` class:

```java
public class Character {
    public void attack() {
        System.out.println("The character attacks with a weapon.");
    }

    public void takeDamage(int points) {
        System.out.println("The character takes " + points + " points of damage.");
    }
}
```

Consequently, the `Ghost` class, which inherits from `Character`, was forced to implement `takeDamage()` method even though ghosts are conceptually immune to physical damage. This led to a "hack" implementation:

```java
public class Ghost extends Character {
    @Override
    public void attack() {
        System.out.println("The ghost casts a spooky spell.");
    }

    @Override
    public void takeDamage(int points) {
        throw new UnsupportedOperationException("A ghost cannot take physical damage!");
    }
}
```


## 2. Why does it violate the principle? 🚩

The inheritance design of the mentioned classes break the **Liskov Substitution Principle (DIP)** because the sub-class `Ghost` can not properly substitute it parent `Character` class, as long as it can not implement `takeDamage()` action.

According to LSP, a program should be able to use any subclass in place of its parent class without knowing the difference and without the program crashing. In this case, if a developer treats `Ghost` as a `Character` and calls `takeDamage()` the program brakes as the class does not allow taking damage.

The subclass **weakens** the contract established by the superclass.

---

## 3. What solution was applied and why? ✅

The refactoring focuses on decoupling the damage logic from the inheritance tree by using the **Strategy Pattern, introducing an abstraction layer. Here is the breakdown of the changes:

1.  **Creation of the `Damage` Interface**:
    We defined a `Damage` interface with an abstract `takeDamage()` method to act as an abstraction. This allows us to define different implementations that act as different "behaviors" depending on the type of `Character` subclass.

```java
public interface Damage {
    public void takeDamage(int points);
}
```

2.  **Defining java.Damage Strategies**
    We created two concrete strategies: `NotPermittedDamageTakingStrategy` and `PermittedDamageTakingProcedure`**:
    We defined permitted and not permitted damage strategies through both mentioned classes (that implement `Damage` interface) by defining the logic of `takeDamage()` method. The `PermittedDamageTakingProcedure` works for characters that take damage and `NotPermittedDamageTakingProcedure` work as a "Null Object" or "Immunity" strategy

```java
public class PermittedDamageTakingStrategy implements Damage {
    private final String characterClassName;
    private final int damageResistanceFactor;

    public PermittedDamageTakingStrategy(String characterClassName, int damageResistanceFactor) {
        if (damageResistanceFactor < 1) throw new IllegalArgumentException("damageReductionFactor can not be less than 1");
        this.characterClassName = Objects.requireNonNull(characterClassName, "characterClassName can not be null");
        this.damageResistanceFactor = damageResistanceFactor;
    }

    @Override
    public void takeDamage(int points) {
        System.out.println("The " + characterClassName  + " resists and only takes " + (points / damageResistanceFactor) + " points of damage.");
    }
}
```

```java
public class NotPermittedDamageTakingStrategy implements Damage {
    private final String characterClassName;

    public NotPermittedDamageTakingStrategy(String characterClassName) {
        this.characterClassName = Objects.requireNonNull(characterClassName, "characterClassName can not be null");
    }
    @Override
    public void takeDamage(int points) {
        System.out.println("A " + characterClassName + " cannot take physical damage!");
    }
}
```


3. **Refactoring the `Character` Base Class**:

   We removed the hardcoded damage logic and replaced it with **Dependency Injection**. 

   We added a `Damage` type attribute to the `Character` super-class (`damageTakingStrategy`) so the class holds a reference to `Damage` strategy. This strategy is instantiated by passing any class that implements it as a parameter in the constructor.

    The `Character` class now implements the `Database` interface. It is no longer a "standalone" dependency but one of many possible implementations of the abstraction.

```java
public class Character {
    private final String characterName;
    private final int attackStrengthInPoints;
    private final Damage damageTakingStrategy;

    public Character (String characterName, int attackStrengthPoints, Damage damageTakingProcedure) {
        if (attackStrengthPoints < 0) throw new IllegalArgumentException("attackStrengthPoints can not be negative");
        this.characterName = Objects.requireNonNull(characterName, "characterName can not be null");
        this.attackStrengthInPoints = attackStrengthPoints;
        this.damageTakingStrategy = Objects.requireNonNull(damageTakingProcedure, "damageTakingProcedure can not be null");
    }

    public void attack() {
        System.out.println("The character attacks with a weapon of " + this.getAttackStrengthInPoints() + " points.");
    }

    public void takeDamage (int points){
        damageTakingStrategy.takeDamage(points);
    }

    public int getAttackStrengthInPoints() {
        return attackStrengthInPoints;
    }
}
```

4.  **Specific Implementations**:
    `Warrior` and `Ghost` in `Character` now provide their specific strategy to the super constructor.
    * **java.Warrior**: Receives the `PermittedDamageTakingStrategy`.
    * **java.Ghost**: Receives the `NotPermittedDamageTakingStrategy`.

```java
public class Ghost extends Character {
    public Ghost (String characterName, int attackStrengthPoints){
        super(characterName, attackStrengthPoints, new NotPermittedDamageTakingStrategy(Ghost.class.getSimpleName()));
    }

    @Override
    public void attack() {
        System.out.println("The ghost casts a spooky spell of " + this.getAttackStrengthInPoints() + " points.");
    }
}
```

```java
public class Warrior extends Character {

    public Warrior(String characterName, int attackStrengthPoints) {
        super(characterName, attackStrengthPoints, new PermittedDamageTakingStrategy(Warrior.class.getSimpleName(), 2));
    }

    @Override
    public void attack() {
        System.out.println("The warrior strikes with a sword of " + this.getAttackStrengthInPoints() + " points.");
    }
}
```


> **Why this is better?** By applying abstraction and the **Strategy Pattern** to make the program more flexible, subclasses now strictly follow the contract of the superclass. A `Ghost` is now a perfectly valid `Character` that can handle a `takeDamage()` call without crashing the system. We have achieved true **Substitutability**, making the system more robust, predictable, and easier to extend.

