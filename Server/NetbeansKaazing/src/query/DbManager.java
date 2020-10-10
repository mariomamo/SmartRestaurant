package query;

public class DbManager {
    private static final String DB_KEY = "SmartRestaurantKey1234";
    public static final String COMANDE_GET_ALL = "Comanda.getAll";
    public static final String PIATTI_GET_ALL = "Piatto.getAll";
    public static final String INSERT_ORDINE = "Tavolo.insertOrdine";   
    public static final String NOTIFICA_SEND = "Notifica.send";
    public static final String NOTIFICA_RECEIVE = "Notifica.receive";
    public static final String TAVOLO_GET_ALL_FREE = "Tavolo.getAllFree";
    public static final String TAVOLO_GET_ALL_OCCUPIED = "Tavolo.getAllOccupied";
    public static final String TAVOLO_CHANGE_STATE = "Tavolo.changeState";
    public static final String TAVOLO_GET_ALL_WANT_PAY = "Tavolo.getAllWantPay";
    public static final String TAVOLO_GET_ALL = "Tavolo.getAll";
    public static final String TAVOLO_NEW = "Tavolo.new";
    public static final String TAVOLO_REMOVE = "Tavolo.remove";
    public static final String TAVOLO_CHANGE_PAY = "Tavolo.changePay";
    public static final String TAVOLO_GET_BY_USERNAME = "Tavolo.getByUsername";
    public static final String TAVOLO_CHANDE_PAYED = "Tavolo.getChangePayed";
    public static final String COMANDA_COMPLETATA = "Comanda.completata";
    public static final String COMANDA_RIMOSSA = "Comanda.rimossa";
    
    public static String getKey() {
        return DB_KEY;
    }
}
