package it.unisa.smartrestaurantapp.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class TavoloService {
    private SmartRestaurantDispatcher senderNotifica;
    private SmartRestaurantDispatcher senderSala;
    private Gson gson = new Gson();

    public TavoloService() {
        senderNotifica = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/notificaTabletToServer");
        senderSala = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/salaTabletToServer");
    }

    public void requestAllTavoli(String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_GET_ALL);
        properties.put("UUID", UUID);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta ottenere tutti i tavoli");
    }

    public void requestTavoliLiberi(String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_GET_ALL_FREE);
        properties.put("UUID", UUID);
        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per i tavoli liberi");
    }

    public void requestTavoliOccupati(String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_GET_ALL_OCCUPIED);
        properties.put("UUID", UUID);
        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per i tavoli occupati");
    }

    public void requestTavoliPerPagamento(String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_GET_ALL_WANT_PAY);
        properties.put("UUID", UUID);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta ottenere i tavoli che vogliono pagare");
    }

    public void requestTavolo(String UUID, String username) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_GET_BY_USERNAME);
        properties.put("username", username);
        properties.put("UUID", UUID);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta ottenere un tavolo");
    }

    public void setPagato(Tavolo tavolo, String UUID) {
        Log.d("MY-DEBUG-TAVOLO", UUID);
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_CHANDE_PAYED);
        properties.put("username", tavolo.getUsername());
        properties.put("UUID", UUID);
        senderSala.sendMessage("", properties);
    }

    public void sendNewTavolo(Tavolo tavolo) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_NEW);
        String tavoloGson = gson.toJson(tavolo);
        properties.put("tavolo", tavoloGson);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per creare un nuovo tavolo");
    }

    public void sendChangeStateTavolo(Tavolo tavolo) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_CHANGE_STATE);
        String tavoloGson = gson.toJson(tavolo);
        properties.put("tavolo", tavoloGson);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per cambiare stato al tavolo");
    }

    public void sendChangePayTavolo(Tavolo tavolo) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_CHANGE_PAY);
        String tavoloGson = gson.toJson(tavolo);
        properties.put("tavolo", tavoloGson);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per cambiare stato del pagamento al tavolo");
    }

    public void sendRemove(Tavolo tavolo) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.TAVOLO_REMOVE);
        String tavoloGson = gson.toJson(tavolo);
        properties.put("tavolo", tavoloGson);

        senderSala.sendMessage("", properties);

        Log.d("TavoloService", "Ho inviato la richiesta per rimuovere un tavolo");
    }
}
