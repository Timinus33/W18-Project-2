package com.dci.menus;

import com.dci.entities.Car;
import com.dci.interfaces.Searchable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FindCarMenuTest {

    private static List<Car> cars;

    @BeforeAll
    static void beforeAll() {
        cars = new ArrayList<>();
        cars.add(new Car("Volkswagen", "Golf", Car.TRANSMISSION.MANUAL, Car.FUEL_TYPE.HYBRID, "125", "112.169", "2015", "black", "14490"));
        cars.add(new Car("Skoda", "Fabia", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.DIESEL, "135", "121.357", "2016", "red", "18390"));
        cars.add(new Car("Ford", "Focus", Car.TRANSMISSION.AUTOMATIC, Car.FUEL_TYPE.PETROL, "120", "120.972", "2015", "gray", "18300"));
    }

    @Test
    void searchByBrand() {
        assertEquals("Skoda", cars.stream().map(Car::brand).filter(value -> value.equals("Skoda")).findAny().orElse(null));
    }

    @Test
    void searchByModel() {
        assertEquals("Fabia", cars.stream().map(Car::model).filter(value -> value.equals("Fabia")).findAny().orElse(null));
    }

    @Test
    void searchByTransmission() {
        assertTrue(cars.stream().map(Car::transmission).anyMatch(transmission -> transmission.getAbbreviation().equals("automatic")));
        assertTrue(cars.stream().map(Car::transmission).anyMatch(transmission -> transmission.getAbbreviation().equals("manual")));
    }

    @Test
    void searchByFuelType() {
        assertTrue(cars.stream().map(Car::fuelType).anyMatch(fuelType -> fuelType.getAbbreviation().equals("petrol")));
        assertTrue(cars.stream().map(Car::fuelType).anyMatch(fuelType -> fuelType.getAbbreviation().equals("diesel")));
        assertTrue(cars.stream().map(Car::fuelType).anyMatch(fuelType -> fuelType.getAbbreviation().equals("hybrid")));
    }

    @Test
    void searchByPower() {
        assertTrue(cars.stream().map(Car::power).anyMatch(s -> Objects.equals(s, "120")));
    }

    @Test
    void searchByMileage() {
        assertTrue(cars.stream().map(Car::mileage).anyMatch(s -> Objects.equals(s, "112.169")));
    }
}