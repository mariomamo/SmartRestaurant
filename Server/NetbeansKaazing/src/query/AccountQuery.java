package query;

import entity.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Si occupa del recupero delle informazioni relative agli account
 * @author Syrenne
 */
public class AccountQuery implements AccountQueryInterface {
    private Connection connection;

    /**
     * Effettua il recupero delle informazioni dell'account dal database, se esistono username e password
     * @param account contiene username e password per recuperare le informazioni dal database
     * @return newAccount, null se le credenziali non sono presenti nel database, altrimenti è inizializzato con le informazioni dell'account del database
     */
    @Override
    public Account login(Account account) {
        PreparedStatement statement = null;
        Account newAccount = null;
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT a.username, a.password, a.nome, a.tipo FROM Account as a WHERE a.username = ? AND aes_decrypt(a.password, \"" + DbManager.getKey() + "\") = ?";
            
            statement = connection.prepareStatement(query);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            
            ResultSet rs = statement.executeQuery(); 

            if(rs.next())   
                newAccount = new Account(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("tipo"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (connection != null) {
                        try {
                            DriverManagerConnectionPool.releaseConnection(connection);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        
        return newAccount;
    }
    
    /**
     * Chiude la connessione
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TavoloQuery.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DriverManagerConnectionPool.releaseConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(TavoloQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
