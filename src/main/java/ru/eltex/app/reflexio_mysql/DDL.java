package ru.eltex.app.reflexio_mysql;

import ru.eltex.app.reflexio.UserReflex;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

/**
 * CREATE TABLE
 * DROP TABLE
 * TRUNCATE TABLE
 * ALTER TABLE
 */
public class DDL {
    Statement statement;

    public DDL(Statement statement) {
        this.statement = statement;
    }

    boolean createTable(String objectClassName, List<Field> fields) throws SQLException {
        if (statement == null) return false;

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

        System.out.println("mysql> " + createTableSQL);

        statement.execute(String.valueOf(createTableSQL));
        return true;
    }

    boolean dropTable (String objectClassName) throws SQLException {
        if (statement == null) return false;

        String dropTableQuery = "DROP TABLE " + objectClassName + ";";
        System.out.println("mysql> " + dropTableQuery);
        statement.execute(dropTableQuery);

        return true;
    }

    boolean isTableExists(final String DB, String objectClassName) {
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

        Reflexio reflexedUserReflex = new Reflexio(userReflex);

        MySQL mySqlConnection = new MySQL();
        System.out.println("CONNECTION IS " + mySqlConnection.connectOpen());

        DDL ddl = new DDL(mySqlConnection.getStatement());

        try {

            boolean isTableExists = ddl.isTableExists(MySQL.getDB(), reflexedUserReflex.getObjectClassName());
            System.out.println("TABLE EXISTS = " + isTableExists);

            boolean isTableCreated = false;

            if (!isTableExists) {
                isTableCreated = ddl.createTable(
                        reflexedUserReflex.getObjectClassName(),
                        reflexedUserReflex.getFields());
            }

            System.out.println("TABLE WAS CREATED = " + isTableCreated);

            isTableExists = ddl.isTableExists(MySQL.getDB(), reflexedUserReflex.getObjectClassName());
            System.out.println("TABLE EXISTS = " + isTableExists);

            boolean isTableDropped = false;

            if (isTableExists) {
                isTableDropped = ddl.dropTable(reflexedUserReflex.getObjectClassName());
            }

            System.out.println("TABLE WAS DROPPED = " + isTableDropped);

            isTableExists = ddl.isTableExists(MySQL.getDB(), reflexedUserReflex.getObjectClassName());
            System.out.println("TABLE EXISTS = " + isTableExists);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        mySqlConnection.connectClose();
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}