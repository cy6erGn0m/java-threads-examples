import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class TaskQueue {
    private static final ExecutorService executor =
//            Executors.newFixedThreadPool(4);
            new ThreadPoolExecutor(4, 100, 1000L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(10000));

    private static final ScheduledExecutorService sched =
            Executors.newScheduledThreadPool(4);

//    private static final List<String> log = new ArrayList<>();
    private static final List<String> log = new CopyOnWriteArrayList<>();

    public static void schedule() {
        sched.scheduleAtFixedRate(() -> {
            System.out.println("current " + new Date());
        }, 0L, 1L, TimeUnit.SECONDS);
    }

    public static Future<String> processPage(int task) throws ExecutionException, InterruptedException {
        Future<String> future = executor.submit(() -> {
//            synchronized (log) {
//                log.add("Task " + task + " started");
//            }

            log.add("Task " + task + " started");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < task; i++) {
                sb.append("i = ");
                sb.append(i);
                sb.append("\n");
            }

            return sb.toString();
        });

//        future.cancel(false);

        return future;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        schedule();

        ArrayList<Future<String>> allResults = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            allResults.add(processPage(i));
        }

        executor.shutdown();
        if (!executor.awaitTermination(10L, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

//        allResults.forEach(s -> {
//            System.out.println(s);
//        });

        Thread.sleep(5000);
        sched.shutdown();

        log.forEach(logEntry -> {
            System.out.println(logEntry);
        });
    }
}
