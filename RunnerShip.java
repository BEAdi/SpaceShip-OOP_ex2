import oop.ex2.*;

/**
 * This spaceship attempts to run away from the fight. It will always accelerate, and
 * will constantly turn away from the closest ship. If the nearest ship is closer than 0.25 units,
 * and if its angle to the Runner is less than 0.23 radians, the Runner feels threatened and will
 * attempt to teleport.
 */
public class RunnerShip extends SpaceShip {

    /**
     * The distance from another ship that when it is closer than it, the ship feels threatened.
     */
    private final double threatDistance = 0.25;
    /**
     * The angle, in radians, from another ship that when it is smaller than it, the ship feels threatened.
     */
    private final double threatAngle = 0.23;


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        toAccel = true;
        checkClosestShip(game);
        super.doAction(game);
    }

    /**
     * Checks if the angle given is threat, by checking if the angle is in the range of the threat angle.
     *
     * @param angle an angle from another ship, to check if threatens.
     * @return true if is a threat, false either.
     */
    private boolean isAngleThreat(double angle) {
        return !((angle >= threatAngle) || (angle <= (-1) * threatAngle));
    }

    /**
     * Checks what is the distance and angle from the closest ship, and changes the flags that stand for wish
     * to teleport or to turn away according to that information.
     *
     * @param game the game in which the ship belongs, that in it the ship checks what it needs.
     */
    private void checkClosestShip(SpaceWars game) {
        SpaceShipPhysics otherShipsPhysics = game.getClosestShipTo(this).getPhysics();
        double distance = physics.distanceFrom(otherShipsPhysics);
        double angle = physics.angleTo(otherShipsPhysics);
        if ((distance < threatDistance) && (isAngleThreat(angle)))
            toTeleport = true;
        if (angle < 0)
            turnAngle = turnLeft;
        else if (angle > 0)
            turnAngle = turnRight;
    }

}
