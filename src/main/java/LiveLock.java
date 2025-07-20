public class LiveLock {
    public static void go() {
        final Worker worker1 = new Worker("Worker 1", true);
        final Worker worker2 = new Worker("Worker 2", true);
        final CommonResource commonResource = new CommonResource(worker1);

        new Thread(() -> worker1.work(commonResource, worker2)).start();
        new Thread(() -> worker2.work(commonResource, worker1)).start();
    }
}

class CommonResource {
    private Worker owner;
    CommonResource(Worker owner) { this.owner = owner; }
    public Worker getOwner() { return owner; }
    public synchronized void  setOwner(Worker owner) { this.owner = owner; }
}

class Worker {
    private final String name;
    private boolean active;

    public Worker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() { return name; }

    public boolean getActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public synchronized void work(CommonResource cr, Worker otherWorker) {
        while (active) {

            if (cr.getOwner() != this) {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    System.err.println("Ошибка InterruptedException ( wait() ): " + e);
                }
                continue;
            }
            if (otherWorker.getActive()) {
                System.out.println(getName() + " -> " + otherWorker.getName());
                cr.setOwner(otherWorker);
            }

        }
    }
}
