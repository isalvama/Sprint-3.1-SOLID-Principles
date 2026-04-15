package O;

import O.concert.ConcertService;
import O.instrument_player.DrumsPlayer;
import O.instrument_player.GuitarPlayer;
import O.instrument_player.PianoPlayer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ConcertServiceTest implements WithAssertions {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ConcertService concertService = new ConcertService();
    private final DrumsPlayer drumsPlayer = new DrumsPlayer();
    private final GuitarPlayer guitarPlayer = new GuitarPlayer();
    private final PianoPlayer pianoPlayer = new PianoPlayer();

    @BeforeEach
    void setUp(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    void playInstruments_whenPassingInstrumentPlayersAsArg_printsExpectedMessage(){
        concertService.playInstruments(List.of(drumsPlayer, guitarPlayer, pianoPlayer));
        assertThat(outputStream.toString().trim())
                .isEqualTo("🥁 Beating the drums\n\uD83C\uDFB8 Strumming the guitar\n🎹 Playing the piano");
    }

    @Test
    void playInstruments_whenPassingNullArgument_throwsNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            concertService.playInstruments(null);});
        assertTrue(exception.getMessage().contains("instrumentPlayers can not be null"));
    }

    @Test
    void playInstruments_whenPassingEmptyList_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            concertService.playInstruments(List.of());});
        assertTrue(exception.getMessage().contains("instrumentPlayers can not be empty"));
    }

}