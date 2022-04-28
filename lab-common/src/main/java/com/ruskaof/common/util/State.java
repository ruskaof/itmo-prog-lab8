package com.ruskaof.common.util;

public class State<T> {
    protected T value;

    public State(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

