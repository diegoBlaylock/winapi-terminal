package edu.blaylock.jni.flags;

public class ConsoleMode {

    public static int ENABLE_PROCESSED_INPUT  = 0x1;
    public static int ENABLE_LINE_INPUT  = 0x2;
    public static int ENABLE_ECHO_INPUT = 0x4;
    public static int ENABLE_WINDOW_INPUT  = 0x8;
    public static int ENABLE_MOUSE_INPUT  = 0x10;
    public static int ENABLE_INSERT_MODE  = 0x20;
    public static int ENABLE_QUICK_EDIT_MODE  = 0x40;
    public static int ENABLE_EXTENDED_FLAGS = 0x80;
    public static int ENABLE_AUTO_POSITION = 0x100;
    public static int ENABLE_VIRTUAL_TERMINAL_INPUT   = 0x200;

    public static int ENABLE_PROCESSED_OUTPUT = 0x1;
    public static int ENABLE_WRAP_AT_EOL_OUTPUT  = 0x2;
    public static int ENABLE_VIRTUAL_TERMINAL_PROCESSING  = 0x4;
    public static int DISABLE_NEWLINE_AUTO_RETURN  = 0x8;
    public static int ENABLE_LVB_GRID_WORLDWIDE  = 0x10;


    public static int setFlags(int consoleMode, int... flags) {
        for(int f : flags) consoleMode |= f;
        return consoleMode;
    }

    public static int unsetFlags(int consoleMode, int... flags) {
        for(int f : flags) consoleMode &= ~f;
        return consoleMode;
    }
}
