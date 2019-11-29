package ru.eltex.app.controllers.db;

import java.sql.*;

public class Transactions {

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/univ?useUnicode=true&serverTimezone=UTC";


    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO students (name, surname, patronymic, phone) " +
                    "VALUES " +
                    "('name1', 'surname1','patronymic1','+7890');";

            String sql2 = "INSERT INTO students (name, surname, patronymic, phone) " +
                    "VALUES " +
                    "('D2', 'D2','D2','+799945153..');";

            long startTime = System.currentTimeMillis();

            for (int i = 1; i <= 100000; i++) {
                statement.executeUpdate(sql);
            }

            long endTime = System.currentTimeMillis();
            long executeTimeAutoCommit = endTime - startTime;
            long startTime2 = System.currentTimeMillis();

            connection.setAutoCommit(false);

            for (int i = 1; i <= 100000; i++) {
                statement.executeUpdate(sql2);
            }

            connection.commit();

            long endTime2 = System.currentTimeMillis();
            long executeTimeAutoCommitFalse = endTime2 - startTime2;
            long diff = executeTimeAutoCommit - executeTimeAutoCommitFalse;

            System.out.println("Цикл с автокоммитом:");
            System.out.println("Начало выполнения цикла" + startTime);
            System.out.println("Конец выполнения цикла" + endTime);
            System.out.println("Время выполнения цикла" + executeTimeAutoCommit);

            System.out.println("Цикл без автокоммита:");
            System.out.println("Начало выполнения цикла" + startTime2);
            System.out.println("Конец выполнения цикла" + endTime2);
            System.out.println("Время выполнения цикла" + executeTimeAutoCommitFalse);
            System.out.println("Difference: " + diff);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
