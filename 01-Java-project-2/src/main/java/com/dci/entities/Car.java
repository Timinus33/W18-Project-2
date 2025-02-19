package com.dci.entities;

public record Car(String brand, String model, com.dci.entities.Car.TRANSMISSION transmission,
                  FUEL_TYPE fuelType, String power, String mileage, String year, String color,
                  String price) {

    public enum TRANSMISSION {
        MANUAL("manual"), AUTOMATIC("automatic");
        private final String abbreviation;

        TRANSMISSION(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    public enum FUEL_TYPE {
        PETROL("petrol"), DIESEL("diesel"), HYBRID("hybrid");

        private final String abbreviation;

        FUEL_TYPE(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    @Override
    public String toString() {
        return "\nBrand: " + brand() + "\nModel: " + model() + "\nGearbox: " + transmission() + "\nFuelType: " + fuelType() + "\nPower: "
                + power() + " HP\nMileage: " + mileage() + " km\nYear: " + year() + "\nColor: " + color() + "\nPrice: " + price() + "$";
    }
}
