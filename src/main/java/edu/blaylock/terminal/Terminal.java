package edu.blaylock.terminal;

import edu.blaylock.jni.flags.BufferMode;
import edu.blaylock.jni.impl.TerminalImpl;
import edu.blaylock.jni.structs.ConsoleScreenBufferInfo;
import edu.blaylock.terminal.events.EventDispatcher;
import edu.blaylock.terminal.io.Reader;
import edu.blaylock.terminal.io.Writer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Terminal {
    private static final Terminal global_instance;
    private static final int initialStdinMode;
    private static final int initialStdoutMode;
    private static final int initialStderrMode;

    private static final int initialOutputEncoding;

    public static final EventDispatcher dispatcher;


    static {
        global_instance = new Terminal();
        initialStdinMode = global_instance.in.getConsoleMode();
        initialStdoutMode = global_instance.out.getConsoleMode();
        initialStderrMode = global_instance.err.getConsoleMode();
        initialOutputEncoding = global_instance.impl.getOutputCharacterEncoding();
        dispatcher = new EventDispatcher(global_instance.impl, 1024);
    }

    public static Terminal getInstance() {
        return global_instance;
    }

    public final long STDOUT;
    public final long STDIN;
    public final long STDERR;

    public final Writer out;
    public final Writer err;

    public final Reader in;

    private TerminalImpl impl;

    public Terminal() {
        try{
            impl = new TerminalImpl();
        }catch(Exception e){
            System.exit(1);
        }
        STDOUT = impl.getStdout();
        STDIN = impl.getStdin();
        STDERR = impl.getStderr();

        out = new Writer(STDOUT, impl, BufferMode.LINE_FLUSH);
        err = new Writer(STDERR, impl, BufferMode.ALWAYS_FLUSH);
        in = new Reader(STDIN, impl, 255);
    }

    public static void resetModes() {
        global_instance.in.setConsoleMode(initialStdinMode);
        global_instance.out.setConsoleMode(initialStdoutMode);
        global_instance.err.setConsoleMode(initialStderrMode);
    }
}
