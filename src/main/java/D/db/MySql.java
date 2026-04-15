package D.db;

import java.util.Objects;

public class MySql implements DataBase {
    @Override
    public void saveObject(Object object) {
        Objects.requireNonNull(object, "the object being saved can not be null");
        System.out.println("Saving " + object.getClass().getName() + " in SQL database ok...");
    }
}
