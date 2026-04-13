package L;

public class Warrior extends Character {

    public Warrior(String characterName, int attackStrengthPoints) {
        super(characterName, attackStrengthPoints, new PermittedDamageTakingProcedure(Warrior.class.getName(), 2));
    }

    @Override
    public void attack() {
        System.out.println("The warrior strikes with a sword of " + this.getAttackStrengthInPoints() + " points.");
    }
}
