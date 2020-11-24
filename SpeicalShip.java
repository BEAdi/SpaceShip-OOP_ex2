import oop.ex2.*;

/**
 * This spaceship is a speical ship! It is interesting to fight against. It always attempt to teleport if it
 * can do so. It can always shoot, no matter what its energy level is or when it last fired. It fires when the
 * closest ship is closer than the fire distance it holds. It also chases the closest ship.
 */
public class SpeicalShip extends SpaceShip {

    /**
     * he distance from another ship that when it is closer than it, the ship fires.
     */
    double fireDistance = 0.3;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        toTeleport = true;
        checkClosestShip(game);
        super.doAction(game);
    }


    /**
     * Checks what is the distance and angle from the closest ship, and changes the flags that stand for wish
     * to to fire and to angle to turn to according to that information.
     *
     * @param game the game in which the ship belongs, that in it the ship checks what it needs.
     */
    private void checkClosestShip(SpaceWars game) {
        SpaceShipPhysics otherShipsPhysics = game.getClosestShipTo(this).getPhysics();
        double distance = physics.distanceFrom(otherShipsPhysics);
        double angle = physics.angleTo(otherShipsPhysics);
        if (distance < fireDistance)
            toFire = true;
        if (angle < 0)
            turnAngle = turnRight;
        else if (angle > 0)
            turnAngle = turnLeft;
    }

    /**
     * Attempts to fire a shot. It can shoot any time it wants, without any relation to it's energy level.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        game.addShot(physics);
        game.addShot(new SpaceShipPhysics());
    }
}
