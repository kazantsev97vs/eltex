package ru.eltex.app.reflexio;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTableTest {

    /*
    CREATE TABLE teachers (id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR (250) NOT NULL,
    surname VARCHAR (250) NOT NULL,
    patronymic VARCHAR (250) NOT NULL,
    phone VARCHAR (17),
    PRIMARY KEY (id)
    );
     */

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/univ?useUnicode=true&serverTimezone=UTC";

    public static void main(String[] args) {

        List<String> fieldsTypes = new ArrayList<>();
        List<String> fieldsNames = new ArrayList<>();

        fieldsTypes.add("int");
        fieldsNames.add("id");

        UserReflex userReflex = new UserReflex();
        userReflex
               .setName("Pasha")
               .setSurname("B")
               .setPatronymic("S")
               .setPhoneNumber("8999000990");

        Class userReflexClass = userReflex.getClass();
        System.out.println(userReflexClass);

        // имя класса - будущее имя таблицы
        String tableClassName = userReflexClass.getSimpleName();
        System.out.println(tableClassName);


        List<Field> fields = new ArrayList<>();

        System.out.println(fields);

        Class objectClass = new Object().getClass();

        while (!(userReflexClass.equals(objectClass))) {

            Collections.addAll(fields, userReflexClass.getDeclaredFields());
            System.out.println("");
            System.out.println("");
            System.out.println(fields);
            System.out.println("");
            System.out.println("");
            userReflexClass = userReflexClass.getSuperclass();

        }

        StringBuilder values = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            try {
                System.out.print(field.getName() + " ");
                System.out.println(field.get(userReflex));
                if (field.get(userReflex) != null) {
                    values.append("'").append(field.get(userReflex)).append("'");
                } else {
                    values.append(field.get(userReflex));
                }

                if (i < fields.size() - 1) {
                    values.append(", ");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        System.out.println(values);

        System.out.println("");
        System.out.println(fields);
        System.out.println("");

        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableClassName).append(" (\n");
        sql.append("\tid int(11) NOT NULL AUTO_INCREMENT,\n");


        for (Field f :
                fields) {

            String type = f.getType().toString();
            String[] types = type.split("\\.");
            String typeName = types[types.length - 1];
            String fieldName = f.getName();

            System.out.print(typeName);
            System.out.println(" " + fieldName);



            if (!fieldName.equals("id")) {
                sql.append("\t");
                sql.append(fieldName);

                fieldsTypes.add(typeName);
                fieldsNames.add(fieldName);

                switch (typeName) {

                    case "String":
                        sql.append(" VARCHAR(500),");
                        break;

                    case "Integer":
                        sql.append(" INTEGER(11),");
                        break;

                    case "Date":
                        sql.append(" Date,");
                        break;

                    default:
                        System.out.println("Problem in switch");
                        break;
                }
                sql.append("\n");
            }
        }
        sql.append("\tPRIMARY KEY (id)\n");
        sql.append(");");


        System.out.println("");
        System.out.println(sql);


        Connection connection = null;
        Statement statement = null;

        System.out.println(fieldsTypes);
        System.out.println(fieldsNames);

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();

            boolean isExecute = statement.execute(String.valueOf(sql));
            System.out.println(isExecute);

            ResultSet describe = statement.executeQuery("DESCRIBE " + tableClassName + ";");
            int fieldsTypesCounter = 0;
            int fieldsNamesCounter = 0;

            while (describe.next()) {
                System.out.print(describe.getString("Field") + " ");
                System.out.println(describe.getString("Type"));
            }

            StringBuilder valuesNames = new StringBuilder();
            StringBuilder values2 = new StringBuilder();

            for (int i = 0; i < fieldsNames.size() - 1; i++) {



                if (fieldsNames.get(i) != "id" && fields.get(i + 1).get(userReflex) != null) {
                    System.out.println("***" + fields.get(i + 1).get(userReflex));
                    valuesNames.append(fieldsNames.get(i + 1)).append(", ");

                    String[] arr = fields.get(i + 1).getType().toString().split("\\.");
                    if (arr[arr.length - 1].equals("String")) {
                        values2.append("'").append(fields.get(i + 1).get(userReflex)).append("', ");
                    } else {
                        values2.append(fields.get(i + 1).get(userReflex)).append(", ");
                    }
                }
            }

            if (fields.get(fieldsNames.size() - 1).get(userReflex) != null) {
                valuesNames.append(fieldsNames.get(fieldsNames.size() - 1));
                values2.append(fields.get(fieldsNames.size() - 1).get(userReflex));
            }

            String preparedValuesNames = valuesNames.substring(0, valuesNames.length() - 2);
            String preparedValues = values2.substring(0, values2.length() - 2);
            System.out.println(valuesNames);

            String insert = "INSERT INTO " + tableClassName + " (" +
                    preparedValuesNames + ") VALUES (" + preparedValues +");";

            System.out.println(insert);
            statement.executeUpdate(insert);

            ResultSet set = statement.executeQuery("SELECT * FROM " + tableClassName + ";");

            while (set.next()) {

                switch (fieldsTypes.get(fieldsTypesCounter)) {
                    case "String":
                        System.out.println(set.getString(fieldsNames.get(fieldsNamesCounter)));
                        break;

                    case "Date":
                        System.out.println(set.getDate(fieldsNames.get(fieldsNamesCounter)));
                        break;

                    case "Integer":
                        System.out.println(set.getInt(fieldsNames.get(fieldsNamesCounter)));
                        break;
                }

                fieldsTypesCounter++;
                fieldsNamesCounter++;
            }


            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
