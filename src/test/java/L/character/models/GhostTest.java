package L.character.models;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GhostTest implements WithAssertions {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final Ghost Ghost = new Ghost("Windsor Castle Ghost", 30);

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
                    new Ghost(null, 20);
                }
        );
        String exceptionMessage = exception.getMessage();
        assertEquals("characterName can not be null", exceptionMessage);
    }

    @ParameterizedTest
    @CsvSource({"-1", "-10", "-23", "-187", })
    void constructor_whenPassingNullAsCharacterNameArgument_throwsNullPointerException(int points) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    new Ghost("character", points);
                }
        );
        String exceptionMessage = exception.getMessage();
        assertEquals("attackStrengthPoints can not be negative", exceptionMessage);
    }

    @Test
    void attack_whenCallingMethod_printsExpectedMessage(){
        Ghost.attack();
        assertThat(outputStream.toString().trim())
                .isEqualTo("The ghost casts a spooky spell of 30 points.");
    }

    @Test
    void takeDamage_whenCallingMethod_printsExpectedMessage(){
        Ghost.takeDamage(20);
        assertThat(outputStream.toString().trim())
                .isEqualTo("A Ghost cannot take physical damage!");
    }
}