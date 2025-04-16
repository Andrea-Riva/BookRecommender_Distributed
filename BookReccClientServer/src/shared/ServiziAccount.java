package shared;

import java.sql.Connection;
import java.sql.SQLException;

public interface ServiziAccount {

    public boolean inserimentoAccountDB(String nome, String cognome,
                                        String codiceFiscale, String mail,
                                        String password) throws SQLException;

    public boolean login(String mail, String password) throws SQLException;

    public boolean isCfUsed(String cf) throws SQLException;

    boolean isMailUsed(String mail) throws SQLException;
}
