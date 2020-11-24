import java.awt.Image;

import oop.ex2.*;

/**
 * The class that represents the human-controlled ship.
 */
public class HumanShip extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        toTeleport = gui.isTeleportPressed();
        toAccel = gui.isUpPressed();
        if (gui.isLeftPressed()) {
            turnAngle = SpaceShip.turnLeft;
        } else if (gui.isRightPressed())
            turnAngle = SpaceShip.turnRight;
        toActivateShield = gui.isShieldsPressed();
        toFire = gui.isShotPressed();
        super.doAction(game);
    }


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return shieldStat ? GameGUI.SPACESHIP_IMAGE_SHIELD : GameGUI.SPACESHIP_IMAGE;
    }

}
