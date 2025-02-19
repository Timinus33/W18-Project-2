package com.dci.menus;

import com.dci.entities.Car;
import com.dci.interfaces.Searchable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dci.menus.MainMenu.WRONG_INPUT;
import static com.dci.utils.Utils.*;

public class FindCarMenu implements Searchable {
    private static final Searchable searchable = new FindCarMenu();

    public static void printFindCarMenu() {
        System.out.println("""
                \n--- Find a car ---
                1. Search
                2. Back
                3. Exit""");

        processMenuOption();
    }

    private static void processMenuOption() {
        System.out.print("Please enter an option: ");
        String option = scanner.nextLine();
        if (option != null && !option.isEmpty() && option.chars().allMatch(Character::isDigit)) {
            switch (Integer.parseInt(option)) {
                case 1 -> findCarByBrand();
                case 2 -> MainMenu.printMainMenu();
                case 3 -> System.exit(0);
                default -> {
                    System.out.println(WRONG_INPUT);
                    printFindCarMenu();
                }
            }
        } else {
            System.out.println(WRONG_INPUT);
            printFindCarMenu();
        }
    }

    private static void findCarByBrand() {
        List<String> brands = cars.stream().map(Car::brand).distinct().toList();
        if (!brands.isEmpty()) {
            System.out.print("\nAvailable brands: " + brands + "\nPlease enter a brand name: ");
            String input = scanner.nextLine().trim();
            if (brands.stream().anyMatch(s -> s.equalsIgnoreCase(input))) {
                List<Car> carsFound = searchable.searchByBrand(input);
                carsFound.forEach(System.out::println);
                findCarByModel(carsFound);
            } else {
                System.out.println("Failed! Please try again...");
                findCarByBrand();
            }
        } else {
            System.out.println("Failed! No cars found...");
            printFindCarMenu();
        }
    }

    private static void findCarByModel(List<Car> results) {
        List<String> models = results.stream().map(Car::model).distinct().toList();
        if (!models.isEmpty()) {
            System.out.print("\nAvailable models: " + models + "\nPlease enter a model name: ");
            String input = scanner.nextLine().trim();
            if (models.stream().anyMatch(s -> s.equalsIgnoreCase(input))) {
                List<Car> carsFound = searchable.searchByModel(results, input);
                carsFound.forEach(System.out::println);
                if (carsFound.size() == 1) {
                    processCar(carsFound.getFirst());
                } else {
                    findCarByTransmission(carsFound);
                }
            } else {
                System.out.println("Failed! Please try again...");
                findCarByModel(results);
            }
        } else {
            System.out.println("Failed! No cars found...");
            printFindCarMenu();
        }
    }

    private static void findCarByTransmission(List<Car> results) {
        List<Car.TRANSMISSION> transmissions = results.stream().map(Car::transmission).distinct().toList();
        if (!transmissions.isEmpty()) {
            System.out.print("\nAvailable transmissions: " + transmissions + "\nPlease enter a transmission name: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank() && transmissions.stream().map(Car.TRANSMISSION::getAbbreviation).anyMatch(s -> s.equals(input))) {
                findTransmissionType(results, input);
            } else {
                System.out.println("Failed! Please try again...");
                findCarByTransmission(results);
            }
        } else {
            System.out.println("Failed! No cars found...");
            printFindCarMenu();
        }
    }

    private static void findTransmissionType(List<Car> results, String input) {
        Car.TRANSMISSION transmission = Objects.equals(input, Car.TRANSMISSION.MANUAL.getAbbreviation()) ? Car.TRANSMISSION.MANUAL : Car.TRANSMISSION.AUTOMATIC;
        List<Car> carsFound = searchable.searchByTransmission(results, transmission);
        carsFound.forEach(System.out::println);
        if (carsFound.size() == 1) {
            processCar(carsFound.getFirst());
        } else {
            findCarByFuelType(carsFound);
        }
    }

    private static void findCarByPower(List<Car> results) {
        List<String> carPowerList = results.stream().map(Car::power).distinct().toList();
        if (!carPowerList.isEmpty()) {
            System.out.print("\nAvailable powers: " + carPowerList + "\nPlease enter the car power: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank() && carPowerList.contains(input)) {
                List<Car> carsFound = searchable.searchByPower(results, input);
                carsFound.forEach(System.out::println);
                if (carsFound.size() == 1) {
                    processCar(carsFound.getFirst());
                } else {
                    findCarByPower(carsFound);
                }
            } else {
                System.out.println("Failed! Please try again...");
                findCarByPower(results);
            }
        } else {
            System.out.println("Failed! No cars found...");
            printFindCarMenu();
        }
    }

