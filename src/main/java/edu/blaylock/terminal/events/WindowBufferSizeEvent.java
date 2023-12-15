package edu.blaylock.terminal.events;

import edu.blaylock.jni.structs.Coord;

public class WindowBufferSizeEvent extends Event {

    private final Coord size;

    public WindowBufferSizeEvent(Coord size ){
        this.size = size;
    }

    public Coord size(){
        return size;
    }
}
