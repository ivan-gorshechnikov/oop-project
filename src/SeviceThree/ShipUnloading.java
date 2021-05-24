package SeviceThree;

import ServiceOne.Generator;
import ServiceOne.Ship;
import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;


public class ShipUnloading implements Runnable{
    private int startTime;
    private volatile int simulatedTime = 0;
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
    @Override
    public void run(){
        //List<Thread> craneList = new ArrayList<Thread>();
        while (isContinue()) {
            fine = 30000 * numOfCranes;
            simulatedTime = this.ships.get(0).getRealTimeOfArrival() - startTime;
            //System.out.println("I am here " + Thread.currentThread().toString() + "  "  + ships.get(0).getCargo().getType().toString());
            for (Ship ship : ships) {
                ship.setWaitTime(0);
                ship.writeWorkingTime();
            }
            //craneList.clear();
            shipQueue = new CopyOnWriteArrayList<>();
            for (int i = 0; i < numOfCranes; i++) {
                new Thread(new Crane()).start();
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Ship ship:
                 ships) {
                fine += (ship.getWaitTime() / 60) * 100;
            }
            //System.out.println("I am here");
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
        return minFine - 30000 * numOfCranes > 0;
    }

    private int num = -1;
    private int index = 0;

    synchronized void doMethod() {
        num = (num + 1) % numOfCranes;
        if (num == 0) {
            if (index < ships.size()) {
                if (simulatedTime + startTime >= ships.get(index).getRealTimeOfArrival()) { // добавляем в очередь если его время пришло
                    shipQueue.add(ships.get(index));
                    index++;
                }
            }
        }
        if (num < shipQueue.size() * 2) {
            shipQueue.get(num % shipQueue.size()).decWorkUploadingTime();
        }

        if (num == numOfCranes - 1) {
            simulatedTime++;
            for (int i = numOfCranes; i < shipQueue.size(); i++) {
                shipQueue.get(i).incWaitTime();
            }
            for (int i = 0; i < shipQueue.size() && i < numOfCranes; i++) {
                Ship ship = shipQueue.get(i);
                if (ship.getWorkingUnloadingTime() <= 0) {
                    shipQueue.remove(i);
                }
            }
        }
    }

    public class Crane implements Runnable {

        @Override
        public void run() {
            index = 0;
            while (simulatedTime < Generator.MaxTime) {
                doMethod();
            }
        }
    }
}
