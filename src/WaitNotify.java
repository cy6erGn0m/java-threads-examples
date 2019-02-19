public class WaitNotify {
    private int result = 0;
    private final Object room = new Object();

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignore) {
        }
    }

    public void compute() {
        sleep(4000);

        synchronized (room) {
            result = 100;
            room.notifyAll();
        }
    }

    public void processPage() throws InterruptedException {
        synchronized (room) {
            while (result == 0) {
                room.wait();
            }
        }

//        while (true) {
//            sleep(1000000000);
//            if (result != 0) break;
//        }

        System.out.println("result = " + result);
    }

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify();
        new Thread(() -> {
            waitNotify.compute();
        }).start();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    waitNotify.processPage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
