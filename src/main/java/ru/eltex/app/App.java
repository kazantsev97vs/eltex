package ru.eltex.app;


import ru.eltex.app.tools.FileTools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {
    public static final String DIR_PATH = "data/";
    public static final String TEACHERS_PATH = "teachers";
    public static final String STUDENTS_PATH = "students";
    public static final String CSV_EXTENSION = ".csv";
    public static final String JSON_EXTENSION = ".json";



    public static void main(String[] args) {

        Student[] students = new Student[5];
        Teacher[] teachers = new Teacher[3];

        ArrayList<Subject> subjectList = new ArrayList<>();
        subjectList.add(new Subject().setId(1).setName("Mathematics").setNumberOfHours(10));
        subjectList.add(new Subject().setId(2).setName("Russian").setNumberOfHours(12));
        subjectList.add(new Subject().setId(3).setName("Informatics").setNumberOfHours(15));

        teachers[0] = new Teacher();
        teachers[0]
                .setId(1)
                .setName("K")
                .setSurname("T")
                .setPatronymic("S")
                .setPhoneNumber("8999");
        teachers[0].setSubjectList(subjectList);
//        teachers[0].setSubjectList("[1 Mathematics 5, 2 Russian 12, 3 Informatics 17]");

        System.out.println(teachers[0]);

        teachers[0].writeToCSV(DIR_PATH + TEACHERS_PATH + CSV_EXTENSION);
//
        teachers[1] = new Teacher();
        teachers[1].fillAttributesFromCSV(DIR_PATH + TEACHERS_PATH + CSV_EXTENSION);
        System.out.println(teachers[1]);

        teachers[0].toJSON(DIR_PATH + TEACHERS_PATH + JSON_EXTENSION);
        teachers[2] = new Teacher();
        teachers[2].fromJSON(DIR_PATH + TEACHERS_PATH + JSON_EXTENSION);

        System.out.println(teachers[2]);

//        students[0] = new Student();
//        students[0].setId(1).setName("Anya").setSurname("Rat").setPatronymic("Inc").setPhoneNumber("8999");
//
//        students[1] = new Student();
//        students[1].setId(2).setName("Dima").setPhoneNumber("8913");
//
//        students[2] = new Student();
//        students[2].setId(3).setName("Danila").setPhoneNumber("8923");
//
//        students[3] = new Student();
//        students[4] = new Student();
//
//        students[0].writeToCSV("students.csv");
//        students[3].fillAttributesFromCSV("students.csv");
//
//        students[3].toJSON("students.json");
//        students[4].fromJSON("students.json");
//
//        System.out.println(students[3]);
//        System.out.println(students[4]);

//        System.out.println("getId: " + students[3].getId());
//        System.out.println("getName: " + students[3].getName());
//        System.out.println("getSurname: " + students[3].getSurname());
//        System.out.println("getPatronymic: " + students[3].getPatronymic());
//        System.out.println("getPhoneNumber: " + students[3].getPhoneNumber());
//        System.out.println("getNumberOfAccountBook: " + students[3].getNumberOfAccountBook());

    }
}