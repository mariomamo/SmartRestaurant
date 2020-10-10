package query;

import entity.Comanda;
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
 * Si occupa di gestire la memorizzazione e la ricerca dei piatti ordinati all'interno del database
 * @author Syrenne
 */
public class PiattoOrdinatoQuery implements PiattoOrdinatoQueryInterface {
    private Connection connection;
    private PiattoQuery piattoQuery = new PiattoQuery();
    
    /**
     * Inserisce un nuovo piatto ordinato all'interno del database
     * @param piattoOrdinato piatto ordinato da inserire
     * @param idComanda identificativo della comanda relativa al piatto ordinato
     * @return piatto ordinato memorizzato
     */
    @Override
    public PiattoOrdinato insert(PiattoOrdinato piattoOrdinato, int idComanda) {
        try {  
            connection = DriverManagerConnectionPool.getConnection();
         
            /*PiattoQuery piattoQuery = new PiattoQuery();
            piattoQuery.insert(piattoOrdinato.getPiatto());*/

            String query = "INSERT INTO PiattoOrdinato (id_piatto, comanda, stato, data, quantita, note)" + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, piattoOrdinato.getPiatto().getId());
            preparedStmt.setInt(2, idComanda);
            preparedStmt.setBoolean(3, piattoOrdinato.getStato());
            preparedStmt.setString(4, piattoOrdinato.getData());
            preparedStmt.setInt(5, piattoOrdinato.getQuantita());
            preparedStmt.setString(6, piattoOrdinato.getNote());
            
            preparedStmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return piattoOrdinato;
    }
    
    /**
     * Restituisce il piatto ordinato con l'id corrispondente
     * @param id id del piatto ordinato da cercare
     * @return piatto ordinato con l'id passato come parametro se esiste, null altrimenti
     */
    @Override
    public PiattoOrdinato findById(int id) {
        PiattoOrdinato piattoOrdinato = null;
        
        try {  
            connection = DriverManagerConnectionPool.getConnection();

            String query = "SELECT * FROM PiattoOrdinato AS po WHERE po.id = ?;";
            
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id);
            
            preparedStmt.execute();            
            ResultSet rs = preparedStmt.executeQuery();
            
            if (rs.next()) {
                piattoOrdinato = new PiattoOrdinato();
                piattoOrdinato.setId(Long.parseLong(rs.getInt("id") + ""));
                
                Piatto piatto = new PiattoQuery().findById(Integer.parseInt(rs.getInt("id_piatto") + ""));
                piattoOrdinato.setPiatto(piatto);
                
                piattoOrdinato.setStato(rs.getBoolean("stato"));
                piattoOrdinato.setData(rs.getString("data"));
                piattoOrdinato.setNote(rs.getString("note"));
                piattoOrdinato.setQuantita(Integer.parseInt(rs.getInt("quantita") + ""));
                piattoOrdinato.setComanda(rs.getInt("comanda"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return piattoOrdinato;
    }

    /**
     * Aggiorna il piatto ordinato passato come parametro
     * @param p piatto ordinato da aggiornare
     * @param id_comanda id della comanda del piatto ordinato
     * @return piatto ordinato aggiornato
     */
    @Override
    public PiattoOrdinato update(PiattoOrdinato p, long id_comanda) {
        try {  
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "UPDATE PiattoOrdinato as p SET p.comanda = ?, p.stato = ?, p.data = ?, p.quantita = ?, p.note = ? where p.id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id_comanda);
            preparedStmt.setBoolean(2, p.getStato());
            preparedStmt.setString(3, p.getData());
            preparedStmt.setInt(4, p.getQuantita());
            preparedStmt.setString(5, p.getNote());
            preparedStmt.setLong(6, p.getId());
            
            preparedStmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return p;
    }

    /**
     * Rimuove il piatto ordinato passato come parametro
     * @param id piatto ordinato da rimuovere
     */
    @Override
    public void delete(PiattoOrdinato p) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "DELETE FROM piattoordinato WHERE piattoordinato.id = ?;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setLong(1, p.getId());
            
            System.out.println("Delete code: " + statement.executeUpdate());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Restituisce tutti i piatti ordinati relativi ad una comanda memorizzati nel database
     * @param comanda comanda relativa ai piatti ordinati da cercare
     * @return piatti ordinati relativi alla comanda
     */
    @Override
    public List<PiattoOrdinato> findByComanda(Comanda comanda) {
        ArrayList<PiattoOrdinato> piattiOrdinati = new ArrayList<>();
        PiattoOrdinato p;
        
        if(comanda != null)
            try {
                connection = DriverManagerConnectionPool.getConnection();
                String query = "SELECT * FROM PiattoOrdinato AS po JOIN Comanda AS c JOIN Piatto AS p WHERE po.comanda = c.id AND c.id = ? AND po.id_piatto = p.id;";

                PreparedStatement statement = null;
                statement = connection.prepareStatement(query);
                statement.setInt(1, comanda.getId());

                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    p = new PiattoOrdinato();
                    p.setId(rs.getLong("id"));

                    Piatto piatto = new Piatto();
                    piatto.setId(rs.getLong("id_piatto"));
                    piatto.setNome(rs.getString("nome"));
                    piatto.setCategoria(rs.getString("categoria"));
                    piatto.setIngredienti(rs.getString("ingredienti"));
                    piatto.setDescrizione(rs.getString("descrizione"));
                    piatto.setPrezzo(rs.getFloat("prezzo"));
                    piatto.setFoto(rs.getString("foto"));

                    p.setPiatto(piatto);
                    p.setStato(rs.getBoolean("stato"));
                    p.setData(rs.getString("data"));
                    p.setNote(rs.getString("note"));
                    p.setQuantita(rs.getInt("quantita"));

                    piattiOrdinati.add(p);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                close();
            }
        
        return piattiOrdinati;
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