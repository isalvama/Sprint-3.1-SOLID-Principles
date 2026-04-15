package L.character.models;

import L.character.damage_strategy.Damage;

import java.util.Objects;

public class Character {
    private final String characterName;
    private final int attackStrengthInPoints;
    private final Damage damageTakingStrategy;

    public Character (String characterName, int attackStrengthPoints, Damage damageTakingProcedure) {
        if (attackStrengthPoints < 0) throw new IllegalArgumentException("attackStrengthPoints can not be negative");
        this.characterName = Objects.requireNonNull(characterName, "characterName can not be null");
        this.attackStrengthInPoints = attackStrengthPoints;
        this.damageTakingStrategy = Objects.requireNonNull(damageTakingProcedure, "damageTakingProcedure can not be null");
    }

    public void attack() {
        System.out.println("The character attacks with a weapon of " + this.getAttackStrengthInPoints() + " points.");
    }

    public void takeDamage (int points){
        damageTakingStrategy.takeDamage(points);
    }

    public int getAttackStrengthInPoints() {
        return attackStrengthInPoints;
    }
}
