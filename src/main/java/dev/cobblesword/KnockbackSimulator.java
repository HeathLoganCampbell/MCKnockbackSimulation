package dev.cobblesword;

/**
 * @Author: Sprock
 */
public class KnockbackSimulator {
    private Player attacker;
    private Player victim;

    public KnockbackSimulator()
    {
        attacker = new Player(0, 0, 0);
        victim = new Player(2, 0, 0);
    }

    public void init()
    {
    }

    public void tick()
    {
        attacker.updatePosition();
        victim.updatePosition();
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getVictim() {
        return victim;
    }
}
