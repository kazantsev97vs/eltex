package ru.eltex.app;

public class Subject {
    private Integer id;
    private String name;
    private Integer numberOfHours;

    public Integer getId() {
        return id;
    }

    public Subject setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public Subject setNumberOfHours(Integer numberOfHours) {
        this.numberOfHours = numberOfHours;
        return this;
    }

    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getNumberOfHours();
    }
}
