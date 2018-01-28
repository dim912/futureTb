package dim.multiThreading;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * notify, wait and notifyAll are final native methods in Object class
 * These methods are used to communicate lock status of a resource
 * between threads.
 * notify => wakes up one thread. Thread selection depends on OS implementation
 * notifyAll => wakes ups all thread. Which thread process is OS dependent
 * wait => wait Infinitly till other method call notify/notifyAll or timeout => to wakeup current thread
 * => the thread which invoke this methods should have the object monitor
 * => else => java.lang.IllegalMonitorStateException is thrown(run time)
 * Can be used to : implement producer/consumer problem.
 * Consumer thread are waiting for Objects in a Queue. and Producer put objects in a queue and notify waiting consume threads.
 */

/**
 * wait => release the lock and goto waiting state.
 * notify/notify all => notify all waiting threads to resume and look for the lock. Notify does not release the lock from current thread.
 *
 * Why wait/notify inside synchronized blocks
 * ------------------------------------------
 * wait() is called conditionally (if(condition) obj.wait())
 *  => But the condition is changed by an activity of another thread => because of that wait/notify methods should be called inside synchronized blocks.
 *
 * to avoid dead locks
 *  => Ex : In producer-consumer case => If synchronized is not used => There is a posibility consumer does not notice the last message (If the
 *  mesasge is entered to the queue by the producer after consumer checks the isEmpty() condition. => Then consumer goes to a deadLock.
 *  => because of wait() => thread does not wants to continuesly check the condition in a loop.
 * */
public class NotifyWaitTest {

    public static Queue<String> queue = new ArrayDeque<>();

    public static void main(String[] args) {
        new Thread(new Consumer(queue), "consumer1").start();
        new Thread(new Consumer(queue), "consumer2").start();
        new Thread(new Producer(queue), "producer1").start();
        new Thread(new Producer(queue), "producer2").start();
    }
}

class Consumer implements Runnable {
    Queue<String> queue;
    boolean started = true;
    public Consumer(Queue<String> messages) {
        this.queue = messages;
    }
    @Override
    public void run() {
        while (started) {
            synchronized (queue) {
                System.out.println(Thread.currentThread().toString() + ": trying to Consume : " + queue.size());
                if (queue.size() == 0) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().toString() + ":" + queue.poll());
                }
            }
        }
    }
}

class Producer implements Runnable {
    Queue<String> queue;
    boolean started = true;
    public Producer(Queue<String> messages) {
        this.queue = messages;
    }
    @Override
    public void run() {
        while (started) {
            synchronized (queue) {
                System.out.println(Thread.currentThread().toString() + ": trying to Produce :" + queue.size());
                try {
                    queue.add(Long.toString(System.currentTimeMillis()));
                    //queue.notifyAll();
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}