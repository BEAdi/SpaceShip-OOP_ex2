import oop.ex2.*;

/**
 * This class is the factory of the game, which creates the ships as asked.
 */
public class SpaceShipFactory {


    /**
     * This method return an array of SpaceShips, as was asked in the array of letters it got.
     * @param args An array with the input from the user, that stands for the wanted ships in this game.
     * @return An array with the wanted space ships.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] gamesShips = new SpaceShip[args.length];
        for (int i = 0; i < gamesShips.length; i++) {
            gamesShips[i] = createShip(args[i]);
        }
        return gamesShips;
    }

    /**
     * This method creates and return a ship, according to the letter given.
     * @param letter A letter that stands for the ship that is wanted to be created
     * @return A spaceShip as asked
     */
    private static SpaceShip createShip(String letter) {
        switch (letter) {
            case "a":
                return (new AggressiveShip());
            case "b":
                return (new BasherShip());
            case "d":
                return (new DrunkardShip());
            case "h":
                return (new HumanShip());
            case "s":
                return (new SpeicalShip());
            case "r":
                return (new RunnerShip());
        }
        return null;
    }
}
