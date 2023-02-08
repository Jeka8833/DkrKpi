package com.Jeka8833.DkrKpi;

import org.jetbrains.annotations.NotNull;

public class Student {

    public @NotNull String name;
    public @NotNull String group;
    public int score;

    public Student(@NotNull String name, @NotNull String group, int score) {
        this.name = name;
        this.group = group;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Group: " + group + " Score: " + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
