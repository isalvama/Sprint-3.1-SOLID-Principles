package I;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



class AirConditionerTest implements WithAssertions {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final AirConditioner airConditioner = new AirConditioner();

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void turnOn_whenCallingMethod_printsExpectedMessage(){
        airConditioner.turnOn();
        assertThat(outputStream.toString().trim())
                .isEqualTo("AirConditioner is ON");
    }

    @Test
    void turnOff_whenCallingMethod_printsExpectedMessage(){
        airConditioner.turnOff();
        assertThat(outputStream.toString().trim())
                .isEqualTo("AirConditioner is OFF");
    }

    @Test
    void heating_whenCallingMethod_printsExpectedMessage(){
        airConditioner.heat();
        assertThat(outputStream.toString().trim())
                .isEqualTo("AirConditioner is heating.");
    }

    @Test
    void wash_whenCallingMethod_printsExpectedMessage(){
        airConditioner.cool();
        assertThat(outputStream.toString().trim())
                .isEqualTo("AirConditioner is cooling.");
    }
}