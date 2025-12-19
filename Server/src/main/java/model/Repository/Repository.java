package model.Repository;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repository {
    private Connection connection;

    public Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String password = "root";

        try {
            if (this.connection == null || this.connection.isClosed()) {
                Class.forName(driver);
                this.connection = DriverManager.getConnection(dbUrl, user, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.getLogger(Repository.class.getName())
                    .log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }

        return this.connection;
    }
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println( "An error occured while trying to close the statement");
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) { try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println( "An error occured while trying to close the statement");
            }
        }
    }
}
