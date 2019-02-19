public class Main2 {
    private static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (!stop) {
            }
        }).start();

        Thread.sleep(5000);
        stop = true;
    }
}
