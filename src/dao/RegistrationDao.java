package dao;

import entity.Registration;

import java.sql.SQLException;

public interface RegistrationDao {
   public void registerAccount(Registration client) throws SQLException;
   public void loginToAccount(String userName,String password) throws SQLException;
   public void deleteAccount(Registration registration, int deleteId) throws SQLException;
   public void StatusChange(Registration registration, int statusChangeId, String status) throws SQLException;
   public void logout(Registration registration) throws SQLException;

}
