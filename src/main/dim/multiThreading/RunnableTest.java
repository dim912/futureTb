package dim.multiThreading;

public class RunnableTest {

    public static void main(String[] args) {
        System.out.println("Start main Thread " + Thread.currentThread().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("\n start syncMethodCall : " + Thread.currentThread().toString());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\n end syncMethodCall : " + Thread.currentThread().toString());
            }
        }).start();
        System.out.println("End main Thread " + Thread.currentThread().toString());
    }
}
