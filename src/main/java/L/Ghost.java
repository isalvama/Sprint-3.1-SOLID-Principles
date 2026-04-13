package L;

public class Ghost extends Character {
    public Ghost (String characterName, int attackStrengthPoints){
        super(characterName, attackStrengthPoints, new NotPermittedDamageTakingProcedure(Ghost.class.getName()));
    }

    @Override
    public void attack() {
        System.out.println("The ghost casts a spooky spell of " + this.getAttackStrengthInPoints() + " points.");
    }

}
