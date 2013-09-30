package pl.edu.pw.elka.pfus.eds.web.jersey;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public abstract class LazyValue<T> {
    private final AtomicReference<FutureTask<T>> futureRef = new AtomicReference<>();

    public final T get() throws InterruptedException, ExecutionException {
        FutureTask<T> future = futureRef.get();
        if(future == null) {
            future = new FutureTask<>(new Callable<T>() {
                public T call() throws Exception {
                    return compute();
                }
            });

            if(futureRef.compareAndSet(null, future)) {
                future.run();
            } else {
                future = futureRef.get();
            }
        }
        return future.get();
    }

    protected abstract T compute() throws Exception;
}
