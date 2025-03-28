package impl;
import dao.TransactionDao;
import entity.Transaction;
import factory.connectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    Connection connection;

    public TransactionDaoImpl() throws SQLException{
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

    @Override
    public List<Transaction> sentHistory(String userName, String password, int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, accountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                accountStatusCheck(userName, password, accountId).equals("approved")) {
            String senderHistory = "select* from transaction where senderId = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(senderHistory);
            preparedStatement.setInt(1, accountId);
            ResultSet senderSet = preparedStatement.executeQuery();
            while(senderSet.next()) {
                transactions.add(new Transaction(senderSet.getDouble("amount"),
                        senderSet.getString("receiverName"),
                        senderSet.getString("senderName"),
                        senderSet.getInt("id"),
                        senderSet.getInt("senderId"),
                        senderSet.getInt("receiverId")));
            }

        } else System.out.println("account ID not correct");
        return transactions;
    }

    @Override
    public List<Transaction> receivedHistory(String userName, String password, int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, accountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                accountStatusCheck(userName, password, accountId).equals("approved")) {
            String receiverHistory = "select* from transaction where receiverId = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(receiverHistory);
            preparedStatement.setInt(1, accountId);
            ResultSet receiverSet = preparedStatement.executeQuery();
            while(receiverSet.next()) {
                transactions.add(new Transaction(receiverSet.getDouble("amount"),
                        receiverSet.getString("receiverName"),
                        receiverSet.getString("senderName"),
                        receiverSet.getInt("id"),
                        receiverSet.getInt("senderId"),
                        receiverSet.getInt("receiverId")));
            }

        } else System.out.println("account ID not correct");
        return transactions;
    }

    @Override
    public void transaction(String userName, String password, int senderAccountId, int receiverAccountId, double amount) throws SQLException {
        if(loginStatus(userName, password) == 0) {
            System.out.println("user not logged in");
        } else if(accountStatusCheck(userName, password, senderAccountId).equals("pending")){
            System.out.println("account not approved");
        }else if(loginStatus(userName, password) == 1 &&
                accountStatusCheck(userName, password, senderAccountId).equals("approved")) {
            double balance = -1;
            String balanceSQL = "select balance from account where id = (?)";
            PreparedStatement balanceStatement = connection.prepareStatement(balanceSQL);
            balanceStatement.setInt(1, senderAccountId);
            ResultSet balanceSet = balanceStatement.executeQuery();
            while(balanceSet.next()) {
                balance = balanceSet.getDouble("balance");
            }
            String senderSQL = "update account set balance = balance - (?) where id = (?)";
            String receiverSQL = "update account set balance = balance + (?) where id = (?)";
            if(balance >= amount) {
                PreparedStatement senderStatement = connection.prepareStatement(senderSQL);
                senderStatement.setDouble(1, amount);
                senderStatement.setInt(2, senderAccountId);
                int senderResult = senderStatement.executeUpdate();

                PreparedStatement receiverStatement = connection.prepareStatement(receiverSQL);
                receiverStatement.setDouble(1, amount);
                receiverStatement.setInt(2, receiverAccountId);
                int receiverResult = receiverStatement.executeUpdate();

                String senderName = "";
                String receiverName = "";
                String NameSQL = "select ownerName from account where id = (?)";
                PreparedStatement senderNameStatement = connection.prepareStatement(NameSQL);
                senderNameStatement.setInt(1, senderAccountId);
                ResultSet senderNameSet = senderNameStatement.executeQuery();
                while(senderNameSet.next()) {
                    senderName = senderNameSet.getString("ownerName");
                }

                PreparedStatement receiverNameStatement = connection.prepareStatement(NameSQL);
                receiverNameStatement.setInt(1, receiverAccountId);
                ResultSet receiverNameSet = receiverNameStatement.executeQuery();
                while(receiverNameSet.next()) {
                    receiverName = receiverNameSet.getString("ownerName");
                }

                String transactionSQL = "insert into transaction(senderId, senderName, receiverId, receiverName, amount) " +
                        "values(?, ?, ?, ?, ?)";
                PreparedStatement transactionStatement = connection.prepareStatement(transactionSQL);
                transactionStatement.setInt(1,senderAccountId);
                transactionStatement.setString(2, senderName);
                transactionStatement.setInt(3, receiverAccountId);
                transactionStatement.setString(4, receiverName);
                transactionStatement.setDouble(5, amount);
                int transactionResult = transactionStatement.executeUpdate();

                if(senderResult >= 0 && receiverResult >=0 && transactionResult >=0) {
                    System.out.println("transaction successful");
                } else System.out.println("transaction not successful");

            } else System.out.println("sender balance not enough");

        } else System.out.println("Not a correct sender bank account ID");
    }
}
