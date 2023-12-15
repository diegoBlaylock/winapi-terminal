package edu.blaylock.terminal.events;

public class MenuEvent extends Event {
    public final int menuItemID;

    public MenuEvent(int menuItemID) {
        this.menuItemID = menuItemID;
    }
}
