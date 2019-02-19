public class Main3 {
    private static volatile boolean stop = false;
    private static int exitCode = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 200; ++i) {
            stop = false;
            exitCode = 0;

            new Thread(() -> {

                try {
                    while (true) {
                        if (stop) {
                            System.out.println("exitCode = " + exitCode);
                            break;
                        }
                        Thread.sleep(1);
                    }
                } catch (InterruptedException ignore) {
                }
            }).start();

            Thread.sleep(2);

            exitCode = 1;
            stop = true;
        }
    }

    void test() {
        int a = 1;
        int b = 2;

        a = a + 1;
        a = a + 2;
        a = a + 3;
        a = a + 4;
        b = b + 2;
    }
}
