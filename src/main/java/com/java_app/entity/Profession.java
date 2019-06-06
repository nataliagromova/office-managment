package com.java_app.entity;


public enum Profession {
    Director(1),
    Kassir(2),
    Creative_producer(3);
    private int value;

    private Profession(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
