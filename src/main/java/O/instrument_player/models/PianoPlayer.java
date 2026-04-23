package O.instrument_player.models;

import O.instrument_player.InstrumentPlayer;

public class PianoPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🎹 Playing the piano");
    }
}