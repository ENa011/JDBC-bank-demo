package impl;

import dao.ClientAccountDao;
import entity.ClientAccount;
import entity.Registration;
import factory.connectionFactory;

import java.sql.*;

public class ClientAccountDaoImpl implements ClientAccountDao {

    Connection connection;

    public ClientAccountDaoImpl() throws SQLException {
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

    private String accountStatusCheck(String username, String password, int accountId) throws SQLException {
        String status = "NA";
        String sql = "select a.accountStatus from account a join registration r on a.ownerId = r.id " +
                "where r.username = (?) and r.password = (?) and a.id = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setInt(3, accountId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            status = resultSet.getString("accountStatus");
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

    @Override
    public void createAccount(String userName, String password, double balance) throws SQLException {
        if(loginStatus(userName , password)== 0) {
            System.out.println("user not logged in");
        } else if(!statusCheck(userName, password).equals("client")){
            System.out.println("must be a approved client ID to create account");
        }else if(loginStatus(userName, password) == 1 &&
                    statusCheck(userName, password).equals("client")) {
            String getSQL = "select* from registration where username =(?) and password=(?)";
            PreparedStatement resultStatement = connection.prepareStatement(getSQL);
            resultStatement.setString(1, userName);
            resultStatement.setString(2, password);
            ResultSet resultSet = resultStatement.executeQuery();
            Registration registration = new Registration();
            while(resultSet.next()) {
                 registration = new Registration(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("username"),resultSet.getString("password"),
                resultSet.getInt("login"), resultSet.getString("status"));
            }
            String accountSQL = "insert into account(balance, ownerId, ownerName) values(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(accountSQL);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, registration.getId());
            preparedStatement.setString(3, registration.getName());
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("account created");
            } else System.out.println("unknown error, account not created");
        } else System.out.println("unknown error");
    }

    @Override
    public double seeBalance(String userName, String password, int accountId) throws SQLException {
        double balance = -1;
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, accountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                    accountStatusCheck(userName, password, accountId).equals("approved")) {
            String balanceSQL = "select balance from account where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(balanceSQL);
            preparedStatement.setInt(1, accountId);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()) {
                balance = resultset.getDouble("balance");
            }
        }else System.out.println("Not a correct bank account ID");
        return balance;
    }

    @Override
    public void depositMoney(String userName, String password, int accountId, double amount) throws SQLException {
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, accountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                accountStatusCheck(userName, password, accountId).equals("approved")) {
            String depositSQL = "update account set balance = balance + (?) where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(depositSQL);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountId);
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("Money deposited successfully");
            } else System.out.println("unknown error, money not deposited");
        } else System.out.println("Not a correct bank account ID");
    }

    @Override
    public void withdrawMoney(String userName, String password, int accountId, double amount) throws SQLException {
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, accountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                accountStatusCheck(userName, password, accountId).equals("approved")) {
            double balance = -1;
            String balanceSQL = "select balance from account where id = (?)";
            PreparedStatement balanceStatement = connection.prepareStatement(balanceSQL);
            balanceStatement.setInt(1, accountId);
            ResultSet balanceSet = balanceStatement.executeQuery();
            while(balanceSet.next()) {
                balance = balanceSet.getDouble("balance");
            }
            if(balance >=amount) {
                String withdrawSQL = "update account set balance = balance - (?) where id =(?)";
                PreparedStatement preparedStatement = connection.prepareStatement(withdrawSQL);
                preparedStatement.setDouble(1,amount);
                preparedStatement.setInt(2, accountId);
                int result = preparedStatement.executeUpdate();
                if(result > 0) {
                    System.out.println("Money withdrawn successfully");
                } else System.out.println("unknown error, money not withdrawn");
            } else System.out.println("balance not enough");
        } else System.out.println("Not a correct bank account ID");
    }

    @Override
    public double seeBalanceAdmin(String userName, String password, int accountId) throws SQLException {
        double balance = -1;
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(!statusCheck(userName, password).equals("admin")){
            System.out.println("unauthorized access");
        }else if(loginStatus(userName, password) == 1 &&
                statusCheck(userName, password).equals("admin")) {
            String balanceSQL = "select balance from account where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(balanceSQL);
            preparedStatement.setInt(1, accountId);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()) {
                balance = resultset.getDouble("balance");
            }
        } else System.out.println("unknown error");
        return balance;
    }

    @Override
    public void changeAccountStatus(String userName, String password, int accountId, String status) throws SQLException  {
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(!statusCheck(userName, password).equals("admin")){
            System.out.println("unauthorized access");
        }else if(loginStatus(userName, password) == 1 &&
                statusCheck(userName, password).equals("admin")) {
            String statusSQL = "update account set accountStatus = (?) where id =(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statusSQL);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, accountId);
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("account status updated");
            } else System.out.println("unknown error, account status not updated");
        } else System.out.println("unknown error");
    }

    @Override
    public void deleteAccount(String userName, String password, int accountId) throws SQLException {
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(!statusCheck(userName, password).equals("admin")){
            System.out.println("unauthorized access");
        }else if(loginStatus(userName, password) == 1 &&
                statusCheck(userName, password).equals("admin")) {
            String deleteSQL = "delete from account where id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1,accountId);
            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                System.out.println("account deleted successfully");
            } else System.out.println("unknown error, account is not deleted");

        }else System.out.println("unknown error");
    }
}
