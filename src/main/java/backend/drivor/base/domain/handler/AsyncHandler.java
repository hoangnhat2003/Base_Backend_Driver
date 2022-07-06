package backend.drivor.base.domain.handler;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncHandler {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public AsyncHandler() {
    }

    public static <T> CompletableFuture<T> run(Callable<T> callable) {
        CompletableFuture<T> result = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                result.complete(callable.call());
            } catch (Throwable var3) {
                result.completeExceptionally(var3);
            }

        }, executor);
        return result;
    }
}
