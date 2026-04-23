package O.instrument_player.models;

import O.instrument_player.InstrumentPlayer;

public class DrumsPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🥁 Beating the drums");
    }
}
