package ru.eltex.app;

public class Course {
    private Integer id;
    private String name;
    private Integer numberOfHours;

    public Integer getId() {
        return id;
    }

    public Course setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public Course setNumberOfHours(Integer numberOfHours) {
        this.numberOfHours = numberOfHours;
        return this;
    }

    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getNumberOfHours();
    }
}
