package dim.multiThreading;

/**
 * All multi threading is built by the Thread class. All magic is done by the start0 method of Thread class.
 * it runs the run() method of targent object on a different thread started at OS.
 *
 * */
public class ThreadTest extends Thread{ //Thread class is comming from java.lang

    public static SyncClass syncClass = new SyncClass();

    public static void main(String[] args){
        System.out.println("Start main Thread " + Thread.currentThread().toString());
        //internally it calls the native start0 method.
        new ThreadTest().start();
        new ThreadTest().start();
        System.out.println("End main Thread " + Thread.currentThread().toString());
    }

    @Override
    public void run(){
        System.out.println("Starting thread : :"  + Thread.currentThread().toString());
        try {
            syncClass.printCalled(Thread.currentThread().toString());
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SyncClass{

    //Object level lock. lock appyies on the object this.
    public synchronized  void printCalled(String calledBy) throws InterruptedException {
        System.out.println("\n start syncMethodCall : " + calledBy);
        Thread.sleep(1000L);
        System.out.println("End syncMethodCall : " + calledBy + "\n");
    }
    /* same as above
    public void printCalled(String calledby){
        synchronized (this){
        }
    }*/


    //lock applies on SyncClass.class object. This is independent from object level lock
    public static synchronized  void staticPrintCalled(String calledBy) throws InterruptedException {
        System.out.println("\n start syncMethodCall : " + calledBy);
        Thread.sleep(1000L);
        System.out.println("End syncMethodCall : " + calledBy + "\n");
    }
        /* same as above
    public void printCalled(String calledby){
        synchronized (SyncClass.class){
        }
    }*/

}

