import oop.ex2.*;

/**
 * This spaceship is amusing!
 * When it collides another ship- it dies. When it is away of other ships it fires. It randomly accelerates,
 * and every time it dies, it changes from chasing other ships to running away from them them.
 */
public class DrunkardShip extends SpaceShip {

    /**
     * The distance from another ship that when it is larger than it, the ship wants to shoot.
     */
    private final double shotDistance = 0.4;

    /**
     * The boolean value that tells the ship wif it is now in the chasing position or not.
     */
    private boolean isChasing = true;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        checkClosestShip(game);
        super.doAction(game);
    }

    /**
     * Checks what is the distance and angle from the closest ship, and changes the flags that stand for wish
     * to fire or where to go according to that information.
     *
     * @param game the game in which the ship belongs, that in it the ship checks what it needs.
     */
    private void checkClosestShip(SpaceWars game) {
        SpaceShipPhysics otherShipsPhysics = game.getClosestShipTo(this).getPhysics();
        double distance = physics.distanceFrom(otherShipsPhysics);
        double angle = physics.angleTo(otherShipsPhysics);
        if (distance > shotDistance)
            toFire = true;
        if (isChasing)
            controlTurn(angle, turnRight, turnLeft);
        else controlTurn(angle, turnLeft, turnRight);
        toAccel = chooseIfAccel(angle);
    }

    /**
     * This method controls the turn of the ship.
     *
     * @param angle        the angle from the closest ship
     * @param angleToRight the turn that is wanted if the closest ship is at the ship's right
     * @param angleToLeft  the turn that is wanted if the closest ship is at the ship's left
     */
    private void controlTurn(double angle, int angleToRight, int angleToLeft) {
        if (angle < 0)
            turnAngle = angleToRight;
        else if (angle > 0)
            turnAngle = angleToLeft;

    }

    /**
     * Randomises a number, and decides to accelerate or not, according to whether the angle to the closest
     * ship is larger or smaller that that random number.
     *
     * @param angle the angle to the closest ship.
     * @return Whether if to accelerate or not.
     */
    private boolean chooseIfAccel(double angle) {
        double rand = Math.random();
        return (rand > angle);
    }

    /**
     * When collides another ship, it dies and resets.
     */
    public void collidedWithAnotherShip() {
        this.reset();
    }

    /**
     * When dies, it resets like a normal ship, and changes it behavior from chaser to runner or the opposite.
     */
    public void reset() {
        super.reset();
        isChasing = !isChasing;
    }
}
