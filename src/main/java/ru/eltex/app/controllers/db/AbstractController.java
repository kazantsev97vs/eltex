package ru.eltex.app.controllers.db;

import java.sql.*;
import java.util.List;

public abstract class AbstractController <E, K> {

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/univ?useUnicode=true&serverTimezone=UTC";

    private Connection connection;
    private Statement statement;

    public void createConnection () {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("CREATE-CONNECTION ERROR");
            e.printStackTrace();
        }
    }

    public void closeConnection () {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("CLOSE-CONNECTION ERROR");
            e.printStackTrace();
        }
    }

    public Statement createStatement () {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("CREATE-STATEMENT ERROR");
            e.printStackTrace();
        }

        return statement;
    }

    public void closeStatement (Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("CLOSE-STATEMENT ERROR");
                e.printStackTrace();
            }
        }
    }


    public abstract List<E> getAll();
    public abstract E update(E entity);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);

}