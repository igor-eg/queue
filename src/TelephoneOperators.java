public class TelephoneOperators extends Thread {
    private static final int FLOW_DELAY_TIME = 3000;

    TelephoneOperators(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(FLOW_DELAY_TIME);
                System.out.printf("Я %s,принял   %s!\n", getName(), Main.callCenterQueue.take());
            }
        } catch (InterruptedException err) {
        }
    }
}


