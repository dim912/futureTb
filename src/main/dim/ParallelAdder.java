package dim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelAdder {

    //calls sequential adder and parallel adder one after another
    public static void main(String[] args) {
        ParallelAdder adder = new ParallelAdder();
        adder.sequentialSum(100000000L);
        adder.parallelSum(100000000L,4,(100000000L/4));
        System.out.println("Done");
    }

    public Long parallelSum(Long upto,long threadPoolSize, long rangeForaExecutor) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Long>> list = new ArrayList<>();

        long range = rangeForaExecutor;
        long from = -1;
        long to = -1;
        Long start = System.currentTimeMillis();

        for (long i = 0; i < Math.ceil(upto / range); i++) {
            from = to+1;
            to = from + range < upto ? from+ range : upto ;
            final long f = from;
            final long t = to;
            //passing sliced range to a Executor service submit method
            //which runs it in a separate thread and return a future
            Future<Long> future = executor.submit(new Callable<Long>() {
                Long from = f;
                Long to = t;
                public Long call() throws Exception {
                    System.out.println("A new thread is started : " + from + ":"+to) ;
                    Long totsum = 0L ;
                    for(long i = from ; i <= to ;  i++){
                        totsum +=  i;
                    }
                    return totsum;
                }
            });
            list.add(future);
        }
        long totsum = 0;
        //get result from futures and add
        for (Future<Long> fut : list) {
            try {
                totsum = totsum + fut.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("Parallel:sum:" + totsum+":"+start+":"+end+ ":"+(end-start));
        executor.shutdown();
        return totsum;
    }

    public long sequentialSum(Long upto) {
        Long totsum = 0L;

        Long start = System.currentTimeMillis();
        for (long i = 0; i <= upto; i++) {
            totsum = totsum + i;
        }
        Long end = System.currentTimeMillis();
        System.out.println("Sequential:sum:" + totsum+":"+start+":"+end+ ":"+(end-start));
        return totsum;
    }
}
