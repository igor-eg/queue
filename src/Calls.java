public class Calls extends Thread {
    private static final int FLOW_DELAY_TIME = 1000;

    Calls(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (!isInterrupted()) {
                System.out.printf("Поступил %s!\n", getName());
                Thread.sleep(FLOW_DELAY_TIME);
                i++;
                Main.callCenterQueue.put("Звонок " + i);
            }
        } catch (InterruptedException err) {
        }
    }
}
