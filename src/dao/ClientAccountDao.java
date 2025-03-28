package dao;

import entity.ClientAccount;
import entity.Registration;

import java.sql.SQLException;

public interface ClientAccountDao {
    public void createAccount(String userName, String password, double balance) throws SQLException;
    public double seeBalance(String userName, String password, int accountId) throws SQLException;
    public void depositMoney(String userName, String password, int accountId, double amount) throws SQLException;
    public void withdrawMoney(String userName, String password, int accountId, double amount) throws SQLException;
    public double seeBalanceAdmin(String userName, String password, int accountId) throws SQLException ;
    public void changeAccountStatus(String userName, String password, int accountId, String status) throws SQLException;
    public void deleteAccount(String userName, String password, int accountId) throws SQLException;
}
