package SeviceThree;

import ServiceOne.Generator;
import ServiceOne.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;


public class ShipUnloading {
    private int startTime;
    private int simulatedTime = 0;
    private int numOfCranes = 1;
    private List<Ship> shipQueue;
    private List<Ship> ships = new CopyOnWriteArrayList<Ship>();
    private int minFine = Integer.MAX_VALUE;
    private int minNumOfCranes = 0;
    private int fine = 30000 * numOfCranes;

    public ShipUnloading(List<Ship> ships, int startTime) {
        this.ships = ships;
        this.startTime = startTime;
    }

    public void run() throws InterruptedException {
        List<Thread> craneList = new ArrayList<Thread>();
        while (isContinue()) {
            fine = 0;
            simulatedTime = this.ships.get(0).getRealTimeOfArrival() - startTime;
            //System.out.println("I am here " + Thread.currentThread().toString() + "  "  + ships.get(0).getCargo().getType().toString());
            for (Ship ship : ships) {
                ship.setWaitTime(0);
                ship.writeWorkingTime();
            }
            craneList.clear();
            shipQueue = new CopyOnWriteArrayList<>();
            for (int i = 0; i < numOfCranes; i++) {
                new Thread(new Crane()).start();
            }
            sleep(20000);
            System.out.println("I am here");
            //shipQueue.clear();
            System.out.println("Crane " + numOfCranes + " fine: " + fine + "  "  + ships.get(0).getCargo().getType().toString());
            if (fine < minFine) {
                minFine = fine;
                minNumOfCranes = numOfCranes;
            }
            ++numOfCranes;
        }
    }

    private boolean isContinue() {
        return minFine > 30000;
    }

    private int num = -1;
    private int index = 0;

    synchronized void doMethod() {
        // System.out.println("I am here " + ij);
        //ij++;
        num = (num + 1) % numOfCranes;
        //System.out.println(Thread.currentThread().getName() + " " + num + "  " + simulatedTime);
        if (num == 0) {
            if (index < ships.size()) {
                if (simulatedTime + startTime <= ships.get(index).getRealTimeOfArrival()) { // добавляем в очередь если его время пришло
                    shipQueue.add(ships.get(index));
                    index++;
                }
            }
        }
        if (num < shipQueue.size() * 2) {
            shipQueue.get(num % shipQueue.size()).decWorkUploadingTime();
            //System.out.println("I dec");
        }

        if (num == numOfCranes - 1 && !shipQueue.isEmpty()) {
            for (int i = numOfCranes; i < shipQueue.size(); i++) { // те кто не прошли увеличиваем их время стоянки
                shipQueue.get(i).incWaitTime();
            }
            for (int i = 0; i < shipQueue.size() && i < numOfCranes; i++) { // если ктото приехал то выкидываем из очереди
                Ship ship = shipQueue.get(i);
                if (ship.getWorkingUnloadingTime() <= 0) {
                    fine += (ship.getWaitTime() / 60) * 100;
                    shipQueue.remove(i);
                }
            }
            simulatedTime++;
        }
    }

    public class Crane implements Runnable {

        private int ij = 0;

        @Override
        public void run() {
            index = 0;
            while (simulatedTime < Generator.MaxTime) {
                doMethod();
            }
            System.out.println("I am here");
        }
    }
}
