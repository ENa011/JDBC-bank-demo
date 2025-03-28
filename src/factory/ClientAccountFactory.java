package factory;

import dao.ClientAccountDao;
import impl.ClientAccountDaoImpl;

import java.sql.SQLException;

public class ClientAccountFactory {

    private static ClientAccountDao clientAccountDao = null;
    private ClientAccountFactory() {

    }

    public static ClientAccountDao getClientAccountDao() throws SQLException {
        if(clientAccountDao == null) {
            clientAccountDao = new ClientAccountDaoImpl();
        }
        return clientAccountDao;
    }


}