    private static void findCarByFuelType(List<Car> results) {
        List<Car.FUEL_TYPE> fuelTypes = results.stream().map(Car::fuelType).distinct().toList();
        if (!fuelTypes.isEmpty()) {
            System.out.print("\nAvailable fuel types: " + fuelTypes + "\nPlease enter a fuel type: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank() && fuelTypes.stream().map(Car.FUEL_TYPE::getAbbreviation).anyMatch(s -> s.equals(input))) {
                findFuelType(results, input);
            } else {
                System.out.println("Failed! Please try again...");
                findCarByFuelType(results);
            }
        } else {
            System.out.println("Failed! No cars found...");
            findCarByFuelType(results);
        }
    }

    private static void findFuelType(List<Car> results, String input) {
        Car.FUEL_TYPE fuelType = null;
        Optional<Car.FUEL_TYPE> type = Arrays.stream(Car.FUEL_TYPE.values()).filter(foundFuelType -> Objects.equals(foundFuelType.getAbbreviation(), input)).findFirst();
        if (type.isPresent()) {
            fuelType = type.get();
        }

        if (fuelType == null) {
            System.out.println("Failed! Please try again...");
            findCarByFuelType(results);
        } else {
            List<Car> carsFound = searchable.searchByFuelType(results, fuelType);
            carsFound.forEach(System.out::println);
            if (carsFound.size() == 1) {
                processCar(carsFound.getFirst());
            } else {
                findCarByPower(carsFound);
            }
        }
    }

    private static void processCar(Car car) {
        System.out.println("""
                \n1. Purchase
                2. Add to favorites
                3. Back
                4. Exit""");

        System.out.print("Please enter an option: ");
        String input = scanner.nextLine().trim();

        if (!input.isBlank() && input.chars().allMatch(Character::isDigit)) {
            switch (Integer.parseInt(input)) {
                case 1 -> {
                    if (currentUser != null) {
                        if (currentUser.withdraw(Double.parseDouble(car.price()))) {
                            currentUser.addCarToPurchasedCars(car);
                            System.out.println("Purchase is successful!");
                            deleteCar(car);
                            printFindCarMenu();
                        } else {
                            processCar(car);
                        }
                    } else {
                        System.out.println("Failed! You must log in or register to perform this action...");
                        processCar(car);
                    }
                }
                case 2 -> {
                    if (currentUser != null) {
                        if (!currentUser.getSavedCars().contains(car)) {
                            currentUser.addCarToSavedCars(car);
                            System.out.println("The car was saved into your list of favorite cars in the user account screen!");
                            printFindCarMenu();
                        } else {
                            System.out.println("Failed! The car is already on your list of favorite cars...");
                            processCar(car);
                        }
                    } else {
                        System.out.println("Failed! You must log in or register to perform this action...");
                        processCar(car);
                    }
                }
                case 3 -> printFindCarMenu();
                case 4 -> System.exit(0);
                default -> {
                    System.out.println("Failed! Please try again");
                    processCar(car);
                }
            }
        } else {
            System.out.println(WRONG_INPUT);
            processCar(car);
        }
    }

    @Override
    public List<Car> searchByBrand(String brand) {
        return cars.stream().filter(car -> car.brand().equalsIgnoreCase(brand)).toList();
    }

    @Override
    public List<Car> searchByModel(List<Car> carList, String model) {
        return carList.stream().filter(car -> car.model().equalsIgnoreCase(model)).toList();
    }

    @Override
    public List<Car> searchByTransmission(List<Car> carList, Car.TRANSMISSION transmission) {
        return carList.stream().filter(car -> Objects.equals(car.transmission(), transmission)).toList();
    }

    @Override
    public List<Car> searchByFuelType(List<Car> carList, Car.FUEL_TYPE fuelType) {
        return carList.stream().filter(car -> Objects.equals(car.fuelType(), fuelType)).toList();
    }

    @Override
    public List<Car> searchByPower(List<Car> carList, String power) {
        return carList.stream().filter(car -> Objects.equals(car.power(), power)).toList();
    }

    @Override
    public List<Car> searchByMileage(List<Car> carList, String mileage) {
        return carList.stream().filter(car -> Objects.equals(car.mileage(), mileage)).toList();
    }

    @Override
    public List<Car> searchByYear(List<Car> carList, String year) {
        return carList.stream().filter(car -> Objects.equals(car.year(), year)).toList();
    }

    @Override
    public List<Car> searchByColor(List<Car> carList, String color) {
        return carList.stream().filter(car -> Objects.equals(car.color(), color)).toList();
    }

    @Override
    public List<Car> searchByPrice(List<Car> carList, String price) {
        return carList.stream().filter(car -> Objects.equals(car.price(), price)).toList();
    }
}

