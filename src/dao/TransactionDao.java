package dao;

import entity.Transaction;
import entity.ClientAccount;
import entity.Registration;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    public List<Transaction> sentHistory(String userName, String password, int accountId) throws SQLException;
    public List<Transaction> receivedHistory(String userName, String password, int accountId) throws SQLException;
    public void transaction(String userName, String password,
                            int senderAccountId, int receiverAccountId, double balance) throws SQLException;

}
