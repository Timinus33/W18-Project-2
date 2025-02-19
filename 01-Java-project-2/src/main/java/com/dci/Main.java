package com.dci;

import com.dci.menus.MainMenu;
import com.dci.utils.Utils;

public class Main {
    public static void main(String[] args) {
        Utils.loadCars();
        Utils.loadAccounts();

        System.out.println("\n*** Welcome to TopCars! ***");
        MainMenu.printMainMenu();
    }
}