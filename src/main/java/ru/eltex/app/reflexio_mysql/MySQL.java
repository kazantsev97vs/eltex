package ru.eltex.app.reflexio_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DB = "univ";
    private static final String PARAMS = "?useUnicode=true&serverTimezone=UTC";

    private Connection connection;
    private Statement statement;

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

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public static String getDB() {
        return DB;
    }
}
