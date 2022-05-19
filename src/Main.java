import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int THREAD_STOP_TIME = 20000;
    private static final int NUMBER_OF_THREADS = 5;
    private static final int CALL_QUEUE = 15;
    private static List<TelephoneOperators> telephoneOperators = new ArrayList<>();
    //для хранения поступивших звоков и дальнейшей обработки выбрал ArrayBlockingQueue, так как:
    //1. Потокобезопасна
    //2.Возможность «честных» блокировок, что позволяет поочередно обрабатывать звонки и распределять между операторами
    //3. Имеет буфер, для хранения входящих звонков (определенную очередь), в отличии от той же SynchronousQueue

    static ArrayBlockingQueue<String> callCenterQueue = new ArrayBlockingQueue<String>(CALL_QUEUE);
    private static Calls call = new Calls("Звонок");

    public static void main(String[] args) {
        call.start(); //старт звонков
        creatingStreams();
        namingAndStartingStreams(); //старт операторов
        stoppingStreams(THREAD_STOP_TIME); //завершение работы колл-центра

    }

    private static void creatingStreams() {
        for (int i = 1; i < NUMBER_OF_THREADS; i++) {
            telephoneOperators.add(new TelephoneOperators("Оператор " + i));
        }
    }

    private static void namingAndStartingStreams() {
        int i = 1;
        for (TelephoneOperators entry : telephoneOperators) {
            entry.start();
            i++;
        }
    }

    private static void stoppingStreams(int THREAD_STOP_TIME) {
        try {
            Thread.sleep(THREAD_STOP_TIME);
            for (TelephoneOperators entry : telephoneOperators) {
                entry.interrupt();
            }
            call.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("завершаю работу колл-центра");

    }
}