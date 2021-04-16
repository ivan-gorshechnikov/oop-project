package SeviceThree;

import ServiceOne.Cargo;
import ServiceOne.Ship;

import java.util.List;

public class Crane {
    private List<Ship> ships;
    private int actualTime = 0;
    private int fine = 0;

    public Crane(List<Ship> ships) {
        this.ships = ships;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Ship ship: ships) {
                    int timeArrival = ship.getRealTimeOfArrival();
                    if (timeArrival >= actualTime)
                    {
                        actualTime = ship.getEndTime();
                    }
                    else
                    {
                        fine += (actualTime - timeArrival) / 60;
                        actualTime = ship.getUnloadingTime() + ship.getDelayUploadingTime();
                    }
                }
            }
        }).start();

    }

    public int getFine() {
        return fine;
    }
}
