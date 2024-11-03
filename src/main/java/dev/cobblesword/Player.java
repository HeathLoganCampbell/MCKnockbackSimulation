package dev.cobblesword;

/**
 * @Author: Sprock
 */
public class Player {
    private static final double GRAVITY = 0.08; // Gravity constant in Minecraft
    private static final double AIR_RESISTANCE = 0.98; // Air resistance factor
    private static final double GROUND_FRICTION = 0.6; // Friction when on the ground
    private static final double AIR_FRICTION = 0.91; // Friction when in the air

    private double horizontal = 0.4D;
    private double vertical = 0.4D;
    private double verticalMin = -1.0D;
    private double verticalMax = 0.4D;
    private double extraHorizontal = 0.5D;
    private double extraVertical = 0.1D;
    private double frictionHorizontal = 0.5D;
    private double frictionVertical = 0.5D;

    private boolean stopSprint = true;
    private boolean sprinting = false;

    private double x;
    private double y;
    private double z;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private boolean onGround = true;

    public Player(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public void setVelocity(double x, double y, double z) {
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
    }

    public void updatePosition() {
        // Update position based on current velocity
        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;

        // Apply gravity to vertical motion if not on the ground
        if (!onGround) {
            this.velocityY -= GRAVITY;
            this.velocityY *= AIR_RESISTANCE;
        }

        // Apply friction to horizontal motion
        if (onGround) {
            this.velocityX *= GROUND_FRICTION;
            this.velocityZ *= GROUND_FRICTION;
        } else {
            this.velocityX *= AIR_FRICTION;
            this.velocityZ *= AIR_FRICTION;
        }

        // Check if the entity hits the ground (simple check for demonstration)
        if (this.y <= 0) {
            this.y = 0;
            this.velocityY = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    public void attack(Player victim) {
        if(this.distanceTo(victim) > 3)
        {
            System.out.println("Out of range " + this.distanceTo(victim));
            return;
        }

        double deltaX = victim.getX() - this.getX();
        double deltaZ = victim.getZ() - this.getZ();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        if (magnitude > 0) {
            // Normalize direction
            deltaX /= magnitude;
            deltaZ /= magnitude;

            // Apply horizontal friction to existing velocity
            victim.setVelocity(victim.getVelocityX() * frictionHorizontal,
                                victim.getVelocityY() * frictionVertical,
                                victim.getVelocityZ() * frictionHorizontal);

            // Apply knockback forces
            victim.setVelocity(victim.getVelocityX() + deltaX * horizontal
                             , Math.min(Math.max(victim.getVelocityY() + vertical, verticalMin), verticalMax)
                            , victim.getVelocityZ() + deltaZ * horizontal);
        }
    }

    public double distanceTo(Player other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2)
                + Math.pow(other.getY() - this.y, 2)
                + Math.pow(other.getZ() - this.z, 2));
    }

    public String toString() {
        return this.x + ", " + this.y + ", " + this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocityZ(double velocityZ) {
        this.velocityZ = velocityZ;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }
}
