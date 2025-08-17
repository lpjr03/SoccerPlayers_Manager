package it.unimol.player_manager;

import it.unimol.player_manager.ui.MainMenu;

public class Main {
    public static void main(final String[] args) {
        final MainMenu mainMenu = MainMenu.getInstance();
        mainMenu.execute();
    }
}
