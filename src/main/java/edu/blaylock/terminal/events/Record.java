package edu.blaylock.terminal.events;

public class Record {
    public static final int KEY_EVENT = 0x1;
    public static final int MOUSE_EVENT = 0x2;
    public static final int WINDOW_BUFFER_SIZE_EVENT = 0x4;
    public static final int MENU_EVENT = 0x8;
    public static final int FOCUS_EVENT = 0x10;

    public final int type;

    public final Event record;

    public Record(int type, Event record) {
        this.type = type;
        this.record = record;
    }

}
