import dao.ClientAccountDao;
import dao.RegistrationDao;
import dao.TransactionDao;
import entity.ClientAccount;
import entity.Registration;
import factory.ClientAccountFactory;
import factory.RegistrationFactory;
import factory.TransactionFactory;
import factory.connectionFactory;
import impl.ClientAccountDaoImpl;
import impl.RegistrationDaoImpl;
import impl.TransactionDaoImpl;

import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        boolean status = true;

        RegistrationDao registrationDao = RegistrationFactory.getRegistrationDao();
        ClientAccountDao clientAccountDao = ClientAccountFactory.getClientAccountDao();
        TransactionDao transactionDao = TransactionFactory.getTransactionDao();

        while (status) {
            System.out.println("******************************************************");
            System.out.println("Welcome to the Bank, please select from a options below");
            System.out.println("*******************************************************");
            System.out.println("1.register account");
            System.out.println("2.log in to account");
            System.out.println("3.Bank customer options");
            System.out.println("4.Bank Administration options");
            System.out.println("5.log out");
            System.out.println("6.Exit program");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    System.out.println("registering account");
                    System.out.print("you name?: ");
                    String registerName = scanner.next();
                    System.out.print("type desire user name: ");
                    String registerUsername = scanner.next();
                    System.out.print("type your password: ");
                    String registerPassword = scanner.next();
                    Registration registration = new Registration();
                    registration.setName(registerName);
                    registration.setPassword(registerPassword);
                    registration.setUsername(registerUsername);
                    registrationDao.registerAccount(registration);
                    input = 0;
                    break;
                case 2:
                    System.out.println("login to account");
                    System.out.print("user name: ");
                    String loginUsername = scanner.next();
                    System.out.print("password: ");
                    String loginPassword = scanner.next();
                    registrationDao.loginToAccount(loginUsername, loginPassword);
                    input = 0;
                    break;
                case 3:
                    while (input == 3) {
                        System.out.println("**********************************************");
                        System.out.println("Welcome to the customer service, please enter on options from below");
                        System.out.println("**********************************************");
                        System.out.println("1.create a bank account");
                        System.out.println("2.see a balance on a account");
                        System.out.println("3.deposit money on a account");
                        System.out.println("4.withdraw money on a account");
                        System.out.println("5.transfer money to a different account");
                        System.out.println("6.see a history of money transferred out ");
                        System.out.println("7.see a history of money transferred in");
                        System.out.println("8.back to menu");
                        int customerInput = scanner.nextInt();

                        switch (customerInput) {
                            case 1:
                                System.out.println("creating bank account");
                                System.out.print("user name: ");
                                String accountUsername = scanner.next();
                                System.out.print("password: ");
                                String accountPassword = scanner.next();
                                System.out.print("balance deposit to create account: ");
                                double accountBalance = scanner.nextDouble();
                                clientAccountDao.createAccount(accountUsername, accountPassword, accountBalance);
                                break;
                            case 2:
                                System.out.println("checking balance");
                                System.out.print("user name: ");
                                String seeBalanceUsername = scanner.next();
                                System.out.print("password: ");
                                String seeBalancePassword = scanner.next();
                                System.out.print("account ID: ");
                                int seeBalanceAccountId = scanner.nextInt();
                                System.out.println(clientAccountDao.seeBalance(seeBalanceUsername, seeBalancePassword, seeBalanceAccountId));
                                break;
                            case 3:
                                System.out.println("deposit money");
                                System.out.print("user name: ");
                                String depositUsername = scanner.next();
                                System.out.print("password: ");
                                String depositPassword = scanner.next();
                                System.out.print("account ID: ");
                                int depositAccountId = scanner.nextInt();
                                System.out.print("amount you want deposit: ");
                                double depositBalance = scanner.nextDouble();
                                clientAccountDao.depositMoney(depositUsername, depositPassword,
                                        depositAccountId, depositBalance);
                                break;
                            case 4:
                                System.out.println("withdraw money");
                                System.out.print("user name: ");
                                String withdrawUsername = scanner.next();
                                System.out.print("password: ");
                                String withdrawPassword = scanner.next();
                                System.out.print("account ID: ");
                                int withdrawAccountId = scanner.nextInt();
                                System.out.print("amount you want to withdraw ");
                                double withdrawBalance = scanner.nextDouble();
                                clientAccountDao.withdrawMoney(withdrawUsername, withdrawPassword,
                                        withdrawAccountId, withdrawBalance);
                                break;
                            case 5:
                                System.out.println("transferring money");
                                System.out.print("user name: ");
                                String transferUsername = scanner.next();
                                System.out.print("password: ");
                                String transferPassword = scanner.next();
                                System.out.print("sender account ID: ");
                                int senderAccountId = scanner.nextInt();
                                System.out.print("receiver account ID: ");
                                int receiverAccountId = scanner.nextInt();
                                System.out.print("amount you want to transfer: ");
                                double moneyTransfer = scanner.nextDouble();
                                transactionDao.transaction(transferUsername, transferPassword,
                                        senderAccountId, receiverAccountId, moneyTransfer);
                                break;
                            case 6:
                                System.out.println("checking history of money transferred out");
                                System.out.print("user name: ");
                                String sHistoryUsername = scanner.next();
                                System.out.print("password: ");
                                String sHistoryPassword = scanner.next();
                                System.out.print("account ID of money sent: ");
                                int senderHistoryId = scanner.nextInt();
                                System.out.println(transactionDao.sentHistory(sHistoryUsername, sHistoryPassword, senderHistoryId));
                                break;
                            case 7:
                                System.out.println("checking history of money transferred in");
                                System.out.print("user name: ");
                                String rHistoryUsername = scanner.next();
                                System.out.print("password: ");
                                String rHistoryPassword = scanner.next();
                                System.out.print("account ID of money received: ");
                                int receiverHistoryId = scanner.nextInt();
                                System.out.println(transactionDao.receivedHistory(rHistoryUsername, rHistoryPassword, receiverHistoryId));
                                break;
                            case 8:
                                input = 0;
                                break;
                            default:
                                System.out.println("not a correct option choose between 1 -8");
                        }
                    }
                    break;

                case 4:
                    while (input == 4) {
                        System.out.println("**********************************************");
                        System.out.println("Welcome to the administrator service, please enter on options from below");
                        System.out.println("**********************************************");
                        System.out.println("1.change status of registered account");
                        System.out.println("2.delete registered account");
                        System.out.println("3.change status of bank account");
                        System.out.println("4.see a balance of bank account");
                        System.out.println("5.delete a bank account");
                        System.out.println("6.back to menu");
                        int adminInput = scanner.nextInt();

                        switch (adminInput) {
                            case 1:
                                System.out.println("changing status of registered account");
                                System.out.print("user name: ");
                                String registerStatusUsername = scanner.next();
                                System.out.print("password: ");
                                String registerStatusPassword = scanner.next();
                                System.out.print("register account ID you want to change status: ");
                                int registerStatusId = scanner.nextInt();
                                System.out.print("status you want to change account to(PENDING, CLIENT, ADMIN): ");
                                String registerStatus = scanner.next();
                                Registration statusRegistration = new Registration();
                                statusRegistration.setUsername(registerStatusUsername);
                                statusRegistration.setPassword(registerStatusPassword);
                                registrationDao.StatusChange(statusRegistration, registerStatusId, registerStatus);
                                break;
                            case 2:
                                System.out.println("deleting registered account");
                                System.out.print("user name: ");
                                String deleteUsername = scanner.next();
                                System.out.print("password: ");
                                String deletePassword = scanner.next();
                                System.out.print("deleting registered account ID: ");
                                int deleteId = scanner.nextInt();
                                Registration deleteRegistration = new Registration();
                                deleteRegistration.setUsername(deleteUsername);
                                deleteRegistration.setPassword(deletePassword);
                                registrationDao.deleteAccount(deleteRegistration, deleteId);
                                break;
                            case 3:
                                System.out.println("changing bank account status");
                                System.out.print("user name: ");
                                String accountStatusUsername = scanner.next();
                                System.out.print("password: ");
                                String accountStatusPassword = scanner.next();
                                System.out.print("account ID you wish to change status: ");
                                int accountStatusId = scanner.nextInt();
                                System.out.print("status you want to change bank account to(PENDING, APPROVED): ");
                                String accountStatus = scanner.next();
                                clientAccountDao.changeAccountStatus(accountStatusUsername, accountStatusPassword,
                                        accountStatusId, accountStatus);
                                break;
                            case 4:
                                System.out.println("seeing a balance in a account");
                                System.out.print("user name: ");
                                String seeAdminUsername = scanner.next();
                                System.out.print("password: ");
                                String seeAdminPassword = scanner.next();
                                System.out.print("account ID you want to see balance: ");
                                int seeAdminID = scanner.nextInt();
                                System.out.println(clientAccountDao.seeBalanceAdmin(seeAdminUsername, seeAdminPassword, seeAdminID));
                                break;
                            case 5:
                                System.out.println("deleting bank account");
                                System.out.print("user name: ");
                                String deleteAdminUsername = scanner.next();
                                System.out.print("password: ");
                                String deleteAdminPassword = scanner.next();
                                System.out.print("bank account ID you want to delete: ");
                                int deleteAdminID = scanner.nextInt();
                                clientAccountDao.deleteAccount(deleteAdminUsername, deleteAdminPassword, deleteAdminID);
                                break;
                            case 6:
                                input = 0;
                                break;
                            default:
                                System.out.println("not a correct option, please pick between 1 - 6");
                        }
                    }
                    break;
                case 5:
                    System.out.println("logging out");
                    System.out.print("user name: ");
                    String logoutUsername = scanner.next();
                    System.out.print("password: ");
                    String logoutPassword = scanner.next();
                    Registration logout = new Registration();
                    logout.setUsername(logoutUsername);
                    logout.setPassword(logoutPassword);
                    registrationDao.logout(logout);
                    input = 0;
                    break;
                case 6:
                    input = 0;
                    status = false;
                    connectionFactory.closeConnection();
                    System.out.println("................Exiting program....................");
                    break;
                default:
                    System.out.println("not a correct option, please pick between 1 - 6");
            }
        }



    }
}