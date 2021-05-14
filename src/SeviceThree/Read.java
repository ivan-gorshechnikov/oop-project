package SeviceThree;

import ServiceOne.Cargo;
import ServiceOne.Ship;


import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Read {

    private List<Ship> LooseShips = new ArrayList<>();
    private List<Ship> ContainerShips = new ArrayList<>();
    private List<Ship> LiquidShips = new ArrayList<>();

    public void JsonRead() {
        JSONParser parser = new JSONParser();
        Random random = new Random();
        try {
            FileReader file = new FileReader("C:\\Users\\vania\\IdeaProjects\\oop-project\\src\\ServiceTwo\\report.json");
            JSONArray array = (JSONArray) parser.parse(file);
            for (Object obj: array) {
                JSONObject record = (JSONObject) obj;
                String shipName = (String) record.get("Name ship");
                int arrivalDate = ((Long) record.get("Arrival date")).intValue();
                String typeCargo = (String) record.get("Type of cargo");
                Cargo type = new Cargo();
                switch (typeCargo) {
                    case "LOOSE" -> type.setType(Cargo.TypeOfCargo.LOOSE);
                    case "LIQUID" -> type.setType(Cargo.TypeOfCargo.LIQUID);
                    case "CONTAINER" -> type.setType(Cargo.TypeOfCargo.CONTAINER);
                    default -> throw new IllegalArgumentException();
                }
                int weight = ((Long) record.get("Weight is")).intValue();
                int unloadingTime = ((Long) record.get("Unloading time")).intValue();
                Ship ship = new Ship(shipName, type, arrivalDate, unloadingTime, weight);
                switch (type.getType())
                {
                    case LIQUID -> LiquidShips.add(ship);
                    case LOOSE -> LooseShips.add(ship);
                    case CONTAINER -> ContainerShips.add(ship);
                }
                ship.setDelayTime(random.nextInt(20161) - 10080);
                ship.setDelayUploadingTime(random.nextInt(1441));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        LooseShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
        LiquidShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
        ContainerShips.sort(Comparator.comparing(Ship::getRealTimeOfArrival));
    }

    public List<Ship> getContainerShips() {
        return ContainerShips;
    }

    public List<Ship> getLiquidShips() {
        return LiquidShips;
    }

    public List<Ship> getLooseShips() {
        return LooseShips;
    }
}
