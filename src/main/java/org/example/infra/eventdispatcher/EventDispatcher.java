package org.example.infra.eventdispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventDispatcher<T> {
    private final List<Consumer<T>> listeners = new ArrayList<>();

    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void dispatch(T event) {
        for (Consumer<T> listener: listeners) {
            listener.accept(event);
        }
    }
}
