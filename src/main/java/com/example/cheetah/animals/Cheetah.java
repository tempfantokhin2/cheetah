package com.example.cheetah.animals;

import java.util.List;
import java.util.Objects;

public class Cheetah {
    String name;
    double age;
    double speed;

    public Cheetah(String name, double age, double speed) {
        this.name = name;
        this.age = age;
        this.speed = speed;
    }

    public int doHunt(List<Gazelle> gazelles) {
        if(gazelles.isEmpty()) {
            System.out.println(name + ": nothing to catch");
            return -1;
        }
        int slowestGazelleIndex = findSlowestGazelle(gazelles);
        if(gazelles.get(slowestGazelleIndex).getSpeed()<speed) {
            gazelles.get(slowestGazelleIndex).doSquirm();
            gazelles.remove(slowestGazelleIndex);
            return 1;
        }
        System.out.println(name + ": damn");
        return 0;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public double getAge() {
        return age;
    }

    public double getSpeed() {
        return speed;
    }

    public int findSlowestGazelle(List<Gazelle> gazelles) {
        double minSpeed = gazelles.getFirst().getSpeed();
        int indexOfMinSpeed = 0;
        for (int i = 1; i < gazelles.size(); i++) {
            double currentGazelleSpeed = gazelles.get(i).getSpeed();
            if (minSpeed > currentGazelleSpeed) {
                minSpeed = currentGazelleSpeed;
                indexOfMinSpeed = i;
            }
        }
        return indexOfMinSpeed;
    }

    @Override
    public String toString() {
        return "Cheetah{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", speed=" + speed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cheetah cheetah = (Cheetah) o;
        return Double.compare(age, cheetah.age) == 0 && Objects.equals(name, cheetah.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
