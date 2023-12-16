package edu.blaylock.terminal.io;

import edu.blaylock.jni.impl.TerminalImpl;

public class Reader {
    private final TerminalImpl impl;
    private final long handle;

    private final byte[] buffer;

    public Reader(long handle, TerminalImpl impl, int buffer_size) {
        this.handle = handle;
        this.impl = impl;
        buffer = new byte[buffer_size];
    }

    public String read(int num_chars){
        int num_chars_read = impl.read(buffer, num_chars);
        return new String(buffer, 0, num_chars_read);
    }

    public String read(){
        return read(255);
    }

    public int getConsoleMode(){
        return impl.getConsolemode(handle);
    }

    public void setConsoleMode(int mode){
        boolean success = impl.setConsolemode(handle, mode);
    }


}
