package query;

import entity.Comanda;
import entity.Tavolo;

/***
 * Si occupa del recupero ed inserimento delle informazioni relative alle comande
 * @author Syrenne
 */
public interface ComandaQueryInterface {
    /**
     * Crea una nuova comanda relativa al tavolo passato come parametro
     * @param tavolo contiene i dati per creare la comanda
     * @return comanda creata con i dati contenuti nel tavolo 
     */
    public Comanda insert(Tavolo tavolo);
    
    /**
     * Restituisce la comanda relativa al tavolo con l'username passata come parametro
     * @param username username del tavolo di cui cercare la comanda
     * @return comanda del tavolo, se esiste. null altrimenti
     */
    public Comanda findByUsername(String username);
    
    /**
     * Chiude la connessione
     */
    public void close();
}
