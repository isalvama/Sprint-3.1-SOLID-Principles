package O;

public class DefaultPlayer implements InstrumentPlayer {

    @Override
    public void play() {
        System.out.println("🔇 Unknown instrument");
    }
}
