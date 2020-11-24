import oop.ex2.*;

/**
 * This ship pursues other ships and tries to fire at them. It will always accelerate,
 * and turn towards the nearest ship. When its angle to the nearest ship is less than 0.21
 * radians, it will try to fire.
 */
public class AggressiveShip extends SpaceShip {

    /**
     * The distance from another ship that when it is closer than it, the ship wants to turn on it's shields.
     */
    private final double angleToFire = 0.21;

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
     * Checks if the angle given is an angle to fire, by checking if the angle is in the range of the angle to
     * fire range.
     *
     * @param angle an angle from another ship, to check if there is a need to shot.
     * @return true wants to fire, false either.
     */
    private boolean isAngleToFire(double angle) {
        return !((angle >= angleToFire) || (angle <= (-1) * angleToFire));
    }

    /**
     * Checks what is the distance and angle from the closest ship, and changes the flags that stand for wish
     * to fire or to turn towards the ship according to that information.
     *
     * @param game the game in which the ship belongs, that in it the ship checks what it needs.
     */
    private void checkClosestShip(SpaceWars game) {
        SpaceShipPhysics otherShipsPhysics = game.getClosestShipTo(this).getPhysics();
        double angle = physics.angleTo(otherShipsPhysics);
        if (isAngleToFire(angle))
            toFire= true;
        if (angle < 0)
            turnAngle = turnRight;
        else if (angle > 0)
            turnAngle = turnLeft;
    }

}
