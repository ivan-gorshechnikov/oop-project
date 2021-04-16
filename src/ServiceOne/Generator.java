package ServiceOne;

import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

import static ServiceOne.Cargo.TypeOfCargo.*;

public class Generator {
    private final int MaxTime = 43200;
    private Vector<Ship> ships;

    public Generator(int size) {
        ships = new Vector<Ship>();
        for (int i = 0; i < size; i++) {
            ships.addElement(generateNewShip());
        }
    }

    public Ship generateNewShip() {
        Random random = new Random();
        Cargo cargo = new Cargo();
        switch (random.nextInt(3)) {
            case 0 -> cargo.setType(LOOSE);
            case 1 -> cargo.setType(LIQUID);
            case 2 -> cargo.setType(CONTAINER);
        }
        int weight = random.nextInt(1000);
        int unloadingTime = 0;
        switch (cargo.getType()) {
            case LOOSE -> unloadingTime = weight / 3 + 1;
            case LIQUID -> unloadingTime = weight / 2 + 1;
            case CONTAINER -> unloadingTime = weight;
        }
        return new Ship(shipNames[random.nextInt(shipNames.length)], cargo, random.nextInt(MaxTime), unloadingTime, weight);
    }


    public Vector<Ship> getShips() {
        return ships;
    }

    final String[] shipNames = new String[]{"Prince Royal", "Constant Reformation", "Victory", "Swiftsure", "St. George",
            "St. Andrew", "Triumph", "Charles", "Henrietta Maria", "James", "Unicorn", "Sovereign of the Seas", "Convertive",
            "Happy Entrance", "Garland", "Mercury", "Spy", "Bonaventure", "Mary Rose", "Leopard", "Swallow", "Expedition", "Providence",
            "Satisfaction", "Adventure", "Nonsuch", "Assurance", "Constance Warwick", "Phoenix", "Dragon", "Tiger", "Elisabeth"};
}
