package ru.eltex.app.entity;
import ru.eltex.app.interfaces.CSV;
import ru.eltex.app.interfaces.JSON;

import java.lang.reflect.Field;

public abstract class User extends Person implements CSV, JSON {
    private Integer id;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;

    public String getPATTERN() {
        return ";";
    }

    Field[] getFields () {
        return User.class.getDeclaredFields();
    }


    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public User setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "\nid: " + getId() +
                "\nname: " + getName() +
                "\nsurname: " + getSurname() +
                "\npatronymic: " + getPatronymic() +
                "\nphoneNumber: " + getPhoneNumber();
    }
}