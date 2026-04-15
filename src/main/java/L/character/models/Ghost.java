package L.character.models;

import L.character.damage_strategy.NotPermittedDamageTakingStrategy;

public class Ghost extends Character {
    public Ghost (String characterName, int attackStrengthPoints){
        super(characterName, attackStrengthPoints, new NotPermittedDamageTakingStrategy(Ghost.class.getSimpleName()));
    }

    @Override
    public void attack() {
        System.out.println("The ghost casts a spooky spell of " + this.getAttackStrengthInPoints() + " points.");
    }

}
