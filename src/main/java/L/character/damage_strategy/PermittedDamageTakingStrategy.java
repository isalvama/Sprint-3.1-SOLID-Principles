package L.character.damage_strategy;

import java.util.Objects;

public class PermittedDamageTakingStrategy implements Damage {
    private final String characterClassName;
    private final int damageResistanceFactor;

    public PermittedDamageTakingStrategy(String characterClassName, int damageResistanceFactor) {
        if (damageResistanceFactor < 1) throw new IllegalArgumentException("damageReductionFactor can not be less than 1");
        this.characterClassName = Objects.requireNonNull(characterClassName, "characterClassName can not be null");
        this.damageResistanceFactor = damageResistanceFactor;
    }

    @Override
    public void takeDamage(int points) {
        System.out.println("The " + characterClassName  + " resists and only takes " + (points / damageResistanceFactor) + " points of damage.");
    }
}
