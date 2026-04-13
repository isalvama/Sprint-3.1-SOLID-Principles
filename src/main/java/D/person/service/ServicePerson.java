package D.person.service;

import D.db.DataBase;
import D.person.model.Person;

public class ServicePerson {
    DataBase dataBase;

    public ServicePerson(DataBase dataBase){
        this.dataBase = dataBase;
    }

    public void savePerson(Person person) {
        dataBase.saveObject(person);
    }
}
