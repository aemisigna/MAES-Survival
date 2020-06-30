package me.melondev.maes.api.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

/**
 * Executor service provider class.
 *
 * @author MelonDev
 * @since 1.0.0
 */
public final class CallbackHelper {
    /**
     * Add a callback using Futures.
     * @param listenableFuture (required) an {@code ListenableFuture} instance.
     * @param callback (required) name for the returned object.
     */
    public static <T> void add(final ListenableFuture<T> listenableFuture, final Callback<T> callback) {
        Futures.addCallback(listenableFuture, CallbackHelper.handle(callback));
    }

    /**
     * Handle requested callback.
     * @param callback a {@code Callback} object.
     * @return A {@code FutureCallback} object.
     */
    static <T> FutureCallback<T> handle(final Callback<T> callback) {
        return new FutureCallback<T>() {
            public void onSuccess(T t) {
                callback.call(t);
            }

            public void onFailure(@NotNull final Throwable throwable) {
                callback.handleException(throwable);
            }
        };
    }
}
