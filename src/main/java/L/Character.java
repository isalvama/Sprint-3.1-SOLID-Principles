package L;

public class Character {
    private final String characterName;
    private final int attackStrengthInPoints;
    private final Damage damageTakingProcedure;

    public Character (String characterName, int attackStrengthPoints, Damage damageTakingProcedure) {
        this.characterName = characterName;
        this.attackStrengthInPoints = attackStrengthPoints;
        this.damageTakingProcedure = damageTakingProcedure;
    }

    public void attack() {
        System.out.println("The character attacks with a weapon of " + this.getAttackStrengthInPoints() + " points.");
    }

    public void takeDamage (int points , int damageReductionFactor){
        damageTakingProcedure.takeDamage(points);
    }

    public int getAttackStrengthInPoints() {
        return attackStrengthInPoints;
    }
}
