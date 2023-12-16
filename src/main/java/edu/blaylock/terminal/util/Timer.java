package edu.blaylock.terminal.util;

public class Timer{
    private final Object TIMER_LOCK = new Object();
    private final Runnable runnable;
    private final Thread thread;

    private final long milli;
    private volatile boolean isStopped = true;
    private volatile boolean cancelled = false;

    public Timer(Runnable runnable, long milli, String name) {
        synchronized (TIMER_LOCK) {
            thread = new Thread(this::run, name);
            thread.setDaemon(true);
            this.runnable = runnable;
            this.milli = milli;
        }
    }

    public void start() {
        synchronized (this.TIMER_LOCK) {
            isStopped = false;
            thread.start();
        }
    }

    public void suspend(){
        synchronized (this.TIMER_LOCK) {
            isStopped = true;
        }
    }

    public void resume(){
        synchronized (this.TIMER_LOCK) {
            isStopped = false;
            synchronized (this) {
                notify();
            }
        }
    }

    public void stop(){
        synchronized (this.TIMER_LOCK) {
            isStopped = true;
            cancelled = true;
            thread.interrupt();
        }
    }

    private void run(){
        while(!cancelled) {
            long start = System.currentTimeMillis();

            if (isStopped) {
                try {
                    synchronized (this.TIMER_LOCK) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }

            if(cancelled) {
                return;
            }

            runnable.run();

            try {
                long requested_sleep;
                requested_sleep = milli;
                Thread.sleep(Math.max(0, requested_sleep - System.currentTimeMillis() + start));
            } catch (InterruptedException e) {return;};
        }
    }
}
