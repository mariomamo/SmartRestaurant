package it.unisa.smartrestaurantapp.entity;

/**
 * Rappresenta una notifica che può essere scambiata tra "Tavolo" e "Capo Cameriere" oppure tra "Executive Chef" e "Capo Cameriere"
 */
public class Notifica {

    /**
     * Rappresenta i possibili valori associabili a categoria
     */
    public class Categoria {
        public static final String PAGAMENTO = "Pagamento";
        public static final String AIUTO = "Aiuto";
    }

    private long id;
    private String destinatario;
    private String categoria;
    private String mittente;
    private String testo;
    private boolean letta;
    private String data;

    /**
     * Crea una notifica vuota
     */
    public Notifica() {
    }

    /**
     * Crea una notifica inizializzandola con i valori passati come parametro
     * @param id id della notifica
     * @param destinatario username del destinatario della notifica
     * @param categoria tipo della notifica, indicato usando la classe Categoria
     * @param mittente username del mittente
     * @param testo messaggio della notifica
     * @param letta valore indicante se la notifica è stata letta. 0 se non è stata letta, 1 altrimenti.
     * @param data data di creazione della notifica
     */
    public Notifica(long id, String destinatario, String categoria, String mittente, String testo, boolean letta, String data) {
        this.id = id;
        this.destinatario = destinatario;
        this.mittente = mittente;
        this.testo = testo;
        this.letta = letta;
        this.categoria = categoria;
        this.data = data;
    }

    /**
     * Restituisce l'id della notifica
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setta un nuovo id alla notifica
     * @param id nuovo id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Restituisce l'username del destinatario della notifica
     * @return username del destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Setta un nuovo destinatario alla notifica
     * @param destinatario nuovo destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Restituisce l'username del mittente della notifica
     * @return username del mittente
     */
    public String getMittente() {
        return mittente;
    }

    /**
     * Setta un nuovo mittente alla notifica
     * @param mittente nuovo mittente
     */
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    /**
     * Restituisce il messaggio della notifica
     * @return messaggio
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Setta un nuovo messaggio alla notifica
     * @param testo nuovo messaggio
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * Restituisce un booleano rappresentante lo stato della notifica
     * @return 0 se non è stata letta, 1 altrimenti
     */
    public boolean isLetta() {
        return this.letta;
    }

    /**
     * Setta un nuovo stato alla notifica
     * @param letta nuovo stato, 0 se non è stata letta, 1 altrimenti
     */
    public void setLetta(boolean letta) {
        this.letta = letta;
    }

    /**
     * Restituisce la categoria della notifica
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setta una nuova categoria alla notifica
     * @param categoria nuova categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Restituisce la data di emissione della notifica
     * @return data di emissione
     */
    public String getData() {
        return data;
    }

    /**
     * Setta una nuova data di emissione alla notifica
     * @param data nuova data
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Notifica{" +
                "id=" + id +
                ", destinatario='" + destinatario + '\'' +
                ", categoria='" + categoria + '\'' +
                ", mittente='" + mittente + '\'' +
                ", testo='" + testo + '\'' +
                ", letta=" + letta +
                ", data='" + data + '\'' +
                '}';
    }
}
