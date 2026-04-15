package L.character.models;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest implements WithAssertions {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final Warrior englandArmywarrior = new Warrior("England Army Warrior", 50);

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void constructor_whenPassingNullCharacterNameArgument_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
                    new Warrior(null, 20);
                }
        );
        String exceptionMessage = exception.getMessage();
        assertEquals("characterName can not be null", exceptionMessage);
    }

    @ParameterizedTest
    @CsvSource({"-1", "-10", "-23", "-187", })

    void constructor_whenPassingNullAsCharacterNameArgument_throwsNullPointerException(int points) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    new Warrior("character", points);
                }
        );
        String exceptionMessage = exception.getMessage();
        assertEquals("attackStrengthPoints can not be negative", exceptionMessage);
    }

    @Test
    void attack_whenCallingMethod_printsExpectedMessage(){
        englandArmywarrior.attack();
        assertThat(outputStream.toString().trim())
                .isEqualTo("The warrior strikes with a sword of 50 points.");
    }

    @Test
    void takeDamage_whenCallingMethod_printsExpectedMessage(){
        englandArmywarrior.takeDamage(40);
        assertThat(outputStream.toString().trim())
                .isEqualTo("The Warrior resists and only takes 20 points of damage.");
    }


}