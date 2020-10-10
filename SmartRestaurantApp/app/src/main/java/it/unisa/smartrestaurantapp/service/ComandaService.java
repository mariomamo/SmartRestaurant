package it.unisa.smartrestaurantapp.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import it.unisa.smartrestaurantapp.activity.ECActivity;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Fragment.ECOrderFragment;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class ComandaService {
    public static ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>(){{
        add(new Tavolo("Tavolo1", "pass", "nome", 4, true, false));
        add(new Tavolo("Tavolo2", "pass", "nome", 4, true, false));
        add(new Tavolo("Tavolo3", "pass", "nome", 4, true, false));
        add(new Tavolo("Tavolo4", "pass", "nome", 4, true, false));
    }};

    private static SmartRestaurantDispatcher sender;

    public ComandaService() {
        sender = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/cucinaTabletToServer");
    }

    public static void segnaComeFatto(PiattoOrdinato po, String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.COMANDA_COMPLETATA);
        properties.put("UUID", UUID);
        properties.put("id_piatto_ordinato", po.getId() + "");
        sender.sendMessage("", properties);
    }

    public static void segnaComeNonFatto(PiattoOrdinato po, String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.COMANDA_RIMOSSA);
        properties.put("UUID", UUID);
        properties.put("id_piatto_ordinato", po.getId() + "");
        sender.sendMessage("", properties);
    }

    public void requestAllComande(String UUID) {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("action", DbManager.COMANDE_GET_ALL);
        properties.put("UUID", UUID);
        sender.sendMessage("", properties);
    }
}
