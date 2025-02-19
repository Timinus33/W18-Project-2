package com.dci.menus;

import static com.dci.utils.Utils.cars;
import static com.dci.utils.Utils.scanner;

public abstract class MainMenu {
    private static final String CUSTOMER_MAIN_MENU = """
            \n--- Main Menu ---
            1. View available cars
            2. Find a car
            3. Account
            4. Contact us
            5. Exit""";

    protected static final String WRONG_INPUT = "Failed! No such option available. Please try again...";

    public static void printMainMenu() {
        System.out.println(CUSTOMER_MAIN_MENU);

        System.out.print("Please enter an option: ");
        String option = scanner.nextLine();
        if (option != null && !option.isBlank() && option.chars().allMatch(Character::isDigit)) {
            switch (Integer.parseInt(option)) {
                case 1 -> viewAvailableCars();
                case 2 -> FindCarMenu.printFindCarMenu();
                case 3 -> AccountMenu.printAccountMenu();
                case 4 -> contactUs();
                case 5 -> System.exit(0);
                default -> {
                    System.out.println(WRONG_INPUT);
                    printMainMenu();
                }
            }
        } else {
            System.out.println(WRONG_INPUT);
            printMainMenu();
        }

    }

    private static void viewAvailableCars() {
        cars.forEach(System.out::println);
        printMainMenu();
    }

    private static void contactUs() {
        System.out.println("""
                --- Contact Us ---
                New cars showroom, vehicle workshop and service center. Accessories and equipment sales.
                Showroom open Monday to Friday from 09:00-20:00 and Saturday from 09:00-14:00.
                At Tiergarten Strasse, 10785, Berlin.
                Website: www.topcars.com
                Email: topcars@gmail.com
                Call us: 030-11-22-33-44-55
                """);
        printMainMenu();
    }
}
