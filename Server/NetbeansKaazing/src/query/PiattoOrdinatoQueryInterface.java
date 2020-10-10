package query;

import entity.Comanda;
import entity.PiattoOrdinato;
import java.util.List;

/**
 * Si occupa di gestire la memorizzazione e la ricerca dei piatti ordinati all'interno del database
 * @author Syrenne
 */
public interface PiattoOrdinatoQueryInterface {
    /**
     * Inserisce un nuovo piatto ordinato all'interno del database
     * @param piattoOrdinato piatto ordinato da inserire
     * @param idComanda identificativo della comanda relativa al piatto ordinato
     * @return piatto ordinato memorizzato
     */
    public PiattoOrdinato insert(PiattoOrdinato piattoOrdinato, int idComanda);
    
    /**
     * Aggiorna il valore di un piatto ordinato
     * @param p piatto ordinato da modificare
     * @param id id del piatto da modificare
     * @return  PiattoOrdinato modificato
     */
    public PiattoOrdinato update(PiattoOrdinato p, long id);
    
    /**
     * Rimuove un piatto ordinato
     * @param p  piatto ordinato da rimuovere
     */
    public void delete(PiattoOrdinato p);
    
    /**
     * Restituisce tutti i piatti ordinati relativi ad una comanda memorizzati nel database
     * @param comanda comanda relativa ai piatti ordinati da cercare
     * @return piatti ordinati relativi alla comanda
     */
    public List<PiattoOrdinato> findByComanda(Comanda comanda);
    
    /**
     * Restituisce un piatto ordinato in base all'id
     * @param id id del piatto ordinato
     * @return  piatto ordianto corrispondente a quell'id
     */
    public PiattoOrdinato findById(int id);
    
    /**
     * Chiude la connessione
     */
    public void close();
}
