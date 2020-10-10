package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Rappresenta un piatto ordinato contenuto in una comanda
 */
public class PiattoOrdinato implements Serializable {
    private Long id;
    private Piatto piatto;
    private Boolean stato;
    private String data;
    private String note;
    private Integer quantita;
    private int comanda;

    /**
     * Crea un piatto ordinato vuoto
     */
    public PiattoOrdinato() {
    }

    /**
     * Crea un piatto ordinato inizializzato con i valori passati come parametro
     * @param id id del piatto ordinato
     * @param piatto piatto del menu che è stato ordinato
     * @param stato 0 se il piatto ordinato è in fase di preparazione, 1 se è pronto
     * @param data data di richiesta del piatto
     * @param note note eventuali per la preparazione del piatto
     * @param comanda id della comanda
     */
    public PiattoOrdinato(Long id, Piatto piatto, Boolean stato, String data, String note, int comanda) {
        this.id = id;
        this.piatto = piatto;
        this.stato = stato;
        this.data = data;
        this.note = note;
        this.quantita = 1;
        this.comanda = comanda;
    }

    /**
     * Crea un piatto ordinato inizializzato con i valori passati come parametro
     * @param piatto piatto del menu che è stato ordinato
     * @param stato 0 se il piatto ordinato è in fase di preparazione, 1 se è pronto
     * @param data data di richiesta del piatto
     * @param note note eventuali per la preparazione del piatto
     * @param comanda id della comanda
     */
    public PiattoOrdinato(Piatto piatto, Boolean stato, String data, String note, int comanda) {
        this.piatto = piatto;
        this.stato = stato;
        this.data = data;
        this.note = note;
        this.quantita = 1;
        this.comanda = comanda;
    }
    
    /**
     * Crea un piatto ordinato inizializzato con i valori passati come parametro
     * @param piatto piatto del menu che è stato ordinato
     * @param stato 0 se il piatto ordinato è in fase di preparazione, 1 se è pronto
     * @param data data di richiesta del piatto
     * @param note note eventuali per la preparazione del piatto
     */
    public PiattoOrdinato(Piatto piatto, Boolean stato, String data, String note) {
        this.piatto = piatto;
        this.stato = stato;
        this.data = data;
        this.note = note;
        this.quantita = 1;
    }
    
    /**
     * Restituisce l'id della comanda
     * @return id della comanda
     */
    public int getComanda() {
        return this.comanda;
    }
    
    /**
     * Cambia l'id della comanda
     * @param comanda id della comanda
     */
    public void setComanda(int comanda) {
        this.comanda = comanda;
    }

    /**
     * Restituisce l'id del piatto
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setta un nuovo id al piatto
     * @param id nuovo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il piatto del menu ordinato
     * @return piatto del menu ordinato
     */
    public Piatto getPiatto() {
        return piatto;
    }

    /**
     * Setta un nuovo piatto del menu ordinato
     * @param piatto piatto del menu ordinato
     */
    public void setPiatto(Piatto piatto) {
        this.piatto = piatto;
    }

    /**
     * Ritorna lo stato del piatto ordinato
     * @return 0 se il piatto ordinato è in fase di preparazione, 1 se è pronto
     */
    public Boolean getStato() {
        return stato;
    }

    /**
     * Setta un nuovo stato al piatto ordinato
     * @param stato 0 se il piatto ordinato è in fase di preparazione, 1 se è pronto
     */
    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    /**
     * Ritorna la data di richiesta del piatto ordinato
     * @return data del piatto ordinato
     */
    public String getData() {
        return data;
    }

    /**
     * Setta una nuova data di richiesta al piatto ordinato
     * @param data nuova data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Ritorna le note relative al piatto ordinato
     * @return note del piatto ordinato
     */
    public String getNote() {
        return note;
    }

    /**
     * Setta delle note al piatto ordinato
     * @param note nuove note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Ritorna quanti piatti dello stesso tipo sono stati ordinati
     * @return quantità
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * Setta una nuova quantita di piatti ordinati
     * @param quantita nuova quantita
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PiattoOrdinato other = (PiattoOrdinato) obj;

        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        if (!Objects.equals(this.piatto, other.piatto)) {
            return false;
        }
        if (!Objects.equals(this.stato, other.stato)) {
            return false;
        }
        if (!Objects.equals(this.quantita, other.quantita)) {
            return false;
        }
        return true;
    }
    
    
}