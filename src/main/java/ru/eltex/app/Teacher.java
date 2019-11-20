package ru.eltex.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltex.app.tools.FileTools;
import ru.eltex.app.tools.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher extends User {
    private List<Subject> subjectList = new ArrayList<>();

    Teacher addSubject (Subject subject) {
        subjectList.add(subject);
        return this;
    }

    Teacher removeSubject (Subject subject) {
        subjectList.remove(subject);
        return this;
    }

    Teacher removeSubject (int index) {
        subjectList.remove(index);
        return this;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public Teacher setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
        return this;
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
                String.valueOf(subjectList)
            }, getPATTERN()));
    }

    @Override
    public void fillAttributesFromCSV(String path) {
//        String[] values = FileTools.read(path).split(getPATTERN());


        String[] values = Parser.parseFromString(FileTools.read(path), getPATTERN());
        setId(Integer.parseInt(values[0]));
        setName(values[1]);
        setSurname(values[2]);
        setPatronymic(values[3]);
        setPhoneNumber(values[4]);
        setSubjectListStr(values[5]);
    }

    public void setSubjectListStr(String line) {
        String[] lines = line.substring(1, line.length() - 1).split(", ");

        Subject[] subjects = new Subject[lines.length];

        for (int i = 0; i < lines.length; i++) {

            String[] subjectLine = lines[i].split(" ");
            subjects[i] = new Subject()
                    .setId(Integer.parseInt(subjectLine[0]))
                    .setName(subjectLine[1])
                    .setNumberOfHours(Integer.parseInt(subjectLine[2]));
        }

        subjectList = Arrays.asList(subjects);
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
            String line = FileTools.read(path);
            Teacher teacher = mapper.readValue(line, this.getClass());

            System.out.println(teacher);
            setId(teacher.getId());
            setName(teacher.getName());
            setSurname(teacher.getSurname());
            setPatronymic(teacher.getPatronymic());
            setPhoneNumber(teacher.getPhoneNumber());
            setSubjectList(teacher.getSubjectList());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nsubjectList: " + getSubjectList();
    }
}