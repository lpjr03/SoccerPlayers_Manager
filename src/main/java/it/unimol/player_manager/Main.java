package it.unimol.player_manager;

import it.unimol.player_manager.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = MainMenu.getInstance();
        mainMenu.execute();
    }
}