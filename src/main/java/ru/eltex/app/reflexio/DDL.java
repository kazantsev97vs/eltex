package ru.eltex.app.reflexio;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CREATE TABLE
 * DROP TABLE
 * TRUNCATE TABLE
 * ALTER TABLE
 */
public class DDL {

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DB = "univ";
    private static final String PARAMS = "?useUnicode=true&serverTimezone=UTC";
    private Connection connection;
    private Statement statement;

    // Получаемый объект
    private Object object;
    // Класс получаемого объекта
    private Class objectClass;
    // Название класса объекта
    private String objectClassName;
    // Поля объекта
    private List<Field> fields = new ArrayList<>();
    // Родители объекта
    private List <Class> parentsClasses = new ArrayList<>();

    public DDL(Object object) {
        this.object = object;
        this.objectClass = this.object.getClass();
        this.objectClassName = objectClass.getSimpleName();
        this.setFields();

        System.out.print("Object:");
        System.out.println(object);
        System.out.println("\nobjectClass:");
        System.out.println(objectClass);
        System.out.println("");
    }

    boolean connectOpen () {
        try {
            connection = DriverManager.getConnection(URL + DB + PARAMS, USER, PASSWORD);
            statement = connection.createStatement();
            return true;
        } catch (SQLException e) {
            System.out.println("CONNECTION ERROR");
            e.printStackTrace();
            return false;
        }
    }

    void connectClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR CLOSE CONNECTION");
            e.printStackTrace();
        }
    }

    private void setFields() {
        // Поля текущего класса
        fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        Class parentClass = objectClass;

        while (
                (parentClass = parentClass.getSuperclass()) != null // пока есть родитель
                        && parentClass != Object.class // и этот родитель не Object
        ) {
            // Запоминаем класс родителя
            parentsClasses.add(parentClass);
            // Записываем все поля
            fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
        }
    }

    boolean createTable() throws SQLException {
        if (statement == null) return false;
        if (isTableExists()) return false;

        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE ");
        boolean isId = false;

        createTableSQL.append(objectClassName).append(" (");

        for (int fieldCounter = 0; fieldCounter < fields.size(); fieldCounter++) {
            Field field = fields.get(fieldCounter);
            String[] types = field.getType().toString().split("\\.");
            String type = types[types.length - 1];

            createTableSQL.append("\n\t").append(field.getName());

            switch (type) {
                case "String":
                    createTableSQL.append(" VARCHAR(500)");
                    break;

                case "Integer":
                    createTableSQL.append(" INTEGER(11)");
                    break;

                case "Date":
                    createTableSQL.append(" Date");
                    break;

                default:
                    System.out.println("Problem in switch with 'createTableSQL'");
                    break;
            }

            if (field.getName().equals("id")) {
                isId = true;
                createTableSQL.append(" NOT NULL AUTO_INCREMENT");
            }

            createTableSQL.append(",");
        }

        if (isId) createTableSQL.append("\n\tPRIMARY KEY (id)\n");
        createTableSQL.append(");");

        System.out.println(createTableSQL);

        statement.execute(String.valueOf(createTableSQL));
        return true;
    }

    boolean dropTable () throws SQLException {
        if (statement == null) return false;
        if (!isTableExists()) return false;

        String dropTableQuery = "DROP TABLE " + objectClassName + ";";
        System.out.println(dropTableQuery);
        statement.execute(dropTableQuery);

        return true;
    }

    boolean isTableExists() {
        if (statement == null) return false;
        boolean isTableExists = false;

        try {
            String isTableExistsQuery = "SHOW TABLES FROM " + DB + " LIKE '" + objectClassName + "';";
            ResultSet table = statement.executeQuery(isTableExistsQuery);

            if (table.next()) {
                isTableExists = table.getString("Tables_in_" + DB + " (" + objectClassName + ")")
                        .equals(objectClassName.toLowerCase());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return isTableExists;
    }

    public static void main(String[] args) {
        UserReflex userReflex = new UserReflex();
        userReflex
                .setName("Anya")
                .setSurname("R")
                .setPatronymic("S")
                .setPhoneNumber("8913********");

        DDL ddl = new DDL(userReflex);
        System.out.println("CONNECTION IS " + ddl.connectOpen());
        try {
            System.out.println("TABLE WAS CREATED " + ddl.createTable());
            System.out.println("TABLE WAS DROPPED " + ddl.dropTable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ddl.connectClose();
    }
}