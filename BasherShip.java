import oop.ex2.*;

/**
 * This ship attempts to collide with other ships. It will always accelerate, and will
 * constantly turn towards the closest ship. If it gets within a distance of 0.19 units (inclusive)
 * from another ship, it will attempt to turn on its shields.
 */
public class BasherShip extends SpaceShip {

    /**
     * The distance from another ship that when it is closer than it, the ship wants to turn on it's shields.
     */
    private final double distanceForShield = 0.19;

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
     * Checks what is the distance and angle from the closest ship, and changes the flags that stand for wish
     * to turn on shield or to turn toward the closest ship according to that information.
     *
     * @param game the game in which the ship belongs, that in it the ship checks what it needs.
     */
    private void checkClosestShip(SpaceWars game) {
        SpaceShipPhysics otherShipsPhysics = game.getClosestShipTo(this).getPhysics();
        double distance = physics.distanceFrom(otherShipsPhysics);
        double angle = physics.angleTo(otherShipsPhysics);
        if (distance <= distanceForShield)
            toActivateShield = true;
        if (angle < 0)
            turnAngle = turnRight;
        else if (angle > 0)
            turnAngle = turnLeft;
    }

}
