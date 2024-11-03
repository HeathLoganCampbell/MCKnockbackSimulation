package dev.cobblesword;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: Sprock
 */
public class DrawPanel extends JPanel
{
    public KnockbackSimulator simulator;

    public DrawPanel(KnockbackSimulator simulator)
    {
        this.simulator = simulator;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int scale = 20; // Scale factor for visualization
        g.setColor(Color.LIGHT_GRAY);

        for (int i = -15; i < 15; i++) {
            g.drawLine(centerX + (i * scale), 0, centerX + (i * scale), getHeight());
            g.drawLine(0, centerY + (i * scale), getWidth(), centerY + (i * scale));
        }

        // Draw attacker
        g.setColor(Color.BLUE);
        int attackerX = centerX + (int) (this.simulator.getAttacker().getX() * scale);
        int attackerZ = centerY - (int) (this.simulator.getAttacker().getZ() * scale);
        int playerRadius = 10;
        int playerDiameter = playerRadius * 2;
        g.fillOval(attackerX - playerRadius, attackerZ - playerRadius, playerDiameter, playerDiameter);
        g.drawString("Attacker", attackerX + playerRadius, attackerZ - playerRadius);

        // Draw victim
        if( this.simulator.getVictim().distanceTo(this.simulator.getAttacker()) <= 3)
        {
            g.setColor(Color.GREEN);
        }
        else {
            g.setColor(Color.RED);
        }
        int victimX = centerX + (int) (this.simulator.getVictim().getX() * scale);
        int victimZ = centerY - (int) (this.simulator.getVictim().getZ() * scale);
        g.fillOval(victimX - playerRadius, victimZ - playerRadius, playerDiameter, playerDiameter);
        g.drawString("Victim", victimX + playerRadius, victimZ - playerRadius);
    }
}