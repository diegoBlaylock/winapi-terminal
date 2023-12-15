package edu.blaylock.terminal.events;

import edu.blaylock.jni.impl.TerminalImpl;
import edu.blaylock.terminal.events.listeners.*;
import edu.blaylock.terminal.util.Timer;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDispatcher{
    private final Collection<WeakReference<KeyListener>> keyListeners = Collections.synchronizedCollection(new ArrayDeque<>());
    private final Collection<WeakReference<FocusListener>> focusListeners = Collections.synchronizedCollection(new ArrayDeque<>());
    private final Collection<WeakReference<MenuListener>> menuListeners = Collections.synchronizedCollection(new ArrayDeque<>());
    private final Collection<WeakReference<WindowBufferSizeListener>> wbsListeners = Collections.synchronizedCollection(new ArrayDeque<>());
    private final Queue<Record> recordQueue = new ArrayDeque<>();
    private final Collection<TypeListener> listenersToAdd = Collections.synchronizedCollection(new ArrayDeque<>());
    private final Record[] records;
    private final TerminalImpl impl;

    private final Timer timer;

    private volatile boolean disabled = false;

    public EventDispatcher(TerminalImpl impl, int buffer_size) {
        this.impl = impl;
        this.records = new Record[buffer_size];
        this.timer = new Timer(this::run, 25, "EventDispatcher");
    }

    public void dispatchEvents() {
        if (disabled) return;
        synchronized (listenersToAdd) {
            for (TypeListener t_listener : listenersToAdd) {
                addListenerByType(t_listener);
            }
            listenersToAdd.clear();
        }

        synchronized (recordQueue) {

            for (Record r : recordQueue) {
                var listener = getListenerCollectionByType(r.type);
                if (listener == null) continue;
                var iter = listener.iterator();
                while (iter.hasNext()) {
                    Listener<?> listen = iter.next().get();
                    if(listen == null) {
                        iter.remove();
                        continue;
                    }
                    if(!r.record.consumed()) listen.handleGeneric(r.record);
                }
            }
        }
        recordQueue.clear();
    }



    public  void  addListener(int type, Listener<? extends Event> listener){
        synchronized (listenersToAdd) {
            listenersToAdd.add(new TypeListener(type, listener));
        }
    }

    public void start() {
        timer.start();
    }

    public void suspend() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }

    public void disable() {
        disabled = true;
    }
    public void enable() {
        flush();
        disabled = false;
    }

    public void flush() {
        synchronized (recordQueue) {
            recordQueue.clear();
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<WeakReference<Listener<? extends Event>>> getListenerCollectionByType(int type) {
        return (Collection<WeakReference<Listener<? extends Event>>>) switch (type) {
            case Record.KEY_EVENT -> keyListeners;
            case Record.MENU_EVENT -> menuListeners;
            case Record.WINDOW_BUFFER_SIZE_EVENT -> wbsListeners;
            case Record.FOCUS_EVENT -> focusListeners;
            default -> null;
        };
    }

    private void addListenerByType(TypeListener t_listener){
        var listeners = getListenerCollectionByType(t_listener.type);
        listeners.add(new WeakReference<>(t_listener.listener));
    }

    private void run() {
        int inputsReceived = impl.readInputEvents(records);
        if(inputsReceived < 0) {
            Logger.getLogger("Nope").log(Level.SEVERE, "Problem");
            System.exit(-100);
        }
        synchronized (recordQueue) {
            if(disabled) return;
            Logger.getLogger("Nope").log(Level.INFO, String.format("RECORDS_SENT + %d", inputsReceived));
            recordQueue.addAll(Arrays.asList(records).subList(0, inputsReceived));
        }
    }

    record TypeListener (int type, Listener<? extends Event> listener)
        {}
}
