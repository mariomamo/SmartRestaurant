package query;

import entity.Piatto;
import java.util.List;

/**
 * Si occupa di memorizzare e gestire i piatti memorizzati nel database
 * @author Syrenne
 */
public interface PiattoQueryInterface {
    /**
     * Inserisce un nuovo piatto nel database
     * @param piatto piatto da memorizzare
     * @return piatto memorizzato
     */
    public Piatto insert(Piatto piatto);
    
    /**
     * Restituisce tutti i piatti di una certa categoria
     * @param piatto contiene la categoria da cercare
     * @param inizio indice da cui far partire la ricerca
     * @param fine indice da cui terminare la ricerca
     * @return piatti memorizzati, compresi tra inizio e fine, della categoria richiesta
     */
    public List<Piatto> findByCategoria(Piatto piatto, int inizio, int fine);
    
    /**
     * Cerca un piatto in base al suo id
     * @return piatto
     */
    public Piatto findById(int id);
    
    /**
     * Chiude la connessione
     */
    public void close();
}
