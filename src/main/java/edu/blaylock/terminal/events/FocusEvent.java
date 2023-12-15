package edu.blaylock.terminal.events;

public class FocusEvent extends Event{
    public final boolean isFocused;

    public FocusEvent(boolean isFocused) {
        this.isFocused = isFocused;
    }
}
