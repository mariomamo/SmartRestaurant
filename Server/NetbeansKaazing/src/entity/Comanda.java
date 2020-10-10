package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Rappresenta la comanda di un tavolo
 */
public class Comanda implements Serializable {
    private Integer id;
    private Boolean stato;
    private String data;
    private ArrayList<PiattoOrdinato> piattiOrdinati;
    private String recensione;
    private Float totale;

    /**
     * Crea una nuova comanda vuota
     */
    public Comanda() {
        this.piattiOrdinati = new ArrayList<>();
    }

    /**
     * Ritorna l'id della comanda
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setta un nuovo id alla comanda
     * @param id nuovo id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Ritorna lo stato della comanda, 0 se non � stata pagata, 1 se � stata pagata
     * @return stato
     */
    public Boolean getStato() {
        return stato;
    }

    /**
     * Setta un nuovo stato alla comanda, che indica se � stata pagata o meno
     * @param stato nuovo stato, 0 se non � stata pagata, 1 se � stata pagata
     */
    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    /**
     * Ritorna la data di emissione della comanda
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Setta una nuova data di emissione alla comanda
     * @param data nuova data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Ritorna i piatti ordinati della comanda
     * @return piatti ordinati
     */
    public ArrayList<PiattoOrdinato> getPiattiOrdinati() {
        return piattiOrdinati;
    }

    /**
     * Setta dei nuovi piatti ordinati alla comanda, sostituendoli ai precedenti
     * @param piattiOrdinati i nuovi piatti ordinati
     */
    public void setPiattiOrdinati(ArrayList<PiattoOrdinato> piattiOrdinati) {
        this.piattiOrdinati = piattiOrdinati;
    }

    /**
     * Aggiunge un piatto ordinato alla comanda
     * @param piattoOrdinato il piatto ordinato da aggiungere
     */
    public void addPiatto(PiattoOrdinato piattoOrdinato) {
        piattiOrdinati.add(piattoOrdinato);
    }

    /**
     * Ritorna la recensione della comanda
     * @return recensione
     */
    public String getRecensione() {
        return recensione;
    }

    /**
     * Setta una nuova recensione alla comanda
     * @param recensione la nuova recensione
     */
    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    /**
     * Ritorna il costo totale della comanda
     * @return costo totale
     */
    public Float getTotale() {
        float tot = 0;
        for (PiattoOrdinato p : piattiOrdinati) {
            tot += p.getPiatto().getPrezzo();
        }
        return tot;
    }

    /**
     * Setta un nuovo costo totale alla comanda
     * @param totale nuovo costo totale
     */
    public void setTotale(Float totale) {
        this.totale = totale;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", stato=" + stato +
                ", data=" + data +
                ", piattoOrdinato=" + piattiOrdinati +
                ", recensione='" + recensione + '\'' +
                ", totale=" + totale +
                '}';
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
        final Comanda other = (Comanda) obj;
        if (!Objects.equals(this.recensione, other.recensione)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.stato, other.stato)) {
            return false;
        }
        if (!Objects.equals(this.totale, other.totale)) {
            return false;
        }
        return true;
    }
    
    
}