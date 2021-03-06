package lock;

/**
 * Parent class for each locker
 * Created by tim on 14-09-29.
 */
public abstract class Locker {
    public int thread_nb;
    public int n;

    protected int lock_granted = 0;


    /**
     * Acquires the lock.
     *
     * @return -1 if the lock was not granted or the total number of granted locks
     */
    public abstract int lock();

    /**
     * Releases the lock.
     */
    public abstract void unlock();


    public int run() {
        int max = 0;
        Thread[] threads = new Thread[thread_nb];
        LockerThread[] runnables = new LockerThread[thread_nb];
        try {
            for (int i = 0; i != thread_nb; i++) {
                runnables[i] = new LockerThread();
                threads[i] = new Thread(runnables[i]);
                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            for (LockerThread runnable : runnables) {
                if (runnable.max_delay > max) {
                    max = runnable.max_delay;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return max;
    }

    public class LockerThread implements Runnable {
        protected int max_delay = 0;


        @Override
        public void run() {
            int last_counter = 0;
            int new_counter;
            max_delay = 0;
            for (int i = 0; i < n; i++) {
                new_counter = -1;
                while (new_counter == -1) {  //Until we are granted the lock yield then try again(For synchronized)
                    new_counter = lock();
                    Thread.yield();
                }
                unlock();
                Thread.yield();
                int new_delay = new_counter - last_counter - 1;
                if (new_delay > max_delay) { //Get the number of thread who requested a lock since the last time we got one
                    max_delay = new_delay;
                }
                last_counter = new_counter;
            }
        }
    }
}
