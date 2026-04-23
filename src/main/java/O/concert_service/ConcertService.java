package O.concert_service;

import O.instrument_player.InstrumentPlayer;

import java.util.List;
import java.util.Objects;

public class ConcertService {

    public void playInstruments(List<InstrumentPlayer> instrumentPlayers){
        Objects.requireNonNull(instrumentPlayers, "instrumentPlayers can not be null");
        if (instrumentPlayers.isEmpty()) throw new IllegalArgumentException("instrumentPlayers can not be empty");
        instrumentPlayers.forEach(InstrumentPlayer::play);
    }
}
