package ru.eltex.app.reflexio;

import ru.eltex.app.entity.User;

public class UserReflex extends User {
    private String about;

    @Override
    public void writeToCSV(String path) {}

    @Override
    public void fillAttributesFromCSV(String path) {}

    @Override
    public void toJSON(String path) {}

    @Override
    public void fromJSON(String path) {}
}
