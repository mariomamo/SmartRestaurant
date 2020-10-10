package query;

import entity.Comanda;
import entity.Tavolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Si occupa della memorizzazione e gestione dei tavoli all'interno del database
 * @author Syrenne
 */
public class TavoloQuery implements TavoloQueryInterface {
    private Connection connection;
    
    /**
     * Effettua il recupero delle informazioni del tavolo dal database, se esistono username e password
     * @param tavolo contiene username e password per recuperare le informazioni dal database
     * @return newTavolo, null se le credenziali non sono presenti nel database, altrimenti è inizializzato con le informazioni del tavolo del database
     */
    @Override
    public Tavolo login(Tavolo tavolo) {
        Tavolo newTavolo = null;
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT t.username, aes_decrypt(t.password, \"" + DbManager.getKey() + "\") as password,\n" + 
                                             "t.nome, t.libero, t.posti, t.vuole_pagare\n" +
                                             "FROM Tavolo as t\n" +
                                             "WHERE t.username = ? and aes_decrypt(t.password, \"" + DbManager.getKey() + "\") = ?;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, tavolo.getUsername());
            statement.setString(2, tavolo.getPassword());
            
            ResultSet rs = statement.executeQuery();

            if(rs.next())
                newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return newTavolo;
    }

    /**
     * Inserisce un nuovo tavolo all'interno del database
     * @param tavolo il tavolo da memorizzare
     * @return il tavolo memorizzato
     */
    @Override
    public Tavolo insert(Tavolo tavolo) {
        try {  
            connection = DriverManagerConnectionPool.getConnection();
            String query = "INSERT INTO smartrestaurant.Tavolo (username, password, nome, posti, vuole_pagare, libero)" + " values (?, aes_encrypt(?, \"" + DbManager.getKey() + "\"), ?, ?, ?, ?)";

            System.out.println(tavolo.vuolePagare());
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, tavolo.getUsername());
            preparedStmt.setString (2, tavolo.getPassword());
            preparedStmt.setString  (3, tavolo.getNome());
            preparedStmt.setInt(4, tavolo.getPosti());
            preparedStmt.setBoolean(5, tavolo.vuolePagare());
            preparedStmt.setBoolean(6, tavolo.isLibero());
            preparedStmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
    }

    /**
     * Aggiorna un tavolo esistente all'interno del database con nuove informazioni
     * @param tavolo il tavolo contenente le informazioni da aggiornare
     * @return tavolo il tavolo aggiornato
     */
    @Override
    public Tavolo update(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "UPDATE smartrestaurant.Tavolo as t SET t.password = aes_encrypt(?, \"" + DbManager.getKey() + "\"), t.nome = ?, t.posti = ?, t.vuole_pagare = ?, t.libero = ? where t.username = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, tavolo.getPassword());
            preparedStmt.setString  (2, tavolo.getNome());
            preparedStmt.setInt(3, tavolo.getPosti());
            preparedStmt.setBoolean(4, tavolo.vuolePagare());
            preparedStmt.setBoolean(5, tavolo.isLibero());
            preparedStmt.setString (6, tavolo.getUsername());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
    }
    
    /**
     * Aggiorna un tavolo esistente all'interno del database con nuove informazioni relative al pagamento
     * @param tavolo il tavolo contenente le informazioni da aggiornare
     * @return tavolo il tavolo aggiornato
     */
    @Override
    public Tavolo setPagato(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "UPDATE smartrestaurant.Tavolo as t SET t.vuole_pagare = false, t.libero = true where t.username = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, tavolo.getUsername());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
    }
    
    /**
     * Aggiorna lo stato di un tavolo esistente all'interno del database
     * @param tavolo il tavolo contenente lo stato da aggiornare
     * @return il tavolo aggiornato
     */
    @Override
    public Tavolo updateStato(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "UPDATE smartrestaurant.Tavolo as t SET t.libero = ? where t.username = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setBoolean(1, tavolo.isLibero());
            preparedStmt.setString (2, tavolo.getUsername());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
    }
    
    /**
     * Aggiorna il pagamento di un tavolo esistente all'interno del database
     * @param tavolo il tavolo contenente il pagamento da aggiornare
     * @return il tavolo aggiornato
     */
    @Override
    public Tavolo updateVuolePagare(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            String query = "UPDATE smartrestaurant.Tavolo as t SET t.vuole_pagare = ? where t.username = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setBoolean(1, tavolo.vuolePagare());
            preparedStmt.setString (2, tavolo.getUsername());
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
    }

    /**
     * Elimina un tavolo dal database
     * @param tavolo tavolo da eliminare
     */
    @Override
    public void delete(Tavolo tavolo) {
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "DELETE FROM Tavolo WHERE Tavolo.username = ?;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString (1, tavolo.getUsername());
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * Restituisce tutti i tavoli memorizzati nel database
     * @return tavoli la lista dei tavoli memorizzati
     */
    @Override
    public List<Tavolo> findAll() {
        ArrayList<Tavolo> tavoli = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT t.username, aes_decrypt(t.password, \"" + DbManager.getKey() + "\") as password, t.nome, t.posti, t.libero, t.vuole_pagare FROM Tavolo as t ORDER BY t.username";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                Tavolo newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(newTavolo.getUsername());
                if (comanda != null) {                            
                    newTavolo.setComanda(comanda);
                }
                
                tavoli.add(newTavolo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavoli;
    }

    /**
     * Restituisce tutti i tavoli liberi memorizzati nel database
     * @return la lista dei tavoli liberi memorizzati
     */
    @Override
    public List<Tavolo> findAllFree() {
        ArrayList<Tavolo> tavoli = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT t.username, aes_decrypt(t.password, \"" + DbManager.getKey() + "\") as password, t.nome, t.posti, t.libero, t.vuole_pagare FROM Tavolo as t where t.libero = true ORDER BY t.username";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                Tavolo newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(newTavolo.getUsername());
                if (comanda != null) {                            
                    newTavolo.setComanda(comanda);
                }
                
                tavoli.add(newTavolo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavoli;
    }

    /**
     * Restituisce tutti i tavoli occupati memorizzati nel database
     * @return la lista dei tavoli occupati memorizzati
     */
    @Override
    public List<Tavolo> findAllOccupied() {
        ArrayList<Tavolo> tavoli = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT t.username, aes_decrypt(t.password, \"" + DbManager.getKey() + "\") as password, t.nome, t.posti, t.libero, t.vuole_pagare FROM Tavolo as t where t.libero = false ORDER BY t.username";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(); 
            
            while(rs.next()) {
                Tavolo newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(newTavolo.getUsername());
                if (comanda != null) {                            
                    newTavolo.setComanda(comanda);
                }
                
                tavoli.add(newTavolo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavoli;
    }
    
    /**
     * Restituisce tutti i tavoli occupati aventi una comanda
     * @return tutti i tavoli occupati aventi una comanda
     */
    @Override
    public List<Tavolo> findAllComanda() {
        ArrayList<Tavolo> tavoli = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT t.username, aes_decrypt(t.password, \"" + DbManager.getKey() + "\") as password, t.nome, t.posti, t.libero, t.vuole_pagare, R1.data FROM Tavolo AS t\n" +
"	JOIN (SELECT c.id, username, stato, data, recensione, totale FROM Comanda AS c JOIN (SELECT MAX(c.id) AS id FROM Comanda AS c GROUP BY c.username) AS R1 WHERE R1.id = c.id) AS R1\n" +
"    WHERE t.username = R1.username AND t.libero = false ORDER BY R1.data DESC;";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(); 
            
            while(rs.next()) {  
                Tavolo newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(newTavolo.getUsername());
                if (comanda != null) {                            
                    newTavolo.setComanda(comanda);
                    tavoli.add(newTavolo);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavoli;
    }

    /**
     * Restituisce tutti i tavoli che vogliono pagare memorizzati nel database
     * @return la lista dei tavoli che vogliono pagare memorizzati
     */
    @Override
    public List<Tavolo> findAllWantPay() {
        ArrayList<Tavolo> tavoli = new ArrayList();
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT * FROM smartrestaurant.Tavolo as t where t.vuole_pagare = true";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();  
            
            while(rs.next()) {
                Tavolo newTavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(newTavolo.getUsername());
                if (comanda != null) {                            
                    newTavolo.setComanda(comanda);
                }
                
                tavoli.add(newTavolo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavoli;
    }

    /**
     * Restituisce un tavolo con l'username passata come parametro
     * @param username username del tavolo da cercare
     * @return il tavolo con la stessa username, se esiste. null altrimenti
     */
    @Override
    public Tavolo findByUsername(String username) {
        Tavolo tavolo = null;
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            
            String query = "SELECT * FROM smartrestaurant.Tavolo as t where t.username=?";
            
            PreparedStatement statement = null;
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                tavolo = new Tavolo(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getInt("posti"), rs.getBoolean("libero"), rs.getBoolean("vuole_pagare"));
                
                ComandaQuery comandaQuery = new ComandaQuery();
                Comanda comanda = comandaQuery.findByUsername(tavolo.getUsername());
                if (comanda != null) {                            
                    tavolo.setComanda(comanda);
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        
        return tavolo;
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
