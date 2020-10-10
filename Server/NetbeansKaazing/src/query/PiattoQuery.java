package query;

import entity.Piatto;
import entity.PiattoOrdinato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Si occupa di memorizzare e gestire i piatti memorizzati nel database
 * @author Syrenne
 */
public class PiattoQuery implements PiattoQueryInterface {
    private Connection connection;

    /**
     * Inserisce un nuovo piatto nel database
     * @param piatto piatto da memorizzare
     * @return piatto memorizzato
     */
    @Override
    public Piatto insert(Piatto piatto) {
        try {  
            connection = DriverManagerConnectionPool.getConnection();
            String query = "INSERT INTO Piatto(nome, categoria, ingredienti, prezzo, foto, descrizione) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            
            preparedStmt.setString(1, piatto.getNome());
            preparedStmt.setString(2, piatto.getCategoria());
            preparedStmt.setString(3, piatto.getIngredienti());
            preparedStmt.setFloat(4, piatto.getPrezzo());
            preparedStmt.setString(5, piatto.getFoto());
            preparedStmt.setString(6, piatto.getDescrizione());
            
            preparedStmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            close();
        }
        
        return piatto;
    }
    
    @Override
    public Piatto findById(int id) {
        Piatto piatto = null;
        try {  
            connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT * FROM Piatto AS p WHERE p.id = ?;";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            
            preparedStmt.setInt(1, id);     
            ResultSet rs = preparedStmt.executeQuery();
            
            if (rs.next()) {                
                piatto = new Piatto(Long.parseLong(rs.getInt("id") + ""), rs.getString("nome"), rs.getString("categoria"), rs.getString("descrizione"), rs.getString("ingredienti"), rs.getFloat("prezzo"), rs.getString("foto"));
            }
            
            preparedStmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            close();
        }
        
        return piatto;
    }

    /**
     * Restituisce tutti i piatti di una certa categoria
     * @param piatto contiene la categoria da cercare
     * @param inizio indice da cui far partire la ricerca
     * @param fine indice da cui terminare la ricerca
     * @return piatti memorizzati, compresi tra inizio e fine, della categoria richiesta
     */
    @Override
    public List<Piatto> findByCategoria(Piatto piatto, int inizio, int fine) {
        List<Piatto> piatti = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT * FROM Piatto WHERE Piatto.categoria = ? LIMIT ?, ?";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, piatto.getCategoria());
            statement.setInt(2, inizio);
            statement.setInt(3, fine);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {                
                Piatto p = new Piatto(rs.getLong("id"), rs.getString("nome"), rs.getString("categoria"), rs.getString("descrizione"), rs.getString("ingredienti"), rs.getFloat("prezzo"), rs.getString("foto"));
                piatti.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return piatti;
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
