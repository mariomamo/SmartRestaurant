package query;

import entity.Tavolo;
import java.util.List;

/**
 * Si occupa della memorizzazione e gestione dei tavoli all'interno del database
 * @author Syrenne
 */
public interface TavoloQueryInterface {
    /**
     * Effettua il recupero delle informazioni del tavolo dal database, se esistono username e password
     * @param tavolo contiene username e password per recuperare le informazioni dal database
     * @return newTavolo, null se le credenziali non sono presenti nel database, altrimenti è inizializzato con le informazioni del tavolo del database
     */
    public Tavolo login(Tavolo tavolo);
    
    /**
     * Inserisce un nuovo tavolo all'interno del database
     * @param tavolo il tavolo da memorizzare
     * @return il tavolo memorizzato
     */
    public Tavolo insert(Tavolo tavolo);
    
    /**
     * Aggiorna un tavolo esistente all'interno del database con nuove informazioni
     * @param tavolo il tavolo contenente le informazioni da aggiornare
     * @return tavolo il tavolo aggiornato
     */
    public Tavolo update(Tavolo tavolo);
    
    /**
     * Aggiorna lo stato di un tavolo esistente all'interno del database
     * @param tavolo il tavolo contenente lo stato da aggiornare
     * @return il tavolo aggiornato
     */
    public Tavolo updateStato(Tavolo tavolo);
    
    
    /**
     * Imposta lo stato di un tavolo come 'libero' e setta
     * la variabile 'vuole pagare' del tavolo a false.
     * Serve per rendere il tavolo disponibile per altri clienti.
     * @param tavolo
     * @return 
     */
    public Tavolo setPagato(Tavolo tavolo);
    
    /**
     * Aggiorna il pagamento di un tavolo esistente all'interno del database
     * @param tavolo il tavolo contenente il pagamento da aggiornare
     * @return il tavolo aggiornato
     */
    public Tavolo updateVuolePagare(Tavolo tavolo);
    
    /**
     * Elimina un tavolo dal database
     * @param tavolo tavolo da eliminare
     */
    public void delete(Tavolo tavolo);
    
    /**
     * Restituisce tutti i tavoli memorizzati nel database
     * @return tavoli la lista dei tavoli memorizzati
     */
    public List<Tavolo> findAll();
    
    /**
     * Restituisce tutti i tavoli liberi memorizzati nel database
     * @return la lista dei tavoli liberi memorizzati
     */
    public List<Tavolo> findAllFree();
    
    /**
     * Restituisce tutti i tavoli occupati memorizzati nel database
     * @return la lista dei tavoli occupati memorizzati
     */
    public List<Tavolo> findAllOccupied();
    
    /**
     * Restituisce tutti i tavoli occupati aventi una comanda
     * @return tutti i tavoli occupati aventi una comanda
     */
    public List<Tavolo> findAllComanda();
    
    /**
     * Restituisce tutti i tavoli che vogliono pagare memorizzati nel database
     * @return la lista dei tavoli che vogliono pagare memorizzati
     */
    public List<Tavolo> findAllWantPay();
    
    /**
     * Restituisce un tavolo con l'username passata come parametro
     * @param username username del tavolo da cercare
     * @return il tavolo con la stessa username, se esiste. null altrimenti
     */
    public Tavolo findByUsername(String username);
    
    /**
     * Chiude la connessione
     */
    public void close();
}
