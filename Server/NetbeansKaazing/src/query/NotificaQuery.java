package query;

import entity.Notifica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Si occupa del recupero ed inserimento delle informazioni relative alle notifiche
 * @author Syrenne
 */
public class NotificaQuery implements NotificaQueryInterface {
    private Connection connection;
    
    /**
     * Inserisce una nuova notifica all'interno del database
     * @param notifica notifica da inserire
     * @return notifica inserita
     */
    @Override
    public Notifica insert(Notifica notifica) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "INSERT INTO smartrestaurant.Notifica(username, categoria, mittente, testo, letta, data) VALUES (?, ?, ?, ?, ?, ?);";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, notifica.getDestinatario());
            statement.setString(2, notifica.getCategoria());
            statement.setString(3, notifica.getMittente());
            statement.setString(4, notifica.getTesto());
            statement.setBoolean(5, notifica.isLetta());
            statement.setString(6, notifica.getData());
            
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return notifica;
    }

    /**
     * Aggiorna una notifica con le nuove informazioni passate come parametro
     * @param notifica contiene le nuove informazioni da memorizzare e l'id per ricercare la notifica da aggiornare
     * @return notifica aggiornata
     */
    @Override
    public Notifica update(Notifica notifica) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "UPDATE Notifica SET Notifica.username = ?, Notifica.categoria = ?, Notifica.mittente = ?, Notifica.testo = ?, Notifica.letta = ? WHERE Notifica.id = ?";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, notifica.getDestinatario());
            statement.setString(2, notifica.getCategoria());
            statement.setString(3, notifica.getMittente());
            statement.setString(4, notifica.getTesto());
            statement.setBoolean(5, notifica.isLetta());
            statement.setLong(6, notifica.getId());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return notifica;
    }
    
    /**
     * Restituisce la notifica con id uguale a quello della notifica passata come parametro
     * @param notifica notifica con lo stesso id della notifica da cercare
     * @return notifica, se esiste. null altrimenti
     */
    @Override
    public Notifica findById(Notifica notifica) {
        Notifica new_notifica = null;
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT * FROM Notifica AS n WHERE n.id = ?";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setLong(1, notifica.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                new_notifica = new Notifica(rs.getInt("id"), rs.getString("username"), rs.getString("categoria"), rs.getString("mittente"), rs.getString("testo"), rs.getBoolean("letta"), rs.getString("data"));            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return new_notifica;
    }
    
    /**
     * Restituisce tutte le notifiche memorizzate nel database con un determinato username
     * @param notifica contiene l'username per cercare le notifiche
     * @param inizio indica da quale notifica comincia il recupero
     * @param fine indica da quale notifica finisce il recupero
     * @return tutte le notifiche memorizzate comprese tra inizio e fine e con l'username della notifica passata come parametro
     */
    @Override
    public List<Notifica> findAll(Notifica notifica, int inizio, int fine) {
        ArrayList<Notifica> notifiche = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT * FROM Notifica AS n WHERE n.username = ? ORDER BY n.id DESC LIMIT ?, ?;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, notifica.getDestinatario());
            statement.setInt(2, inizio);
            statement.setInt(3, fine);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Notifica notif = new Notifica(rs.getInt("id"), rs.getString("username"), rs.getString("categoria"), rs.getString("mittente"), rs.getString("testo"), rs.getBoolean("letta"), rs.getString("data"));
                notifiche.add(notif);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return notifiche;
    }
    
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
