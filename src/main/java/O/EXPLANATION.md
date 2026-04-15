
# Case O: Open-Closed Principle (OCP)

Case *O* presents a classic violation of the *Open-Closed Principle (OCP)*. This principle states that software entities (classes, modules, functions) should be *open for extension but closed for modification*. Below is the detailed analysis of the problem and the refactored solution.

## The Core Issue: Hardcoded Conditionals

The primary architectural error in the original code was the reliance on an `if-else if` chain within the `InstrumentPlayer class to handle different behaviors.

```java
public class InstrumentPlayer {
    public void play(String instrument) {
        if ("guitar".equals(instrument)) {
            System.out.println("🎸 Strumming the guitar");
        } else if ("drums".equals(instrument)) {
            System.out.println("🥁 Beating the drums");
        } else if ("piano".equals(instrument)) {
            System.out.println("🎹 Playing the piano");
        } else {
            System.out.println("🔇 Unknown instrument");
        }
    }

    public static void main(String[] args) {
        InstrumentPlayer player = new InstrumentPlayer();
        player.play("guitar");
        player.play("drums");
        player.play("piano");
    }
}
```
The `play` method evaluated a `String` parameter to decide which action to perform. All the logic for every single instrument was centralized inside one method.

## Why does it violate the principle?

The original design failed the OCP for several reasons:

1. **Closed for Extension:** If the system needed to support a new instrument (e.g., a `Violin` or a `Flute`), the developer would be forced to open the `InstrumentPlayer` class and add another `else if` block.
2. **Risk of Regression:** The system could not grow or adapt without modifying existing code, violating the Open/Closed Principle.
3. **Lack of Cohesion:** It was impossible to perform unit tests on `ServicePerson without involving a real MySQL instance, as the dependency was hardcoded.

## Applied Solution: Polymorphism and Abstraction

The refactoring eliminated the conditional logic by leveraging *Polymorphism* and *Abstraction*.

### Step 1: Interface Creation (The Abstraction)

We converted `InstrumentPlayer` into an interface. This establishes a universal contract: any instrument player must know how to `play()`.

```java
public interface InstrumentPlayer {
    void play();
}
```

### Step 2: Concrete Implementations (The Extension)

Each instrument behavior was encapsulated into its own specific class (`GuitarPlayer`, `DrumsPlayer`, `PianoPlayer`).
If we want to add a `ViolinPlayer` tomorrow, we just create a new class. *We do not need to modify any existing code*.

```java
public class GuitarPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🎸 Strumming the guitar");
    }
}
```

```java
public class DrumsPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🥁 Beating the drums");
    }
}
```

```java
public class PianoPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🎹 Playing the piano");
    }
}
```

### Step 3: The ConcertService Class (The Execution)

We created a new `ConcertService` class that delegates the action to the objects. It accepts a `List<InstrumentPlayer> and loops through them.

```java
public class ConcertService {

    public void playInstruments(List<InstrumentPlayer> instrumentPlayers){
        Objects.requireNonNull(instrumentPlayers, "instrumentPlayers can not be null");
        if (instrumentPlayers.isEmpty()) throw new IllegalArgumentException("instrumentPlayers can not be empty");
        instrumentPlayers.forEach(InstrumentPlayer::play);
    }
}
```

The `ConcertService` class doesn't know what instrument is playing; it only knows that the object implements `play()`.

> **Why is this approach superior?** By removing rigid `if-else` structures and implementing Polymorphism, the system is now strictly compliant with the Open-Closed Principle. To add a new instrument, developers simply write a new class (Open for extension) without altering the `Concert` or existing `Player` classes (Closed for modification). This results in a highly scalable, safe, and easily testable architecture.
