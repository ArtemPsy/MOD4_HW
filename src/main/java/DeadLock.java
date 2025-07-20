public class DeadLock {
    public static final Object l1 = new Object();
    public static final Object l2 = new Object();

    public static void go() {

        new Thread(() -> {
            synchronized (l1) {
                System.out.println("synchronized (l1)");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Ошибка InterruptedException: " + e);
                }
                synchronized (l2) {
                    System.out.println("synchronized l1 --> l2 .");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (l2) {
                System.out.println("synchronized (l2)");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Ошибка InterruptedException: " + e);
                }
                synchronized (l1) {
                    System.out.println("synchronized l2 --> l1 ");
                }
            }
        }).start();

    }
}
