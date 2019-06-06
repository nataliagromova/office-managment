package com.java_app.entity;

public enum Projects {
    The_best_project(3),
    The_worst_project(2),
    The_project_California(1);
    private int value;

    private Projects(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
