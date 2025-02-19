package com.dci.account;

import com.dci.entities.User;
import com.dci.utils.Utils;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.dci.utils.Utils.*;

public class Register {
    public static void register() {
        System.out.print("Please enter your username (min. 3 characters): ");
        String username = scanner.nextLine();
        while (!validateUsername(username)) {
            System.out.print("Failed! Please try again: ");
            username = scanner.nextLine();
        }

        System.out.print("Please enter your password (min. 6 characters): ");
        String password = scanner.nextLine();
        while (!validatePassword(password)) {
            System.out.print("Failed! Please try again: ");
            password = scanner.nextLine();
        }

        System.out.print("Please enter your birthdate (format: year-month-day -> 1990-02-09): ");
        String birthdate = scanner.nextLine();
        while (!validateBirthdate(birthdate)) {
            System.out.print("Failed! Please try again: ");
            birthdate = scanner.nextLine();
        }

        System.out.print("Please enter your phone number (min. 10 digits): ");
        String phoneNumber = scanner.nextLine();
        while (!validatePhoneNumber(phoneNumber)) {
            System.out.print("Failed! Please try again: ");
            phoneNumber = scanner.nextLine();
        }

        System.out.print("Please enter your email address: ");
        String emailAddress = scanner.nextLine();
        while (!validateEmailAddress(emailAddress)) {
            System.out.print("Failed! Please try again: ");
            emailAddress = scanner.nextLine();
        }

        User user = new User(username, password, LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), phoneNumber, emailAddress, false);
        currentUser = user;
        Utils.addNewUserToList(user);
        System.out.println("Registration successful!");
    }

    private static boolean validateUsername(String username) {
        return username != null && !username.isBlank() && username.chars().allMatch(Character::isLetterOrDigit) && username.length() >= 3 &&
                users.stream().map(User::getUsername).noneMatch(name -> Objects.equals(name, username));
    }

    private static boolean validatePassword(String password) {
        return password != null && !password.isBlank() && password.chars().allMatch(Character::isLetterOrDigit) && password.length() >= 6;

    }

    private static boolean validateBirthdate(String birthdate) {
        return birthdate.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10,}") && users.stream().map(User::getPhoneNumber).noneMatch(number -> Objects.equals(number, phoneNumber));
    }

    private static boolean validateEmailAddress(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress) && users.stream().map(User::getEmailAddress).noneMatch(address -> Objects.equals(address, emailAddress));
    }
}
