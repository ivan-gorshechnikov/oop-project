package SeviceThree;

import ServiceOne.Ship;

import java.util.List;

public class Simulator {
    public void simulator() {
        Read read = new Read();
        read.JsonRead();
        List<Ship> LooseShips = read.getLooseShips();
        List<Ship> LiquidShips = read.getLiquidShips();
        List<Ship> ContainerShips = read.getContainerShips();
        int looseMin = Integer.MAX_VALUE, liquidMin = Integer.MAX_VALUE, containerMin = Integer.MAX_VALUE;
        if (!LooseShips.isEmpty()) {
            looseMin = LooseShips.get(0).getRealTimeOfArrival();
        }

        if (!LiquidShips.isEmpty()) {
            liquidMin = LiquidShips.get(0).getRealTimeOfArrival();
        }
        if (!ContainerShips.isEmpty()) {
            containerMin = ContainerShips.get(0).getRealTimeOfArrival();
        }
        int min = Integer.min(Integer.min(looseMin, liquidMin), containerMin);
        Thread LooseThread = new Thread(new ShipUnloading(LooseShips, min));
        Thread LiquidThread = new Thread(new ShipUnloading(LiquidShips, min));
        Thread ContainerThread = new Thread(new ShipUnloading(ContainerShips, min));

        LooseThread.start();
        LiquidThread.start();
        ContainerThread.start();
    }
}
