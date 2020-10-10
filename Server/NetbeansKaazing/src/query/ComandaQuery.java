package query;

import entity.Comanda;
import entity.PiattoOrdinato;
import entity.Tavolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Si occupa del recupero ed inserimento delle informazioni relative alle comande
 * @author Syrenne
 */
public class ComandaQuery implements ComandaQueryInterface {
    private Connection connection;
 
    /**
     * Crea una nuova comanda relativa al tavolo passato come parametro
     * @param tavolo contiene i dati per creare la comanda
     * @return comanda creata con i dati contenuti nel tavolo 
     */
    @Override
    public Comanda insert(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "INSERT INTO Comanda(username, stato, data, recensione, totale) VALUES(?, ?, ?, ?, ?);";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, tavolo.getUsername());
            statement.setBoolean(2, tavolo.getComanda().getStato());
            statement.setString(3, tavolo.getComanda().getData());
            statement.setString(4, tavolo.getComanda().getRecensione());
            statement.setFloat(5, tavolo.getComanda().getTotale());
            
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo.getComanda();
    }

    /**
     * Restituisce la comanda relativa al tavolo con l'username passata come parametro
     * @param username username del tavolo di cui cercare la comanda
     * @return comanda del tavolo, se esiste. null altrimenti
     */
    @Override
    public Comanda findByUsername(String username) {
        Comanda comanda = null;
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT * FROM Comanda AS c WHERE c.username = ? ORDER BY c.id DESC;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                comanda = new Comanda();
                comanda.setData(rs.getString("data"));
                comanda.setId(rs.getInt("id"));
                comanda.setRecensione(rs.getString("recensione"));
                comanda.setStato(rs.getBoolean("stato"));
                comanda.setTotale(rs.getFloat("totale"));
                
                PiattoOrdinatoQuery piattoOrdinatoQuery = new PiattoOrdinatoQuery();
                ArrayList<PiattoOrdinato> piatti = (ArrayList) piattoOrdinatoQuery.findByComanda(comanda);
                comanda.setPiattiOrdinati(piatti);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return comanda;
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
