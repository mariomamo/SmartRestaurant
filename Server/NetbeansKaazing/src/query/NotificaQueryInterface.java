package query;

import entity.Notifica;
import java.util.List;

/***
 * Si occupa del recupero ed inserimento delle informazioni relative alle notifiche
 * @author Syrenne
 */
public interface NotificaQueryInterface {
    /**
     * Inserisce una nuova notifica all'interno del database
     * @param notifica notifica da inserire
     * @return notifica inserita
     */
    public Notifica insert(Notifica notifica);
    
    /**
     * Aggiorna una notifica con le nuove informazioni passate come parametro
     * @param notifica contiene le nuove informazioni da memorizzare e l'id per ricercare la notifica da aggiornare
     * @return notifica aggiornata
     */
    public Notifica update(Notifica notifica);
    
    /**
     * Restituisce la notifica con id uguale a quello della notifica passata come parametro
     * @param notifica notifica con lo stesso id della notifica da cercare
     * @return notifica, se esiste. null altrimenti
     */
    public Notifica findById(Notifica notifica);
    
    /**
     * Restituisce tutte le notifiche memorizzate nel database con un determinato username
     * @param notifica contiene l'username per cercare le notifiche
     * @param inizio indica da quale notifica comincia il recupero
     * @param fine indica da quale notifica finisce il recupero
     * @return tutte le notifiche memorizzate comprese tra inizio e fine e con l'username della notifica passata come parametro
     */
    public List<Notifica> findAll(Notifica notifica, int inizio, int fine);
    
    /**
     * Chiude la connessione
     */
    public void close();
    
}
