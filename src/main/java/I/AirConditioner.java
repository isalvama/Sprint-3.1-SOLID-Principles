package I;

public class AirConditioner implements AirActions {

    @Override
    public void turnOn() {
        System.out.println("java.AirConditioner is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("java.AirConditioner is OFF");
    }

    @Override
    public void heat() {
        System.out.println("java.AirConditioner is heating.");
    }

    @Override
    public void cool() {
        System.out.println("java.AirConditioner is cooling.");
    }

}

