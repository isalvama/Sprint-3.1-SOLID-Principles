
# Case D: Dependency Inversion Principle (DIP)

Case D illustrates a scenario where the **Dependency Inversion Principle (DIP)** was violated. Below is a detailed analysis of the architectural flaws and the refactoring applied.

## The Core Issue: Tight Coupling

The primary error was the direct coupling between the high-level logic and the low-level implementation in the `ServicePerson` and `MySql classes.

* **MySql Class:** A low-level module responsible for the physical persistence of data.
* **ServicePerson Class:** A high-level module (business logic). The problem arose because `ServicePerson` was *directly instantiating* `MySql`, tying the business logic to a specific database provider.
```java
public class MySql {
    public void savePerson(Person person) {
        System.out.println("Save person ok...");
    }

}
```
```java
public class ServicePerson {
public void savePerson(Person person) {
MySql mysql = new MySql();
mysql.savePerson(person);
}

}
```

## Why does it violate the principle?

The *DIP* states that high-level modules should not depend on low-level modules; both should depend on *abstractions*. The original design failed because:

1. **Rigidity:** `ServicePerson` was locked into `MySQL`. Switching to another database (like MongoDB or PostgreSQL) would require modifying the business logic.
2. **Lack of Scalability:** The system could not grow or adapt without modifying existing code, violating the Open/Closed Principle.
3. **Untestability:** It was impossible to perform unit tests on `ServicePerson without involving a real MySQL instance, as the dependency was hardcoded.

## Applied Solution: Abstraction & Injection

The logic was decoupled by introducing an interface and utilizing *Dependency Injection*:

* **Database Interface:** Created an abstraction with a generic `saveObject(Object obj)` method. This allows the system to handle any object type and any database provider.
* **Concrete Implementation:** The `MySql` class now implements the `Database` interface. New providers can be added without touching the service.
* **Constructor Injection:** `ServicePerson` no longer creates the database instance; it receives it as a dependency.

```java
public interface DataBase {
    public void saveObject (Object object);
}
```

```java
public class MySql implements DataBase {
    @Override
    public void saveObject(Object object) {
        Objects.requireNonNull(object, "the object being saved can not be null");
        System.out.println("Saving " + object.getClass().getName() + " in SQL database ok...");
    }
}
```

```java
public class ServicePerson {
    DataBase dataBase;

    public ServicePerson(DataBase dataBase){
        this.dataBase = dataBase;
    }

    public void savePerson(Person person) {
        dataBase.saveObject(person);
    }
}
```