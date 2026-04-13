package I;

public class WashingMachine implements WashingActions {

    @Override
    public void turnOn() {
        System.out.println("java.WashingMachine is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("java.WashingMachine is OFF");
    }

    @Override
    public void wash() {
        System.out.println("java.WashingMachine is washing clothes.");
    }
}
