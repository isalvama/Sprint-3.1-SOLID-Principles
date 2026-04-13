# Case I: Applied Changes

Case I illustrates a scenario where the **Interface Seggregation Principle (DIP)** is clearly violated. Below is a detailed analysis of the problems identified and the refactorings performed to resolve them.

---

## 1. The Core Issue: What was wrong?

The primary architectural errors were concentrated in the `java.MachineActions` interface, which acted as a "fat interface".

```java
public interface java.MachineActions {
    void turnOn();
    void turnOff();
    void heat();
    void cool();
    void wash();
}
```
This interface bundled all possible actions into a single contract. As a result, every implementing class was forced to define logic for methods that were irrelevant to its purpose:

* **The `java.AirConditioner` Class**: had to implement `wash()` method.
* **The `java.WashingMachine` Class**: had to implement `heat()`and `cool()` methods.


## 2. Why does it violate the principle? 🚩

Both `java.AirConditioner` and `java.WashingMachine` classes implemented the same interface: `java.MachineActions` even if their abilities are fundamentally different.

The problem lies in the **excessive number of responsibilities** assigned to `java.MachineActions`. Not all machines can `heat()`, `cool()`, or `wash()`. By forcing these classes to implement the entire set, we created "polluted" interfaces with unnecessary dependencies, which is exactly what the Interface Segregation Principle seeks to avoid.

---

## 3. What solution was applied and why? ✅

The refactoring focused on dividing the methods of `java.MachineActions` into smaller, more specific interfaces. Here is the breakdown of the changes:

1.  **Establishment of `java.MachineActions` as the super-interface**:
    We removed all the specific methods from the `java.MachineActions` interface, leaving only ones that are shared by all machines: `turnOn()` and `turnOf()`.

```java
public interface java.AirActions extends java.MachineActions {
    void heat();
    void cool();
}
```

2.  **Creation of `java.AirActions` interface**:
    The `java.AirActions` interface now holds only the specific methods to climate control: `heat()` and `cold()`.`

    It extends from the `java.MachineActions` interface, inheriting its abstract methods related to the basic power controls: `turnOn()` and `turnOf`.

```java
public interface java.AirActions extends java.MachineActions {
    void heat();
    void cool();
}
```

3.  **Creation of `java.WashingActions` interface**:
    The `java.WashingActions` interface now holds the specific method a washing machine can implement: `wash()`.

    Like the previous one, it also extends from the `java.MachineActions` interface and therefore inherits its abstract methods to include power functionality: `turnOn()` and `turnOf`.

```java
import java.MachineActions;

public interface java.WashingActions extends MachineActions {
    void wash();
}
```

4.  **`Implementation of specific interfaces to each corresponding class`**:
    We updated the `java.AirConditioner` and `java.WashingMachine` classes to implement only the interface that corresponds to their real-world behavior:

* **The `java.AirConditioner` Class implements `java.AirActions` interface**: it does not have to implement the `wash()` method, but only `heat()`and `cool()` ones.
* **The `java.WashingMachine` Class implements `java.WashingActions` interface**: it does not have to implement the `heat()` and `cool()` methods, but only the `wash()` one.
* **Both `java.AirConditioner` and `java.WashingMachine` classes have to implement `turnOn()` and `turnOf()` methods**: they both implement the interfaces that inherit from `java.MachineActions`.

```java
import java.AirActions;

public class java.AirConditioner implements AirActions {

    @Override
    public void turnOn() {
        System.out.println("java.AirConditioner is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("java.AirConditioner is OFF");
    }

    @Override
    public void heat() {
        System.out.println("java.AirConditioner is heating.");
    }

    @Override
    public void cool() {
        System.out.println("java.AirConditioner is cooling.");
    }
}
```

```java
import java.AirActions;

public class java.AirConditioner implements AirActions {

    @Override
    public void turnOn() {
        System.out.println("java.AirConditioner is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("java.AirConditioner is OFF");
    }

    @Override
    public void heat() {
        System.out.println("java.AirConditioner is heating.");
    }

    @Override
    public void cool() {
        System.out.println("java.AirConditioner is cooling.");
    }
}
```

```java
public class java.WashingMachine implements java.WashingActions {

    @Override
    public void turnOn() {
        System.out.println("java.WashingMachine is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("java.WashingMachine is OFF");
    }

    @Override
    public void wash() {
        System.out.println("java.WashingMachine is washing clothes.");
    }
}
```


> **Why this is better?** By using smaller, specific interfaces, it is easier to reuse them across different parts of the system without causing compatibility issues, resulting in a more flexible and maintainable design. Classes become simpler and more coherent; we avoid absurd or unnecessary implementations and promote the use of composition over forced inheritance.
