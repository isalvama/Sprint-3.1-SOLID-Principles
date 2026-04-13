package L;

public class NotPermittedDamageTakingProcedure implements Damage {
    private final String characterClassName;

    public NotPermittedDamageTakingProcedure(String characterClassName) {
        this.characterClassName = characterClassName;
    }
    @Override
    public void takeDamage(int points) {
        System.out.println("A " + characterClassName + " cannot take physical damage!");
    }
}
