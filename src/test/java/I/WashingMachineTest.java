package I;

import I.machine.actions.WashingActions;
import I.machine.models.WashingMachine;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class WashingMachineTest implements WithAssertions {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final WashingActions washingMachine = new WashingMachine();

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
        washingMachine.turnOn();
        assertThat(outputStream.toString().trim())
                .isEqualTo("WashingMachine is ON");
    }

    @Test
    void turnOff_whenCallingMethod_printsExpectedMessage(){
        washingMachine.turnOff();
        assertThat(outputStream.toString().trim())
                .isEqualTo("WashingMachine is OFF");
    }

    @Test
    void heating_whenCallingMethod_printsExpectedMessage(){
        washingMachine.wash();
        assertThat(outputStream.toString().trim())
                .isEqualTo("WashingMachine is washing clothes.");
    }
}