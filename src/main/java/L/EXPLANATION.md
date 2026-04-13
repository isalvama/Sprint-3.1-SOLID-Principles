# Case L: Applied Changes

Case L illustrates a scenario where the **Liskov Substitution Principle (DIP)** is clearly violated. Below is a detailed analysis of the problems identified and the refactorings performed to resolve them.

---

## 1. The Core Issue: What was wrong?

The primary architectural errors were concentrated in the `java.Character` super class.

Specifically, the `java.Character` super-class holded the `takeDamage()` method that could only be implemented by `java.Warrior` class but not by `java.Ghost` class:

```java
public class java.Character {
    public void attack() {
        System.out.println("The character attacks with a weapon.");
    }

    public void takeDamage(int points) {
        System.out.println("The character takes " + points + " points of damage.");
    }
}
```

Consequently, the `java.Ghost` class, which inherits from `java.Character`, was forced to implement `takeDamage()` method even though ghosts are conceptually immune to physical damage. This led to a "hack" implementation:

```java
import java.Character;

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

The inheritance design of the mentioned classes break the **Liskov Substitution Principle (DIP)** because the sub-class `java.Ghost` can not properly substitute it parent `java.Character` class, as long as it can not implement `takeDamage()` action.

According to LSP, a program should be able to use any subclass in place of its parent class without knowing the difference and without the program crashing. In this case, if a developer treats `java.Ghost` as a `java.Character` and calls `takeDamage()` the program brakes as the class does not allow taking damage.

The subclass **weakens** the contract established by the superclass.

---

## 3. What solution was applied and why? ✅

The refactoring focuses on decoupling the damage logic from the inheritance tree by using the **Strategy Pattern, introducing an abstraction layer. Here is the breakdown of the changes:

1.  **Creation of the `java.Damage` Interface**:
    We defined a `java.Damage` interface with an abstract `takeDamage()` method to act as an abstraction. This allows us to define different implementations that act as different "behaviors" depending on the type of `java.Character` subclass.

```java
public interface java.Damage {
    public void takeDamage(int points);
}
```

2.  **Defining java.Damage Strategies**
    We created two concrete strategies: `java.NotPermittedDamageTakingProcedure` and `java.PermittedDamageTakingProcedure`**:
    We defined permitted and not permitted damage strategies through both mentioned classes (that implement `java.Damage` interface) by defining the logic of `takeDamage()` method. The `java.PermittedDamageTakingProcedure` works for characters that take damage and `java.NotPermittedDamageTakingProcedure` work as a "Null Object" or "Immunity" strategy

```java
import java.Damage;

public class PermittedDamageTakingProcedure implements Damage {
    private final String characterClassName;
    private final int damageReductionFactor;

    public java.PermittedDamageTakingProcedure(String characterClassName, int damageReductionFactor) {
        this.characterClassName = characterClassName;
        this.damageReductionFactor = damageReductionFactor;
    }

    @Override
    public void takeDamage(int points) {
        System.out.println("The " + characterClassName + " resists and only takes " + (points / damageReductionFactor) + " points of damage.");
    }
}
```

```java
import java.Damage;

public class NotPermittedDamageTakingProcedure implements Damage {
    private final String characterClassName;

    public java.NotPermittedDamageTakingProcedure(String characterClassName) {
        this.characterClassName = characterClassName;
    }

    @Override
    public void takeDamage(int points) {
        System.out.println("A " + characterClassName + " cannot take physical damage!");
    }
}
```


3. **Refactoring the `java.Character` Base Class**:

   We removed the hardcoded damage logic and replaced it with **Dependency Injection**. 

   We added a `java.Damage` type attribute to the `java.Character` super-class (`damageTakingProcedure`) so the class holds a reference to `java.Damage` strategy. This strategy is instantiated by passing any class that implements it as a parameter in the constructor.

    The `java.Character` class now implements the `Database` interface. It is no longer a "standalone" dependency but one of many possible implementations of the abstraction.

```java
import java.Damage;

public class Character {
    private final String characterName;
    private final int attackStrengthInPoints;
    private final Damage damageTakingProcedure;

    public java.Character(String characterName, int attackStrengthPoints, Damage damageTakingProcedure) {
        this.characterName = characterName;
        this.attackStrengthInPoints = attackStrengthPoints;
        this.damageTakingProcedure = damageTakingProcedure;
    }

    public void attack() {
        System.out.println("The character attacks with a weapon of " + this.getAttackStrengthInPoints() + " points.");
    }

    public void takeDamage(int points, int damageReductionFactor) {
        damageTakingProcedure.takeDamage(points);
    }

    public int getAttackStrengthInPoints() {
        return attackStrengthInPoints;
    }
}
```

4.  **Specific Implementations**:
    `java.Warrior` and `java.Ghost` in `java.Character` now provide their specific strategy to the super constructor.
    * **java.Warrior**: Receives the `java.PermittedDamageTakingProcedure`.
    * **java.Ghost**: Receives the `java.NotPermittedDamageTakingProcedure`.

```java
import java.Character;
import java.NotPermittedDamageTakingProcedure;

public class Ghost extends Character {
    public java.Ghost(String characterName, int attackStrengthPoints) {
        super(characterName, attackStrengthPoints, new NotPermittedDamageTakingProcedure(java.Ghost.class.getName()));
    }

    @Override
    public void attack() {
        System.out.println("The ghost casts a spooky spell of " + this.getAttackStrengthInPoints() + " points.");
    }
}
```

```java
import java.Character;
import java.PermittedDamageTakingProcedure;

public class Warrior extends Character {

    public java.Warrior(String characterName, int attackStrengthPoints) {
        super(characterName, attackStrengthPoints, new PermittedDamageTakingProcedure(java.Warrior.class.getName(), 2));
    }

    @Override
    public void attack() {
        System.out.println("The warrior strikes with a sword of " + this.getAttackStrengthInPoints() + " points.");
    }
}
```


> **Why this is better?** By applying abstraction and the **Strategy Pattern** to make the program more flexible, subclasses now strictly follow the contract of the superclass. A `java.Ghost` is now a perfectly valid `java.Character` that can handle a `takeDamage()` call without crashing the system. We have achieved true **Substitutability**, making the system more robust, predictable, and easier to extend.

