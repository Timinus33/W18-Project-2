package com.dci.menus;

import com.dci.account.Login;
import com.dci.account.Register;

import static com.dci.utils.Utils.*;

public class AccountMenu extends MainMenu {
    private static final int LOGIN_MENU_ID = 1;
    private static final int USER_MENU_ID = 2;
    private static final int ADMIN_MENU_ID = 3;

    private static final String LOGIN_ACCOUNT_MENU = """
            \n--- Account ---
            1. Log in
            2. Register
            3. Back
            4. Exit""";
    private static final String USER_ACCOUNT_MENU = """
            \n--- Account ---
            1. Saved cars
            2. Purchased cars
            3. Check balance
            4. Deposit
            5. Withdraw
            6. Log out
            7. Back
            8. Exit""";
    private static final String ADMIN_ACCOUNT_MENU = """
            \n--- Account ---
            1. Edit list of cars
            2. View all accounts
            3. Remove an account
            4. Log out
            5. Back
            6. Exit""";

    public static void printAccountMenu() {
        if (currentUser == null) {
            System.out.println(LOGIN_ACCOUNT_MENU);
            processMenu(LOGIN_MENU_ID);
        } else if (currentUser.isAdmin()) {
            System.out.println(ADMIN_ACCOUNT_MENU);
            processMenu(ADMIN_MENU_ID);
        } else {
            System.out.println(USER_ACCOUNT_MENU);
            processMenu(USER_MENU_ID);
        }
    }

    private static void processMenu(int menuType) {
        System.out.print("Please enter an option: ");
        String option = scanner.nextLine();
        if (option != null && !option.isBlank() && option.chars().allMatch(Character::isDigit)) {
            if (menuType == LOGIN_MENU_ID) {
                processLoginMenu(LOGIN_MENU_ID, Integer.parseInt(option));
            } else if (menuType == USER_MENU_ID) {
                processLoginMenu(USER_MENU_ID, Integer.parseInt(option));
            } else if (menuType == ADMIN_MENU_ID) {
                processLoginMenu(ADMIN_MENU_ID, Integer.parseInt(option));
            }
        } else {
            System.out.println(WRONG_INPUT);
            printAccountMenu();
        }
    }

    private static void processLoginMenu(int menuType, int option) {
        switch (menuType) {
            case LOGIN_MENU_ID -> {
                switch (option) {
                    case 1 -> {
                        Login.login();
                        printAccountMenu();
                    }
                    case 2 -> {
                        Register.register();
                        printAccountMenu();
                    }
                    case 3 -> printMainMenu();
                    case 4 -> System.exit(0);
                    default -> {
                        System.out.println(WRONG_INPUT);
                        printAccountMenu();
                    }
                }
            }
            case USER_MENU_ID -> {
                switch (option) {
                    case 1 -> {
                        if (!currentUser.getSavedCars().isEmpty()) {
                            currentUser.getSavedCars().forEach(System.out::println);
                            printAccountMenu();
                        } else {
                            System.out.println("No cars found!");
                            printAccountMenu();
                        }
                    }
                    case 2 -> {
                        if (!currentUser.getPurchasedCars().isEmpty()) {
                            currentUser.getPurchasedCars().forEach(System.out::println);
                            printAccountMenu();
                        } else {
                            System.out.println("No cars found!");
                            printAccountMenu();
                        }
                    }
                    case 3 -> {
                        System.out.println("Current balance: " + currentUser.getBalance() + "$");
                        printAccountMenu();
                    }
                    case 4 -> {
                        currentUser.deposit();
                        printAccountMenu();
                    }
                    case 5 -> {
                        currentUser.withdraw();
                        printAccountMenu();
                    }
                    case 6 -> {
                        currentUser = null;
                        printAccountMenu();
                    }
                    case 7 -> printMainMenu();
                    case 8 -> System.exit(0);
                    default -> {
                        System.out.println(WRONG_INPUT);
                        printAccountMenu();
                    }
                }
            }
            case ADMIN_MENU_ID -> {
                switch (option) {
                    case 1 -> {
                        System.out.println("In Progress...");
                        printAccountMenu();
                    }
                    case 2 -> {
                        users.forEach(System.out::println);
                        printAccountMenu();
                    }
                    case 3 -> {
                        deleteUserAccount();
                        printAccountMenu();
                    }
                    case 4 -> {
                        currentUser = null;
                        printAccountMenu();
                    }
                    case 5 -> printMainMenu();
                    case 6 -> System.exit(0);
                    default -> {
                        System.out.println(WRONG_INPUT);
                        printAccountMenu();
                    }
                }
            }
        }
    }
}
