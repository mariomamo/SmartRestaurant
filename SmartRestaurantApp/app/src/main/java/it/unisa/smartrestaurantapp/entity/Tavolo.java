package it.unisa.smartrestaurantapp.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rappresenta un tavolo della sala
 */
public class Tavolo implements Serializable {
    private String username;
    private String password;
    private String nome;
    private Integer posti;
    private Boolean libero;
    private Boolean vuolePagare;
    private Comanda comanda;
    private ArrayList<PiattoOrdinato> carrello;

    /**
     * Crea un tavolo vuoto
     */
    public Tavolo() {
        this.comanda = new Comanda();
        this.carrello = new ArrayList<PiattoOrdinato>();
    }

    /**
     * Crea un tavolo inizializzato con i parametri passati
     * @param username username del tavolo
     * @param password password per accedere al tavolo
     * @param nome nome del tavolo
     * @param posti numero di posti del tavolo
     * @param libero 1 se il tavolo è libero, 0 se è occupato
     * @param vuolePagare 1 se il tavolo vuole pagare, 0 altrimenti
     */
    public Tavolo(String username, String password, String nome, Integer posti, Boolean libero, Boolean vuolePagare) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.posti = posti;
        this.libero = libero;
        this.vuolePagare = vuolePagare;
        this.comanda = new Comanda();
        this.carrello = new ArrayList<PiattoOrdinato>();
    }

    /**
     * Ritorna l'username del tavolo
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setta una nuova username al tavolo
     * @param username nuova username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Ritorna la password del tavolo
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setta una nuova password al tavolo
     * @param password nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Ritorna il nome del tavolo
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setta un nuovo nome al tavolo
     * @param nome nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Ritorna il numero di posti del tavolo
     * @return numero di posti
     */
    public Integer getPosti() {
        return posti;
    }

    /**
     * Setta un nuovo numero di posti al tavolo
     * @param posti nuovo numero di posti
     */
    public void setPosti(Integer posti) {
        this.posti = posti;
    }

    /**
     * Ritorna lo stato del tavolo
     * @return 1 se è libero, 0 se è occupato
     */
    public Boolean isLibero() {
        return libero;
    }

    /**
     * Setta un nuovo stato al tavolo
     * @param libero 1 se è libero, 0 se è occupato
     */
    public void setLibero(Boolean libero) {
        this.libero = libero;
    }

    /**
     * Ritorna un booleano indicante se il tavolo vuole pagare
     * @return 1 se vuole pagare, 0 altrimenti
     */
    public boolean vuolePagare() {
        return this.vuolePagare;
    }

    /**
     * Setta un booleano per indicare se il tavolo vuole pagare
     * @param v 1 se vuole pagare, 0 altrimenti
     */
    public void setVuolePagare(boolean v) {
        this.vuolePagare = v;
    }

    /**
     * Ritorna la comanda del tavolo
     * @return comanda
     */
    public Comanda getComanda() {
        return comanda;
    }

    /**
     * Setta una nuova comanda per il tavolo
     * @param comanda nuova comanda
     */
    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    /**
     * Ritorna tutti i piatti ordinati della comanda
     * @return piatti ordinati dal tavolo
     */
    public ArrayList<PiattoOrdinato> getCarrello() {
        return this.carrello;
    }

    public void setCarrello(ArrayList<PiattoOrdinato> carrello) {
        this.carrello = carrello;
    }

    /**
     * Aggiunge un piatto ordinato alla comanda del tavolo
     * @param piattoOrdinato piatto ordinato da aggiungere
     */
    public void addToCart(PiattoOrdinato piattoOrdinato) {
        this.carrello.add(piattoOrdinato);
    }

    public void spostaDalCarrelloAllaComanda() {
        this.comanda.getPiattiOrdinati().addAll(this.carrello);
        this.carrello = new ArrayList<PiattoOrdinato>();
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + nome + '\'' +
                ", posti=" + posti +
                ", libero=" + libero +
                '}';
    }
}
