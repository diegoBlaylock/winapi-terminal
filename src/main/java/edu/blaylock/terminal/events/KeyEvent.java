package edu.blaylock.terminal.events;

public class KeyEvent extends Event {


    public final boolean keyDown;
    public final int repeatCount;
    public final int virtualKeyCode;
    public final int virtualScanCode;

    public final char character;

    public final int controlKeyState;

    public KeyEvent(boolean keyDown, int repeatCount, int vKeyCode, int vScanCode, char character, int controlKeyState) {
        this.keyDown = keyDown;
        this.repeatCount = repeatCount;
        this.virtualKeyCode = vKeyCode;
        this.virtualScanCode = vScanCode;
        this.character = character;
        this.controlKeyState = controlKeyState;
    }
}
