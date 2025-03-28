package factory;

import java.sql.*;
import java.util.ResourceBundle;

public class connectionFactory {

    private static Connection connection = null;

    private connectionFactory() {

    }

    public static Connection setConnection() throws SQLException {

        if (connection == null) {
            ResourceBundle resourcebundle = ResourceBundle.getBundle("config");
            String url = resourcebundle.getString("url");
            String username = resourcebundle.getString("username");
            String password = resourcebundle.getString("password");

            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("connection closed");
    }

}
