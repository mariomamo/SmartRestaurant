package it.unisa.smartrestaurantapp.entity;

import java.io.Serializable;

/**
 * Rappresenta un piatto del menu
 */
public class Piatto implements Serializable {
    private Long id;
    private String nome;
    private String categoria;
    private String ingredienti;
    private String descrizione;
    private Float prezzo;
    private String foto;

    /**
     * Crea un nuovo piatto vuoto
     */
    public Piatto() {
    }

    /**
     * Crea un piatto inizializzandola con i valori passati come parametro
     * @param id id del piatto
     * @param nome nome del piatto
     * @param categoria categoria culinaria del piatto
     * @param descrizione descrizione del piatto
     * @param ingredienti ingredienti del piatto
     * @param prezzo prezzo del piatto
     * @param foto percorso della foto del piatto
     */
    public Piatto(Long id, String nome, String categoria, String descrizione, String ingredienti, Float prezzo, String foto) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.ingredienti = ingredienti;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.foto = foto;
    }

    /**
     * Crea un piatto inizializzandola con i valori passati come parametro
     * @param nome nome del piatto
     * @param categoria categoria culinaria del piatto
     * @param descrizione descrizione del piatto
     * @param ingredienti ingredienti del piatto
     * @param prezzo prezzo del piatto
     * @param foto percorso della foto del piatto
     */
    public Piatto(String nome, String categoria, String descrizione, String ingredienti, Float prezzo, String foto) {
        this.nome = nome;
        this.categoria = categoria;
        this.ingredienti = ingredienti;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.foto = foto;
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
     * Restituisce il nome del piatto
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setta un nuovo nome al piatto
     * @param nome nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la categoria del piatto
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setta una nuova categoria al piatto
     * @param categoria nuova categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Restituisce gli ingredienti del piatto
     * @return ingredienti
     */
    public String getIngredienti() {
        return ingredienti;
    }

    /**
     * Setta dei nuovi ingredienti del piatto
     * @param ingredienti nuovi ingredienti
     */
    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    /**
     * Restituisce la descrizione del piatto
     * @return descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Setta una nuova descrizione del piatto
     * @param descrizione nuova descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce il prezzo del piatto
     * @return prezzo
     */

    public Float getPrezzo() {
        return prezzo;
    }

    /**
     * Setta un nuovo prezzo del piatto
     * @param prezzo nuovo prezzo
     */
    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * Restituisce il percorso della foto del piatto
     * @return foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Setta un nuovo percorso per la foto del piatto
     * @param foto nuovo percorso della foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Piatto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", ingredienti='" + ingredienti + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", foto=" + foto +
                '}';
    }
}
