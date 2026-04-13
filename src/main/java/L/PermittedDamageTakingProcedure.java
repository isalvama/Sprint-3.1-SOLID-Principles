package L;

public class PermittedDamageTakingProcedure implements Damage {
    private final String characterClassName;
    private final int damageReductionFactor;

    public PermittedDamageTakingProcedure(String characterClassName, int damageReductionFactor) {
        this.characterClassName = characterClassName;
        this.damageReductionFactor = damageReductionFactor;
    }

    @Override
    public void takeDamage(int points) {
        System.out.println("The " + characterClassName  + " resists and only takes " + (points / damageReductionFactor) + " points of damage.");
    }
}
