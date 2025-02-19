package com.dci.utils;

import com.dci.entities.Car;
import com.dci.entities.User;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utils {
    public static final Scanner scanner = new Scanner(System.in);

    private static final String users_file = "src/main/resources/users.json";
    public static Set<User> users = new HashSet<>();

    private static final String cars_file = "src/main/resources/cars.json";
    public static ArrayList<Car> cars = new ArrayList<>();

    public static User currentUser = null;

    public static void loadCars() {
        File file = new File(Utils.cars_file);

        if (file.exists()) {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(file)) {
                System.out.println("Loading the car list from the json file...");
                Car[] carArray = gson.fromJson(reader, Car[].class);
                cars = new ArrayList<>(List.of(carArray));
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file.", e);
            }
        } else {
            cars.add(new Car("Volkswagen", "Golf", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "125", "112.169", "2015", "black", "14490"));
            cars.add(new Car("Volkswagen", "Golf", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.DIESEL, "190", "72.944", "2019", "white", "27990"));
            cars.add(new Car("Volkswagen", "Touran", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.DIESEL, "150", "124.843", "2016", "gray", "19990"));
            cars.add(new Car("Volkswagen", "Touran", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "105", "92.170", "2014", "silver", "15990"));
            cars.add(new Car("Volkswagen", "Touran", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.PETROL, "84", "85.158", "2019", "blue", "16990"));

            cars.add(new Car("Skoda", "Fabia", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.DIESEL, "135", "121.357", "2016", "red", "18390"));
            cars.add(new Car("Skoda", "Fabia", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.DIESEL, "180", "127.832", "2018", "yellow", "23290"));
            cars.add(new Car("Skoda", "Superb", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "160", "109.246", "2013", "pink", "26990"));
            cars.add(new Car("Skoda", "Superb", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.PETROL, "115", "92.170", "2020", "green", "18990"));
            cars.add(new Car("Skoda", "Superb", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "90", "85.158", "2019", "orange", "19800"));

            cars.add(new Car("Renault", "Arkana", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.DIESEL, "140", "101.426", "2019", "brown", "19260"));
            cars.add(new Car("Renault", "Captur", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "150", "107.901", "2015", "violet", "20180"));
            cars.add(new Car("Renault", "Scenic", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.DIESEL, "100", "121.981", "2018", "gold", "22270"));
            cars.add(new Car("Renault", "Megane", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.PETROL, "105", "114.240", "2022", "white", "15550"));
            cars.add(new Car("Renault", "Master", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.PETROL, "140", "136.792", "2016", "black", "18600"));

            cars.add(new Car("Ford", "Focus", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.DIESEL, "120", "120.972", "2015", "gray", "18300"));
            cars.add(new Car("Ford", "Focus", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.HYBRID, "130", "115.833", "2017", "green", "22250"));
            cars.add(new Car("Ford", "Focus", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "80", "113.852", "2016", "black", "15399"));
            cars.add(new Car("Ford", "Fiesta", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.PETROL, "115", "141.225", "2015", "orange", "19240"));
            cars.add(new Car("Ford", "Fiesta", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.PETROL, "110", "121.356", "2021", "brown", "17390"));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (Writer writer = new FileWriter(file)) {
                System.out.println("Writing the car list to the JSON file...");
                gson.toJson(cars, writer);
            } catch (IOException e) {
                throw new RuntimeException("Error writing to the file.", e);
            }
        }
    }

    public static void deleteCar(Car car) {
        cars.remove(car);
        try {
            if (Files.deleteIfExists(Path.of(cars_file))) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try (Writer writer = new FileWriter(Path.of(cars_file).toFile())) {
                    gson.toJson(cars, writer);
                } catch (IOException e) {
                    throw new RuntimeException("Error writing to the file.", e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAccounts() {
        if (Files.exists(Path.of(users_file))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
            try (Reader reader = new FileReader(users_file)) {
                System.out.println("Reading the users list from the JSON file...");
                User[] carArray = gson.fromJson(reader, User[].class);
                users = new HashSet<>(List.of(carArray));
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file.", e);
            }
        } else {
            System.out.println("Writing the users list to the JSON file...");
            addNewUserToList(new User("admin", "admin", LocalDate.of(1992, 10, 17), "0101010101", "michael@gmail.com", true));
        }
    }

    private static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDate.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(jsonElement.getAsString(), formatter);
        }
    }

    public static void addNewUserToList(User user) {
        users.add(user);
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
        try (Writer writer = new FileWriter(users_file)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file.", e);
        }
    }

    public static void deleteUserAccount() {
        System.out.print("Please enter the username: ");
        String input = scanner.nextLine();
        if (Objects.equals(currentUser.getUsername(), input)) {
            System.out.println("Failed! You cannot remove yourself from the system. Please try again...");
        } else {
            User foundUser;
            Optional<User> optional = users.stream().filter(user -> Objects.equals(user.getUsername(), input)).findAny();
            if (optional.isPresent()) {
                foundUser = optional.get();
                users.remove(foundUser);
                try {
                    if (Files.deleteIfExists(Path.of(users_file))) {
                        loadAccounts();
                        System.out.println(foundUser.getUsername() + " was successfully removed!");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("User with such name not found! Please try again...");
            }
        }
    }
}