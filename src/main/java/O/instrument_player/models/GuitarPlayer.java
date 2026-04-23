package O.instrument_player.models;

import O.instrument_player.InstrumentPlayer;

public class GuitarPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🎸 Strumming the guitar");
    }
}
