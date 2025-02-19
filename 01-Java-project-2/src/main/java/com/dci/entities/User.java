package com.dci.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dci.utils.Utils.scanner;

public class User {
    private final String username;
    private final String password;
    public LocalDate birthdate;
    private final String phoneNumber;
    private final String emailAddress;
    private final boolean isAdmin;

    private double balance;
    private final List<Car> savedCars;
    private final List<Car> purchasedCars;

    public User(String username, String password, LocalDate birthdate, String phoneNumber, String emailAddress, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.isAdmin = isAdmin;
        savedCars = new ArrayList<>();
        purchasedCars = new ArrayList<>();
    }

    public void deposit() {
        System.out.print("Please enter the amount you want to deposit: ");
        String input = scanner.nextLine();
        if (!input.isBlank() && input.chars().allMatch(Character::isDigit)) {
            deposit(Double.parseDouble(input));
        } else {
            System.out.println("Failed! Invalid amount entered. Please try again...");
        }
    }

    public void withdraw() {
        System.out.print("Please enter the amount you want to withdraw: ");
        String input = scanner.nextLine();
        if (!input.isBlank() && input.chars().allMatch(Character::isDigit)) {
            withdraw(Double.parseDouble(input));
        } else {
            System.out.println("Failed! Invalid amount entered. Please try again...");
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Successfully added " + amount + "$ to your bank account!");
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Failed! Insufficient funds in your bank account.");
            return false;
        } else {
            this.balance -= amount;
            System.out.println("Successfully withdrawn " + amount + "$ from your bank account!");
            return true;
        }
    }

    public void addCarToSavedCars(Car car) {
        this.savedCars.add(car);
    }

    public void addCarToPurchasedCars(Car car) {
        this.purchasedCars.add(car);
    }

    @Override
    public String toString() {
        return String.format("""
                \nUsername: %s
                Password: %s
                Birthdate: %s
                Phone number: %s
                Email address: %s
                isAdmin: %b""", getUsername(), getPassword(), getBirthdate(), getPhoneNumber(), getEmailAddress(), isAdmin);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public double getBalance() {
        return balance;
    }

    public List<Car> getSavedCars() {
        return savedCars;
    }

    public List<Car> getPurchasedCars() {
        return purchasedCars;
    }
}
