package ru.eltex.app.controllers;

import ru.eltex.app.Teacher;
import ru.eltex.app.controllers.db.AbstractController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherController extends AbstractController <Teacher, Integer> {

    public static final String SELECT_ALL_TEACHERS = "SELECT * FROM univ.teachers";
    public static final String INSERT_TEACHER = "INSERT INTO univ.teachers (name, surname, patronymic, phone) VALUE (";
    public static final String DELETE_BY_ID = "DELETE FROM univ.teachers WHERE id = ";
    public static final String GET_BY_ID = "SELECT * FROM teachers WHERE id = ";
    public static final String UPDATE = "UPDATE teachers SET name = 'KOSTYA' WHERE id = 2;";


    @Override
    public List getAll() {
        createConnection();
        List<Teacher> teachers = new ArrayList<>();
        Statement statement = createStatement();
        ResultSet set = null;
        try {
            set = statement.executeQuery(SELECT_ALL_TEACHERS);

            while (set.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(set.getInt("id"));
                teacher.setName(set.getString("name"));
                teacher.setSurname(set.getString("surname"));
                teacher.setPatronymic(set.getString("patronymic"));
                teacher.setPhoneNumber(set.getString("phone"));

                teachers.add(teacher);

//                TODO  courses;
            }

            set.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Закрываем соединения");
            closeStatement(statement);
            createConnection();
        }

        return teachers;
    }

    @Override
    public Teacher update(Teacher entity) {
        return null;
    }

    @Override
    public Teacher getEntityById(Integer id) {
        createConnection();
        Statement statement = createStatement();
        ResultSet set = null;
        Teacher teacher = new Teacher();

        try {
            set = statement.executeQuery(GET_BY_ID + id + ";");

            while (set.next()) {
                teacher.setId(set.getInt("id"));
                teacher.setName(set.getString("name"));
                teacher.setSurname(set.getString("surname"));
                teacher.setPatronymic(set.getString("patronymic"));
                teacher.setPhoneNumber(set.getString("phone"));

//                TODO  courses;
            }

            set.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Закрываем соединения");
            closeStatement(statement);
            createConnection();
            return teacher;
        }
    }

    @Override
    public boolean delete(Integer id) {
        createConnection();
        Statement statement = createStatement();

        try {
            statement.executeUpdate(DELETE_BY_ID + id + ";");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            System.out.println("Закрываем соединения");
            closeStatement(statement);
            createConnection();
        }
    }

    @Override
    public boolean create(Teacher entity) {
        StringBuilder sql = new StringBuilder(INSERT_TEACHER);
        sql.append("'").append(entity.getName()).append("', ");
        sql.append("'").append(entity.getSurname()).append("', ");
        sql.append("'").append(entity.getPatronymic()).append("', ");
        sql.append("'").append(entity.getPhoneNumber()).append("');");

        createConnection();
        Statement statement = createStatement();

        System.out.println(sql);
        try {
            statement.executeUpdate(String.valueOf(sql));
            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        } finally {
            System.out.println("Закрываем соединения");
            closeStatement(statement);
            createConnection();
        }
    }

    public static void main(String[] args) {
        TeacherController controller = new TeacherController();
        List teachers = controller.getAll();


        System.out.println(teachers);

        Teacher teacher = new Teacher();
        teacher
                .setName("D")
                .setSurname("DD")
                .setPatronymic("DDD")
                .setPhoneNumber("+8999");

        boolean isCreated = controller.create(teacher);
        System.out.println(isCreated);

        teachers = controller.getAll();
        System.out.println(teachers);

        boolean isDeleted = controller.delete(1);
        System.out.println(isDeleted);

        teachers = controller.getAll();
        System.out.println(teachers);

        System.out.println("GET_BY_ID-------------------------------------------------------------------");
        System.out.println(controller.getEntityById(2));
    }
}