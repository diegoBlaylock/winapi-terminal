package edu.blaylock.terminal.events.listeners;

import edu.blaylock.terminal.events.Event;

public interface Listener<T extends Event>{

    @SuppressWarnings("unchecked")
    default void handleGeneric(Event event) {
        handleEvent((T) event);
    }
    void handleEvent(T event);
}
