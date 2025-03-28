package factory;

import dao.TransactionDao;
import impl.TransactionDaoImpl;

import java.sql.SQLException;

public class TransactionFactory {
    private static TransactionDao transactionDao = null;

    private TransactionFactory() {

    }

    public static TransactionDao getTransactionDao() throws SQLException {
        if(transactionDao == null) {
            transactionDao = new TransactionDaoImpl();
        }
        return transactionDao;
    }
}
