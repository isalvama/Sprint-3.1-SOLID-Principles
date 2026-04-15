package D.person.service;

import D.db.DataBase;
import D.db.MySql;
import D.person.model.Person;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicePersonTest implements WithAssertions {
    private final Person maria = new Person("Maria");
    private final DataBase mySqlDb = new MySql();
    private final ServicePerson servicePerson = new ServicePerson(mySqlDb);
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void savePerson_whenPassingNullPerson_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
                    servicePerson.savePerson(null);
                }
        );
        String EMessage = exception.getMessage();
        assertEquals("the object being saved can not be null", EMessage);
    }

    @Test
    void savePerson_whenCallingMethod_printsExpectedMessage(){
        servicePerson.savePerson(maria);
        assertThat(outputStream.toString().trim())
                .isEqualTo("Saving " + maria.getClass().getName() + " in SQL database ok...");
    }
}