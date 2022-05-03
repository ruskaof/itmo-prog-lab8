package com.ruskaof.common.util;

public class State<T> {
    private T value;

    public State(T value) {
        this.value = value;
    }

    protected void setValueIHATECHECKSTYLEBECAUSEICOULDUSEJUSTPROTECTEDVALUE(T newValue) {
        this.value = newValue;
    }

    public T getValue() {
        return value;
    }
}

