package me.melondev.maes.api.concurrent;

import java.util.Arrays;

public interface Callback<T> {
    void call(T t);

    /**
     * Throw callback exception to system log.
     * @param throwable an {@code Throwable} object.
     */
    default void handleException(final Throwable throwable) {
        System.out.println("Internal error occurred while handling callback.");
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            System.out.println(stackTraceElement.getClassName() + " | " + stackTraceElement.getLineNumber());
        }
    }
}
