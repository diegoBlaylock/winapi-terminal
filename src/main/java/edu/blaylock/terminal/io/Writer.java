package edu.blaylock.terminal.io;

import edu.blaylock.jni.flags.BufferMode;
import edu.blaylock.jni.impl.TerminalImpl;
import edu.blaylock.jni.structs.ConsoleScreenBufferInfo;

import java.nio.charset.StandardCharsets;

public class Writer {
    private StringBuilder writeBuffer = new StringBuilder();
    private BufferMode bufferMode = BufferMode.LINE_FLUSH;

    private TerminalImpl impl;

    private long handle;

    public Writer(long handle, TerminalImpl impl, BufferMode buffer){
        this.handle = handle;
        this.impl = impl;
        this.bufferMode = buffer;
    }

    public int flush(){
        if(writeBuffer.isEmpty()) return 0;
        int written = impl.write(handle, writeBuffer.toString());
        writeBuffer.setLength(0);
        writeBuffer.trimToSize();
        return written;
    }

    public void print(String message){
        if(message == null || message.isEmpty()) return;
        writeBuffer.append(message);
        flushIfNeeded(message);
    }

    public void println(String message) {
        print(message);
        print("\n");
    }

    public void print_flush(String message) {
        print(message);
        flush();
    }

    public BufferMode bufferMode() {
        return bufferMode;
    }

    public void bufferMode(BufferMode newBufferMode) {
        bufferMode = newBufferMode;
    }

    private void flushIfNeeded(String message){
        boolean shouldFlush = switch(bufferMode()){
            case LINE_FLUSH-> message.contains("\n");
            case NO_FLUSH -> false;
            case ALWAYS_FLUSH -> true;
        };

        if(shouldFlush) flush();
    }

    public int getConsoleMode(){
        return impl.getConsolemode(handle);
    }

    public void setConsoleMode(int mode){
        impl.setConsolemode(handle, mode);
    }

    public ConsoleScreenBufferInfo getConsoleBufferInfo() {
        return impl.getConsoleScreenBufferInfo(handle);
    }
}
