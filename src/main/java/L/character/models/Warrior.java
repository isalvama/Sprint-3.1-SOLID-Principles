package L.character.models;

import L.character.damage_strategy.PermittedDamageTakingStrategy;

public class Warrior extends Character {

    public Warrior(String characterName, int attackStrengthPoints) {
        super(characterName, attackStrengthPoints, new PermittedDamageTakingStrategy(Warrior.class.getSimpleName(), 2));
    }

    @Override
    public void attack() {
        System.out.println("The warrior strikes with a sword of " + this.getAttackStrengthInPoints() + " points.");
    }
}
