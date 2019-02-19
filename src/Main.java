import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final String counterLock = "counterLock";
    private static final Lock lock = new ReentrantLock();

//    private static long counter = 0;
    private static final AtomicLong counter = new AtomicLong(0);

    static class Incrementor implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100000; ++i) {
                counter.incrementAndGet();
                counter.set(1);

//                counter.addAndGet(1L);

//                do {
//                    long current = counter.get();
//                    long newValue = current + 1;
//
//                    if (counter.compareAndSet(current, newValue)) {
//                        break;
//                    }
//                } while (true);

//                counter = counter + 1;

//                synchronized (counterLock) {
//                    counter = counter + 1;
//                }

//                lock.lock();
//                try {
//                    counter = counter + 1;
//                } finally {
//                    lock.unlock();
//                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> allThreads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            allThreads.add(new Thread(new Incrementor()));
        }

        allThreads.forEach(thread -> {
            thread.start();
        });

        allThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        });

        System.out.println("counter = " + counter.get());
    }
}
