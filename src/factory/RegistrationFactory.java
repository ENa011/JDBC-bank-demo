package factory;

import dao.RegistrationDao;
import impl.RegistrationDaoImpl;

import java.sql.SQLException;

public class RegistrationFactory {

    private static RegistrationDao registrationDao = null;

    private RegistrationFactory() {

    }

    public static RegistrationDao getRegistrationDao() throws SQLException {
        if(registrationDao == null) {
            registrationDao = new RegistrationDaoImpl();
        }
        return registrationDao;
    }
}
