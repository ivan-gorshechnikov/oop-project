package SeviceThree;

import ServiceOne.Generator;
import ServiceOne.Ship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class ShipUnloading implements Runnable {
    private int startTime;
    private int simulatedTime = 0;
    private int numOfCranes = 1;
    private List<Ship> shipQueue = new ArrayList<Ship>();
    private List<Ship> ships = new ArrayList<Ship>();
    private int minFine = Integer.MAX_VALUE;
    private int minNumOfCranes = 0;

    public ShipUnloading(List<Ship> ships, int startTime) {
        this.ships = ships;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        List<Thread> craneList = new ArrayList<Thread>();
        int fine;
        while (isContinue()) {
            fine = 0;
            simulatedTime = this.ships.get(0).getRealTimeOfArrival() - startTime;
            int index = 0;
            System.out.println("I am here");

            while (simulatedTime < Generator.MaxTime) {

                if (index < ships.size()) {
                    if (simulatedTime + startTime == this.ships.get(index).getRealTimeOfArrival()) { //добавляем в очередь если его время пришло
                        shipQueue.add(this.ships.get(index));
                        index++;
                    }
                }

                for (int i = 0; i < numOfCranes && i < shipQueue.size() * 2; i++) { // уменьшаем время разгрузки
                    craneList.add(new Thread(new Crane(shipQueue.get(i % shipQueue.size()))));
                }

                for (int i = numOfCranes; i < shipQueue.size(); i++) { // те кто не прошли учеличиваем их время стоянки
                    shipQueue.get(i).incWaitTime();
                }

                for (Thread t : craneList) { //запускаем потоки
                    synchronized (Ship.class) {
                        t.start();
                    }
                }
                craneList.clear();

                for (int i = 0; i < shipQueue.size() && i < numOfCranes; i++) {//если ктото приехал то выкидываем из очереди
                    Ship ship = shipQueue.get(i);
                    if (ship.getUnloadingTime() <= 0) {
                        fine += (ship.getWaitTime() / 60) * 100;
                        shipQueue.remove(0);
                    }
                }
                ++simulatedTime;

            }
            System.out.println("Cranes:  " + numOfCranes + " fine: " + fine);
            if (fine < minFine) {
                minFine = fine;
                minNumOfCranes = numOfCranes;
            }
            ++numOfCranes;
            shipQueue.clear();
            for (Ship ship :
                    ships) {
                ship.setWaitTime(0);

            }
        }
    }

    private boolean isContinue() {
        return minFine > 30000;
    }
}
