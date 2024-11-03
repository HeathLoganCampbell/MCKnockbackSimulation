package dev.cobblesword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sprock
 */
public class KnockbackSimulatorFrame extends JFrame implements KeyListener
{
    private static final int TPS = 20; // Ticks per second (20 TPS is standard in Minecraft)
    private static final long TICK_DURATION_MS = 1000 / TPS; // Duration of each tick in milliseconds
    private static final int SIMULATION_TICKS = 50; // Number of ticks to simulate

    private DrawPanel drawPanel;

    private KnockbackSimulator knockbackSimulator;
    private Map<Integer, Boolean> keyState = new HashMap<>();

    public KnockbackSimulatorFrame() {
        setTitle("Knockback Simulator");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.knockbackSimulator = new KnockbackSimulator();

        drawPanel = new DrawPanel(this.knockbackSimulator);
        add(drawPanel, BorderLayout.CENTER);

        addKeyListener(this); // Add KeyListener for keyboard control
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);

        // Run the simulation in a separate thread
        new Thread(this::runSimulation).start();
    }


    private void runSimulation() {
        this.knockbackSimulator.init();

        while(true) {
            long startTime = System.currentTimeMillis();

            processKeyState();
            this.knockbackSimulator.tick();
            updateUI();

            long timeTaken = System.currentTimeMillis() - startTime;
            long sleepTime = TICK_DURATION_MS - timeTaken;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        System.out.println("Attacker: " + knockbackSimulator.getAttacker().toString());
//        System.out.println("Victim: " + knockbackSimulator.getVictim().toString());
    }

    private void processKeyState() {
        Player attacker = knockbackSimulator.getAttacker();
        double stepSize = 0.3; // The step size for each movement

        if (keyState.getOrDefault(KeyEvent.VK_W, false)) {
            attacker.setVelocityZ(+stepSize);
        }
        if (keyState.getOrDefault(KeyEvent.VK_S, false)) {
            attacker.setVelocityZ(-stepSize);
        }
        if (keyState.getOrDefault(KeyEvent.VK_A, false)) {
            attacker.setVelocityX(-stepSize);
        }
        if (keyState.getOrDefault(KeyEvent.VK_D, false)) {
            attacker.setVelocityX(+stepSize);
        }
        if (keyState.getOrDefault(KeyEvent.VK_SPACE, false)) {
            attacker.attack(knockbackSimulator.getVictim());
        }
    }

    private void updateUI() {
        SwingUtilities.invokeLater(() -> drawPanel.repaint());
    }

    public static void main(String[] args) {
        new KnockbackSimulatorFrame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyState.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyState.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed on key typed
    }
}
