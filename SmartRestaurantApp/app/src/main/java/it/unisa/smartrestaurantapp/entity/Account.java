package it.unisa.smartrestaurantapp.entity;

import java.io.Serializable;

/**
 * Rappresenta l'user che si collega all'app come "Proprietario", "Executive Chef" o "Capo Cameriere"
 */
public class Account implements Serializable {
    public static final int TYPE_PROPRIETARIO = 0;
    public static final int TYPE_CAPO_CAMERIERE = 1;
    public static final int TYPE_EXECUTIVE_CHEF = 2;

    private String username;
    private String password;
    private String nome;
    private Integer tipo;

    /**
     * Costruisce un account vuoto
     */
    public Account() {
    }

    /**
     * Costruisce un account inizializzato con i valori passati come parametro
     * @param username username dell'account
     * @param password password dell'account
     * @param nome nome dell'account
     * @param tipo tipo dell'account, 0 se è "Proprietario", 1 se è "Capo Cameriere", 2 se è "Executive Chef"
     */
    public Account(String username, String password, String nome, Integer tipo) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.tipo = tipo;
    }

    /**
     * Ritorna l'username dell'account
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setta una nuova username all'account
      * @param username nuova username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Ritorna la password dell'account
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setta una nuova password all'account
     * @param password nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Ritorna il nome dell'account
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setta un nuovo nome all'account
     * @param nome nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Ritorna il tipo dell'account
     * @return 0 se è "Proprietario", 1 se è "Capo Cameriere", 2 se è "Executive Chef"
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * Setta un nuovo tipo all'account
     * @param tipo nuova tipo, 0 se è "Proprietario", 1 se è "Capo Cameriere", 2 se è "Executive Chef"
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
