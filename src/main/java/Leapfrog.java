public class Leapfrog {
    public static void go() {
        Object monitor = new Object();
        new Thread(new Printer(monitor), "1").start();
        new Thread(new Printer(monitor), "2").start();
    }

    static class Printer extends Thread {
        private final Object monitor;
        public Printer(Object monitor) { this.monitor = monitor; }

        public void run() {
            try {
                while (!isInterrupted()) {
                    synchronized (monitor) {
                        System.out.println(Thread.currentThread().getName());
                        sleep(500);
                        monitor.notify();
                        monitor.wait();
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("Завершил работу поток: \"" + Thread.currentThread().getName() + '"');
            }
        }
    }
}
