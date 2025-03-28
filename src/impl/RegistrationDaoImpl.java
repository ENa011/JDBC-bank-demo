package impl;

import dao.RegistrationDao;
import entity.Registration;
import factory.connectionFactory;
import java.sql.*;
import java.sql.SQLException;

public class RegistrationDaoImpl implements RegistrationDao {
    Connection connection;

    public RegistrationDaoImpl() throws SQLException {
        connection = connectionFactory.setConnection();
    }

    private int loginStatus(String userName, String password) throws SQLException{
        int status = 0;
        String sql = "select login from registration where userName = (?) and password = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            status = resultSet.getInt("login");
        }
        return status;
    }

    private String statusCheck(String username, String password) throws SQLException {
        String status = "pending";
        String sql = "select status from registration where username = (?) and password = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            status = resultSet.getString("status");
        }
        return status;
    }

    private boolean registrationCheck(String userName, String password) throws SQLException {
        String usernameSQL = "select username from registration where username = (?)";
        String passwordSQL = "select password from registration where password = (?)";
        PreparedStatement userNameStatement = connection.prepareStatement(usernameSQL);
        PreparedStatement passwordStatement = connection.prepareStatement(passwordSQL);

        userNameStatement.setString(1, userName);
        passwordStatement.setString(1, password);

        ResultSet usernameResult = userNameStatement.executeQuery();
        ResultSet passwordResult = passwordStatement.executeQuery();
        boolean status = false;
        if(!usernameResult.next() && !passwordResult.next()) {
            status = true;
        } else if(usernameResult.next()) {
            System.out.println("username taken, can't create account");
        } else if(passwordResult.next()) {
            System.out.println("sorry password is already taken, can't create account");
        } else System.out.println("unknown error");

        return status;
    }

    @Override
    public void registerAccount(Registration registration) throws SQLException {
        if(registrationCheck(registration.getUsername(),registration.getPassword())) {
            String insert = "insert into registration(username, password, name) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, registration.getUsername());
            preparedStatement.setString(2, registration.getPassword());
            preparedStatement.setString(3, registration.getName());
            preparedStatement.executeUpdate();
            System.out.println("Successfully created!");

        } else System.out.println("account didn't get created");

    }

    @Override
    public void loginToAccount(String userName, String password) throws SQLException {
            if(loginStatus(userName, password) == 1) {
                System.out.println("user already logged in");
            } else {
                String loginSQL = "update registration set login = (?) where username =(?) and password = (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(loginSQL);
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, userName);
                preparedStatement.setString(3, password);
                int result = preparedStatement.executeUpdate();
                if(result > 0) {
                    System.out.println("Login successful!");
                } else System.out.println("not a correct credential");
            }
    }

    @Override
    public void deleteAccount(Registration registration, int deleteId) throws SQLException {
        if(statusCheck(registration.getUsername(), registration.getPassword()).equals("admin")
                && loginStatus(registration.getUsername(), registration.getPassword()) == 1) {
            String deleteSQL = "delete from registration where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, deleteId);
            int result  = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("delete account successful");
            } else System.out.println("unknown error, account not deleted");
        } else if (!statusCheck(registration.getUsername(), registration.getPassword()).equals("admin")) {
            System.out.println("unauthorized access");
        } else if( loginStatus(registration.getUsername(), registration.getPassword()) == 0) {
            System.out.println("not logged in");
        } else System.out.println("unknown error, account not deleted()");

    }

    @Override
    public void StatusChange(Registration registration, int statusChangeId, String status) throws SQLException {
        if(statusCheck(registration.getUsername(), registration.getPassword()).equals("admin")
                && loginStatus(registration.getUsername(), registration.getPassword()) == 1) {
            String statusSQL = "update registration set status = (?) where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statusSQL);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, statusChangeId);
            int result  = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("status changed successful");
            } else System.out.println("unknown error, status not changed");
        } else if (!statusCheck(registration.getUsername(), registration.getPassword()).equals("admin")) {
            System.out.println("unauthorized access");
        } else if( loginStatus(registration.getUsername() , registration.getPassword()) == 0) {
            System.out.println("not logged in");
        } else System.out.println("unknown error, status not changed()");
    }

    @Override
    public void logout(Registration registration) throws SQLException {
        if(loginStatus(registration.getUsername(), registration.getPassword()) == 0) {
            System.out.println("already logged out");
        } else if(loginStatus(registration.getUsername(), registration.getPassword()) == 1) {
            String logoutSQL = "update registration set login = (?) where username = (?) and password = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(logoutSQL);
            preparedStatement.setInt(1,0);
            preparedStatement.setString(2, registration.getUsername());
            preparedStatement.setString(3,registration.getPassword());
            int result = preparedStatement.executeUpdate();
            System.out.println("Successfully logged out, Thank you");
        } else System.out.println("unknown error");
    }
}
