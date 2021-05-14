package SeviceThree;

import ServiceOne.Cargo;
import ServiceOne.Ship;

import java.util.List;

public class Crane implements Runnable{
    private Ship ship;

    public Crane(Ship ships) {
        this.ship = ships;
    }

    @Override
    public void run() {
        ship.decUploadingTime();
    }
}
