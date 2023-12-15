import edu.blaylock.jni.flags.ConsoleMode;
import edu.blaylock.terminal.Terminal;
import edu.blaylock.terminal.events.Record;
import edu.blaylock.terminal.events.listeners.KeyListener;
import edu.blaylock.terminal.events.listeners.WindowBufferSizeListener;

public class Main {
    public static String prompt(Terminal t, String prompt){
        t.out.print_flush(prompt);
        return t.in.read(255);
    }

    public static void main(String[] args) throws Exception{

        System.load("C:\\Users\\diego\\IdeaProjects\\Terminal\\src\\edu\\blaylock\\jni\\temp_c\\cmake-build-debug\\temp_c.dll");
        Terminal terminal = Terminal.getInstance();

        Terminal.dispatcher.start();
        Terminal.dispatcher.addListener(Record.KEY_EVENT,
                (KeyListener) event ->
                        Terminal.getInstance().out.println("█" + event.character + ", " + event.keyDown));


        int mode = terminal.in.getConsoleMode();
        terminal.in.setConsoleMode(ConsoleMode.unsetFlags(mode));
        while(true) {
            Thread.sleep(10);
            Terminal.dispatcher.dispatchEvents();
        }
    }
}