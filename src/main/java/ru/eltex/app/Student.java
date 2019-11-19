package ru.eltex.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltex.app.tools.FileTools;
import ru.eltex.app.tools.Parser;

public class Student extends User {

    private Integer numberOfAccountBook = 1; // номер учетной книжки

    public Integer getNumberOfAccountBook() {
        return numberOfAccountBook;
    }

    public void setNumberOfAccountBook(Integer numberOfAccountBook) {
        this.numberOfAccountBook = numberOfAccountBook;
    }

    @Override
    public void writeToCSV(String path) {
        FileTools.write(path,
            Parser.convertToString(new String[]{
                String.valueOf(getId()),
                getName(),
                getSurname(),
                getPatronymic(),
                getPhoneNumber(),
                String.valueOf(getNumberOfAccountBook())
        }, getPATTERN()));
    }

    @Override
    public void fillAttributesFromCSV(String path) {
        String[] values = FileTools.read(path).split(getPATTERN());

        setId(Integer.parseInt(values[0]));
        setName(values[1]);
        setSurname(values[2]);
        setPatronymic(values[3]);
        setPhoneNumber(values[4]);
        setNumberOfAccountBook(Integer.parseInt(values[5]));
    }

    @Override
    public void toJSON(String path) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(this);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        FileTools.write(path, jsonString);
    }

    @Override
    public void fromJSON(String path) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Student student = mapper.readValue(FileTools.read(path), this.getClass());

            setId(student.getId());
            setName(student.getName());
            setSurname(student.getSurname());
            setPatronymic(student.getPatronymic());
            setPhoneNumber(student.getPhoneNumber());
            setNumberOfAccountBook(student.getNumberOfAccountBook());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nnumberOfAccountBook: " + getNumberOfAccountBook();
    }
}
