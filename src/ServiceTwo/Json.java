package ServiceTwo;

import ServiceOne.Cargo;
import ServiceOne.Generator;
import ServiceOne.Ship;

import java.io.FileWriter;

import org.json.*;

import java.util.Comparator;
import java.util.Scanner;

public class Json {

    private Generator ships;

    public Json(int n) {
        ships = new Generator(n);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Dou you want to add ship?(y/n)");
            String answer = input.nextLine();
            while (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Dou you want to add ship?(y/n)");
                answer = input.nextLine();
            }
            if (answer.equals("y")) {
                try {
                    addNewShipFromConsole();
                } catch (IllegalArgumentException exception) {
                    System.out.println(exception.getMessage());
                }
            } else {
                break;
            }
        }
        ships.getShips().sort(Comparator.comparing(Ship::getTimeOfArrival));
        System.out.println(ships.getShips());
    }
    private void addNewShipFromConsole()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter date of arrival");
        int day = input.nextInt();
        if (day < 0 || day > 30) {
            throw new IllegalArgumentException("Invalid date: " + day);
        }
        System.out.println("Enter hour of arrival");
        int hours = input.nextInt();
        if (hours < 0 || hours > 24) {
            throw new IllegalArgumentException("Invalid count of hours: " + hours);
        }
        System.out.println("Enter minutes of arrival");
        int minutes = input.nextInt();
        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("Invalid count of minutes: " + minutes);
        }
        System.out.println("Enter name of ship:");
        String name = input.next();
        System.out.println("Choose type of ship, where 0 - LOOSE, 1 - LIQUID, 2 - CONTAINER");
        int cargo = input.nextInt();
        Cargo type = new Cargo();
        switch (cargo)
        {
            case 0 -> type.setType(Cargo.TypeOfCargo.LOOSE);
            case 1 -> type.setType(Cargo.TypeOfCargo.LIQUID);
            case 2 -> type.setType(Cargo.TypeOfCargo.CONTAINER);
            default -> throw new IllegalArgumentException("Invalid type of ship: " + cargo);
        }
        System.out.println("Enter weight: (from 0 to 1000) ");
        int weight = input.nextInt();
        if (weight < 0 || weight > 1000) {
            throw new IllegalArgumentException("Invalid weight: " + weight);
        }
        int unloadingTime;
        switch (type.getType()) {
            case LOOSE -> unloadingTime = weight / 3 + 1;
            case LIQUID -> unloadingTime = weight / 2 + 1;
            case CONTAINER -> unloadingTime = weight;
            default -> throw new IllegalStateException("Unexpected value: " + type.getType());
        }
        ships.getShips().addElement(new Ship(name, type, (1440 * day + 60 * hours + minutes), unloadingTime, weight));
    }
    public void writeToJson() {
        JSONArray shipJSON = new JSONArray();
        for (Ship ship : ships.getShips()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name ship", ship.getName());
                jsonObject.put("Arrival date", ship.getTimeOfArrival());
                jsonObject.put("Departure date", ship.getEndTime());
                jsonObject.put("Type of cargo", ship.getCargo().getType().toString());
                jsonObject.put("Real arrival date", ship.getRealTimeOfArrival());
                shipJSON.put(jsonObject);
            } catch (JSONException e) {

            }
        }
        try (FileWriter file = new FileWriter("src\\ServiceTwo\\report.JSON")) {
            file.write(shipJSON.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}