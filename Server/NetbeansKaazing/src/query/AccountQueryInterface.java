package query;

import entity.Account;

/**
 * Si occupa del recupero delle informazioni relative agli account
 * @author Syrenne
 */
public interface AccountQueryInterface {
    /**
     * Effettua il recupero delle informazioni dell'account dal database, se esistono username e password
     * @param account contiene username e password per recuperare le informazioni dal database
     * @return newAccount, null se le credenziali non sono presenti nel database, altrimenti è inizializzato con le informazioni dell'account del database
     */
    public Account login(Account account);
    
     /**
      * Chiude la connessione
      */
    public void close();
}
