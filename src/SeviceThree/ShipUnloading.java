package SeviceThree;

import ServiceOne.Generator;
import ServiceOne.Ship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class ShipUnloading {
    private int actualTime = 0;
    private int fine = 0;
    public ShipUnloading (Generator gen) {
        CountShips(gen);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Ship ship: LooseShips) {
                    int timeArrival = ship.getRealTimeOfArrival();
                    if (timeArrival >= actualTime)
                    {
                        actualTime = ship.getEndTime();
                    }
                    else
                    {
                        looseFine += (actualTime - timeArrival) / 60;
                        actualTime = ship.getUnloadingTime() + ship.getDelayUploadingTime();
                    }
                }
            }
        }).start();
        System.out.println(looseFine);
    }

    public void finePlus(int actualTime) {
        fine+=actualTime;
    }

    private void CountShips(Generator gen)
    {
        Random random = new Random();
        for (Ship ship:gen.getShips()) {
            boolean isCorrect= false;
            while (!isCorrect)
            {
                ship.setDelayTime(random.nextInt(20161) - 10080);
                ship.setDelayUploadingTime(random.nextInt(1441));

                if (ship.getEndTime() < 43200 && ship.getRealTimeOfArrival() >= 0)
                {
                    isCorrect = true;
                }
            }
            switch (ship.getCargo().getType()) {
                case LOOSE -> LooseShips.add(ship);
                case LIQUID -> LiquidShips.add(ship);
                case CONTAINER -> ContainerShips.add(ship);
            }
        }
        LooseShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
        LiquidShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
        ContainerShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
    }
    int looseFine = 0;
    List<Ship> LooseShips = new ArrayList<>();
    List<Ship> ContainerShips = new ArrayList<>();;
    List<Ship> LiquidShips = new ArrayList<>();;
}
