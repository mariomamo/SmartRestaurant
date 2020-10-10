package it.unisa.smartrestaurantapp.server;

public class DbManager {
    public static final String IP_MARIO_EHT = "192.168.1.153";
    public static final String IP_MARIO_WIFI = "192.168.1.148";
    public static final String IP_MARIO_UNI = "172.19.242.240";
    public static final String IP_MARIO_2 = "192.168.1.67";
    public static final String IP_VALERIA = "192.168.178.28";
    public static final String IP = IP_MARIO_UNI;
    public static final String PORTA = "8001";
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

    public static String getIp() {
        return IP;
    }

    public static String getPorta()  {
        return PORTA;
    }

}
