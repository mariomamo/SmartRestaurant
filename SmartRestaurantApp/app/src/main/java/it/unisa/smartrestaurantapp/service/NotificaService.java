package it.unisa.smartrestaurantapp.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

/**
 * Classe che si occupa della gestione dell'account
 */
public class NotificaService {
    private SmartRestaurantDispatcher sender;
    private Gson gson = new Gson();

    /**
     * Crea un account service
     */
    public NotificaService() {
        sender = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/notificaTabletToServer");
    }

    /**
     * Invia una richiesta di notifiche al server
     * @param UUID stringa univoca
     * @param first indice della prima notifica che si vuole ricevere
     * @param last indice dell'ultima notifica che si vuole ricevere
     * @param destinatario username del destinatario
     */
    public void requestNotifiche(String UUID, String first, String last, String destinatario) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.NOTIFICA_RECEIVE);
        properties.put("UUID", UUID);
        properties.put("first", first);
        properties.put("last", last);
        properties.put("username", destinatario);
        sender.sendMessage("", properties);
    }

    /**
     * Invia una notifica al server
     * @param UUID stringa univoca
     * @param notifica notifica da inviare
     */
    public void sendNotifica(String UUID, Notifica notifica) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.NOTIFICA_SEND);
        properties.put("UUID", UUID);
        String gsonNotifica = gson.toJson(notifica);
        properties.put("notifica", gsonNotifica);
        sender.sendMessage("", properties);

        Log.d("NotificaService", "Ho inviato la notifica " + notifica.getTesto());
    }
}