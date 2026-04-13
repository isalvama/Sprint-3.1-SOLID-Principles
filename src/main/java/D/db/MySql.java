package D.db;

public class MySql implements DataBase {

    @Override
    public void saveObject(Object object) {
        System.out.println("Saving " + object.getClass().getName() + " in SQL database ok...");
    }
}
