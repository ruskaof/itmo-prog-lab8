package com.ruskaof.common.util;

public class MutableState<T> extends State<T> {
    public MutableState(T value) {
        super(value);
    }

    public void setValue(T newValue) {
        value = newValue;
    }
}
