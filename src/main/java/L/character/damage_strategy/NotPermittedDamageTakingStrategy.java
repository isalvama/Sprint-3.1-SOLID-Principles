package L.character.damage_strategy;

import java.util.Objects;

public class NotPermittedDamageTakingStrategy implements Damage {
    private final String characterClassName;

    public NotPermittedDamageTakingStrategy(String characterClassName) {
        this.characterClassName = Objects.requireNonNull(characterClassName, "characterClassName can not be null");
    }
    @Override
    public void takeDamage(int points) {
        System.out.println("A " + characterClassName + " cannot take physical damage!");
    }
}
