package com.example.cheetah.animals;

import java.util.Objects;

public class Gazelle {
    String name;
    double speed;

    public Gazelle(String name, double speed) {
        this.name = name;
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public String dumpData() {
        return name+" "+speed;
    }

    public void doSquirm() {
        System.out.println(name + ": aaAAAAAAAAAAAAA");
        System.out.println("*" + name + " dies*");
    }

    @Override
    public String toString() {
        return "Cheetah{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gazelle gazelle = (Gazelle) o;
        return Objects.equals(name, gazelle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
