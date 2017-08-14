package dim;

import java.util.concurrent.Callable;

public class CallableAdder implements Callable<Long> {
    Long from;
    Long to;

    public CallableAdder(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    public Long call() throws Exception {
        System.out.println("A new thread is started : " + from + ":"+to) ;
        Long totsum = 0L ;
        for(long i = from ; i <= to ;  i++){
            totsum +=  i;
        }
        return totsum;
    }
}