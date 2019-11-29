package ru.eltex.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltex.app.entity.User;
import ru.eltex.app.tools.FileTools;
import ru.eltex.app.tools.Parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teacher extends User {
    private List<Course> courseList = new ArrayList<>();

    Teacher addSubject (Course course) {
        courseList.add(course);
        return this;
    }

    Teacher removeSubject (Course course) {
        courseList.remove(course);
        return this;
    }

    Teacher removeSubject (int index) {
        courseList.remove(index);
        return this;
    }

    public List<Course> getSubjectList() {
        return courseList;
    }

    public Teacher setSubjectList(List<Course> courseList) {
        this.courseList = courseList;
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
                String.valueOf(courseList)
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

        Course[] courses = new Course[lines.length];

        for (int i = 0; i < lines.length; i++) {

            String[] courseLine = lines[i].split(" ");
            courses[i] = new Course()
                    .setId(Integer.parseInt(courseLine[0]))
                    .setName(courseLine[1])
                    .setNumberOfHours(Integer.parseInt(courseLine[2]));
        }

        courseList = Arrays.asList(courses);
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

    public static void main(String[] args) {
        Teacher t = new Teacher();
        Field[] fields = Teacher.class.getDeclaredFields();

        for (Field f :
                fields) {
            System.out.println(f.getName());
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString() + "\ncourseList: " + getSubjectList();
    }
}