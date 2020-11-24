import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is an abstract class that the other spaceships inherit from.
 */
public abstract class SpaceShip {

    /**
     * The space ship physics that this ship has.
     */
    SpaceShipPhysics physics = new SpaceShipPhysics();

    /**
     * The maximum energy start level of the ship, when it is reset.
     */
    private final int maxEnergyStartLevel = 210;

    /**
     * The maximal energy level of the ship.
     */
    private int maxEnergyLevel = maxEnergyStartLevel;

    /**
     * The current energy start level of the ship, when it is reset.
     */
    private final int currentEnergyStartLevel = 190;

    /**
     * The current energy level of the ship. A whole number larger than 0.
     */
    private int currentEnergyLevel = currentEnergyStartLevel;

    /**
     * The start health level of the ship. A whole number larger than 0.
     */
    private final int healthStartLevel = 22;

    /**
     * The current health level of the ship. A whole number larger than 0.
     */
    private int healthLevel = healthStartLevel;

    /**
     * The status of the ship's shield- false if it is off, true if it is on
     */
    protected boolean shieldStat = false;

    /**
     * Holds a number that tells the ship how many rounds are left until the next time the
     * ship could fire. The ship can't fire for the next couple of rounds after it fired.
     */
    private int roundsToNextFire = 0;

    /**
     * The number of round the gun should wait until it could fire again.
     */
    private int roundsGunWaits = 7;
    /**
     * The amount of energy the ship gets when it bashes another ship with it's shields up.
     */
    private final int energyBashBonus = 18;

    /**
     * The amount of energy the ship loses when it collides another ship or when it gets shot at.
     */
    private final int energyHitCost = 10;

    /**
     * The amount of energy the ship gets at every round of the game.
     */
    private final int energyRoundRenewed = 1;

    /**
     * The amount of energy the ship loses when it teleports.
     */
    private final int energyTeleportCost = 140;

    /**
     * The amount of energy the ship loses when it fires a shot.
     */
    private final int energyShotCost = 19;

    /**
     * The amount of energy the ship loses when it has it's shields up.
     */
    private final int energyShieldCost = 19;

    /**
     * A flag that holds a boolean value if the ship wants to fire this turn or not.
     */
    protected boolean toFire = false;
    /**
     * A flag that holds a boolean value if the ship wants to teleport this turn or not.
     */
    protected boolean toTeleport = false;

    /**
     * A flag that holds a boolean value if the ship wants to accelerate this turn or not.
     */
    protected boolean toAccel = false;

    /**
     * Holds a value of the wanted ship turn angle in this turn.
     * 1 represents left, -1 right and 0 none.
     */
    protected int turnAngle = 0;

    /**
     * Represents that the wanted turn is to the left
     */
    protected final static int turnLeft = 1;

    /**
     * Represents that the wanted turn is to the right
     */
    protected final static int turnRight = -1;

    /**
     * A flag that holds a boolean value if the ship wants to activate it's shields this turn or not.
     */
    protected boolean toActivateShield = false;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        // sets the start of the round
        this.shieldStat = false;
        roundsToNextFire = Math.max(roundsToNextFire - 1, 0);
        // actions to do in this round
        if (toTeleport)
            teleport();
        physics.move(toAccel, turnAngle);
        if (toActivateShield)
            shieldOn();
        if (this.toFire)
            fire(game);
        currentEnergyLevel = Math.min(currentEnergyLevel + energyRoundRenewed, maxEnergyLevel);
        this.setBackFlags();
    }

    /**
     * Sets back the flags to their false values.
     */
    private void setBackFlags() {
        toTeleport = false;
        toActivateShield = false;
        toFire = false;
        turnAngle = 0;
        toAccel = false;
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (shieldStat) {
            maxEnergyLevel += energyBashBonus;
            currentEnergyLevel += energyBashBonus;
        } else this.gotHit();
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        physics = new SpaceShipPhysics();
        healthLevel = healthStartLevel;
        currentEnergyLevel = currentEnergyStartLevel;
        maxEnergyLevel = maxEnergyStartLevel;
        roundsToNextFire = 0;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return (healthLevel == 0);
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!shieldStat) {
            healthLevel--;
            maxEnergyLevel = Math.max(maxEnergyLevel - energyHitCost, 0);
            currentEnergyLevel = Math.min(maxEnergyLevel, currentEnergyLevel);
        }

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return shieldStat ? GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD : GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if ((currentEnergyLevel - energyShotCost >= 0) && (roundsToNextFire == 0)) {
            game.addShot(physics);
            currentEnergyLevel -= energyShotCost;
            roundsToNextFire = roundsGunWaits;
        }

    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (currentEnergyLevel - energyShieldCost <= 0) {
            currentEnergyLevel -= energyShieldCost;
            shieldStat = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (currentEnergyLevel - energyTeleportCost >= 0) {
            currentEnergyLevel -= energyTeleportCost;
            physics = new SpaceShipPhysics();
        }
    }

}
