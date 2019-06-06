package com.java_app.entity;


public enum NumberOfCabinet {
    A123(2),
    A505(3),
    B341(1),
    B102(4),
    A233(5);

    private int value;

    private NumberOfCabinet(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
