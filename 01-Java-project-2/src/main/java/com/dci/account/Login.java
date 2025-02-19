package com.dci.account;

import java.util.Objects;

import static com.dci.utils.Utils.*;

public class Login {
    public static void login() {
        System.out.print("Please enter the username: ");
        String username = scanner.nextLine();
        System.out.print("Please enter the password: ");
        String password = scanner.nextLine();
        currentUser = users.stream().filter(user -> Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)).findAny().orElse(null);
        System.out.println(currentUser != null ? "Successfully logged in!" : "Invalid username or password! Please try again...");
    }
}
