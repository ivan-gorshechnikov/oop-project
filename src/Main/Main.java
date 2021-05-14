package Main;

import ServiceOne.Generator;
import ServiceTwo.Json;
import SeviceThree.ShipUnloading;
import SeviceThree.Simulator;

public class Main {
    public static void main(String[] args) {
        Json gen = new Json(100);
        gen.writeToJson();
        //ShipUnloading sim = new ShipUnloading(gen);
        Simulator simulator = new Simulator();
        simulator.simulator();
    }
}
