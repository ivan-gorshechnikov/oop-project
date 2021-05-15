package SeviceThree;

import ServiceOne.Ship;

import java.util.List;

public class Simulator {
    public void simulator()
    {
        Read read = new Read();
        read.JsonRead();
        List<Ship> LooseShips = read.getLooseShips();
        List<Ship> LiquidShips = read.getLiquidShips();
        List<Ship> ContainerShips = read.getContainerShips();
        int looseMin = Integer.MAX_VALUE, liquidMin = Integer.MAX_VALUE, containerMin = Integer.MAX_VALUE;
        if (!LooseShips.isEmpty())
        {
            looseMin = LooseShips.get(0).getRealTimeOfArrival();
        }

        if (!LiquidShips.isEmpty())
        {
            liquidMin = LiquidShips.get(0).getRealTimeOfArrival();
        }
        if (!ContainerShips.isEmpty())
        {
            containerMin = ContainerShips.get(0).getRealTimeOfArrival();
        }
        int min = Integer.min(Integer.min(looseMin, liquidMin), containerMin);
        ShipUnloading LooseThread = new ShipUnloading(LooseShips, min);
        ShipUnloading LiquidThread = new ShipUnloading(LiquidShips, min);
        ShipUnloading ContainerThread = new ShipUnloading(ContainerShips, min);
        try {
            LooseThread.run();
            LiquidThread.run();
            ContainerThread.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
