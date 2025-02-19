package com.dci.interfaces;

import com.dci.entities.Car;

import java.util.List;

public interface Searchable {
    List<Car> searchByBrand(String brand);

    List<Car> searchByModel(List<Car> carList, String model);

    List<Car> searchByTransmission(List<Car> carList, Car.TRANSMISSION isManual);

    List<Car> searchByFuelType(List<Car> carList, Car.FUEL_TYPE fuelType);

    List<Car> searchByPower(List<Car> carList, String power);

    List<Car> searchByMileage(List<Car> carList, String mileage);

    List<Car> searchByYear(List<Car> carList, String year);

    List<Car> searchByColor(List<Car> carList, String color);

    List<Car> searchByPrice(List<Car> carList, String price);
}
