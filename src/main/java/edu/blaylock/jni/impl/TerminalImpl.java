package edu.blaylock.jni.impl;

import edu.blaylock.jni.structs.ConsoleScreenBufferInfo;
import edu.blaylock.terminal.events.Record;

import java.util.Arrays;


public class TerminalImpl {
    private long stdin, stdout, stderr;

    public TerminalImpl() throws Exception {
        boolean success = intialize();
        if(!success) {
            throw new Exception();
        }
    }

    public native ConsoleScreenBufferInfo getConsoleScreenBufferInfo(long handle);
    public native boolean setConsoleTextAttribute(long handle, int attributeFlags);
    public native int write(long handle, String string);
    public native int read(byte[] arr, int num_bytes);

    public native int readInputEvents(Record[] records, int num_records);
    public int readInputEvents(Record[] records) {
        return readInputEvents(records, records.length);
    }

    public native int getConsolemode(long handle);
    public native boolean setConsolemode(long handle, int newMode);

    public native boolean setOutputCharacterEncoding(int newEncoding);

    public native int getOutputCharacterEncoding();

    public native boolean intialize();

    public long getStdin(){
        return stdin;
    }

    public long getStdout(){
        return stdout;
    }

    public long getStderr(){
        return stderr;
    }
}
