package edu.blaylock.terminal.events;

public abstract class Event {
    protected boolean consumed;


    public void consume() {
        consumed = true;
    }

    public boolean consumed() {
        return consumed;
    }
}
