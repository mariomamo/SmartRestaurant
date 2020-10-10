package it.unisa.smartrestaurantapp.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.UUID;

import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

/**
 * Classe che si occupa della gestione dei piatti del menu
 */
public class PiattoService {
    private static SmartRestaurantDispatcher sender;

    /**
     * Crea un piatto service
     */
    public PiattoService() {
        sender = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/menuTabletToServer");
    }

    /**
     * Richiede i piatti contenuti nel menu al server
     * @param UUID stringa univoca per identificare la richiesta
     * @param type categoria dei piatti richiesta
     */
    public void requestPiatti(String UUID, String type) {
        Log.d("Tv", "[" + getClass().getSimpleName() + "] Invio richiesta di piatti al server per: " + type);

        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.PIATTI_GET_ALL);
        properties.put("type", type);
        properties.put("UUID", UUID);
        sender.sendMessage("", properties);
    }

    /**
     * Invia un ordine al server
     * @param tavolo tavolo contenente l'ordine
     */
    public static void sendOrdine(Tavolo tavolo) {
        Gson gson = new Gson();
        String ordine = gson.toJson(tavolo.getCarrello());

        tavolo.spostaDalCarrelloAllaComanda();

        HashMap<String, String> properties = new HashMap<>();
        properties.put("username", tavolo.getUsername());
        properties.put("UUID", UUID.randomUUID().toString());
        properties.put("action", DbManager.INSERT_ORDINE);
        properties.put("ordine", ordine);
        properties.put("comanda", tavolo.getComanda().getId() + "");

        sender.sendMessage("", properties);
    }
}