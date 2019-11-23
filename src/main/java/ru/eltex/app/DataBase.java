package ru.eltex.app;
import java.sql.*;

public class DataBase {

    private Connection connection;
    private Statement statement;

    private static final String USER = "root";
    private static final String PASSWORD = "pyfrjvmcz!7$";
    private static final String URL = "jdbc:mysql://localhost/univ?useUnicode=true&serverTimezone=UTC";

    void connect () {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("CONNECTION ERROR");
            e.printStackTrace();
        }

        createStatement();
    }

    void createStatement () {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("STATEMENT ERROR");
            e.printStackTrace();
        }
    }

    void close () {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("CONNECTION CLOSE - ERROR");
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }

    void insertValue (String tableName, String[] keys, String[] values) {
        StringBuilder keysLine = new StringBuilder(keys[0]);
        StringBuilder valuesLine = new StringBuilder(values[0]);

        if (keys.length == values.length) {
            for (int i = 0; i < keys.length; i++) {
                keysLine.append(", ").append(keys[i]);
                valuesLine.append(", ").append(values[i]);
            }
        } else {
            System.out.println("Number of keys NOT EQUAL number of values");
        }

        try {
            int statementN = statement.executeUpdate("INSERT INTO " + tableName +
                    "(" + keysLine + ")" +
                    "VALUE" + "" +
                    "(" + valuesLine + ");");

            System.out.println(statementN);

        } catch (SQLException e) {
            System.out.println("EXECUTE_UPDATE - INSERT - ERROR");
            e.printStackTrace();
        }
    }

    String[] select(String tableName, String[] keys, String where) {
        ResultSet result;
        StringBuilder keysLine = new StringBuilder(keys[0]);

        for (int i = 1; i < keys.length; i++) {
            keysLine.append(", ").append(keys[i]);
        }


        try {
            String sql = "SELECT " + keysLine + " FROM " + tableName +
                    ((where == null) ? ";" : " WHERE " + where + ";");

            System.out.println(sql);

            result = statement.executeQuery(sql);

            StringBuilder line = new StringBuilder();

            while (result.next()) {
                line.append(result.getString(keys[0]));
                for (int i = 1; i < keys.length; i++) {
                    line.append(" ").append(result.getString(keys[i]));
                }
                line.append(",\n");
            }

            System.out.println(line);



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM students;");
            String line = "";
            while (res.next()) {
                line += res.getString("id") + " ";
                line += res.getString("name") + " ";
                line += res.getString("surname") + " ";
            }

            System.out.println(line);

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        DataBase db  = new DataBase();
        db.connect();
        db.select("students", new String[]{"id", "name", "surname"}, null);




    }
}
