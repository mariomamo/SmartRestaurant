package netbeanskaazing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.*;
import java.text.SimpleDateFormat;
import query.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

/**
 * Si occupa di creare e gestire gli eventi per lo scambio di dati tra client e server
 * @author Syrenne
 */
public class Starter {
    public static final String IP_MARIO_EHT = "192.168.1.153";
    public static final String IP_MARIO_WIFI = "192.168.1.148";
    public static final String IP_MARIO_UNI = "172.19.242.240";
    public static final String IP_MARIO_2 = "192.168.1.67";
    public static final String IP_VALERIA = "192.168.178.28";
    
    public static final String IP = IP_MARIO_UNI;
    public static final String PORTA = "8001";
    
    private static SmartRestaurantDispatcher loginTabletToServer;
    private static SmartRestaurantDispatcher loginServerToTablet;
    private static SmartRestaurantDispatcher cucinaTabletToServer;
    private static SmartRestaurantDispatcher cucinaServerToTablet;
    private static SmartRestaurantDispatcher menuTabletToServer;
    private static SmartRestaurantDispatcher menuServerToTablet;
    private static SmartRestaurantDispatcher carrelloServerToTablet;
    private static SmartRestaurantDispatcher notificaTabletToServer;
    private static SmartRestaurantDispatcher notificaServerToTablet;
    private static SmartRestaurantDispatcher salaTabletToServer;
    private static SmartRestaurantDispatcher salaServerToTablet;
    
    public static void main (String[] args) throws JMSException, NamingException {
        loginTabletToServer = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/loginTabletToServer");
        loginServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/topic/loginServerToTablet");
        
        /**
         * Si occupa del login da parte degli account e dei tavoli
         * Ricerca nel database le credenziali e, se esiste, ne restituisce l'account o il tavolo
         */
        loginTabletToServer.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    String mess = ((TextMessage) msg).getText();
                    String username = msg.getStringProperty("username");
                    String password = msg.getStringProperty("password");
                    
                    System.out.println(">> Tentativo di login");
                    System.out.println("Username : " + username + ", password : " + password);
                    
                    AccountQuery accountQuery = new AccountQuery();
                    Account account = accountQuery.login(new Account(username, password, "", 0));
                    
                    HashMap<String, String> properties = new HashMap<>();
                    String uniqueId = msg.getStringProperty("UUID");
                    
                    if (account != null) {
                        System.out.println(">> Login effettuato con successo per un account!");

                        properties.put("UUID", uniqueId);
                        properties.put("nome", account.getNome());
                        properties.put("tipo", account.getTipo() + "");
                        
                        loginServerToTablet.sendMessage("", properties);
                    } else {
                        TavoloQuery tavoloQuery = new TavoloQuery();
                        Tavolo tavolo = tavoloQuery.login(new Tavolo(username, password, "", 0, false, false));
                        String comandaJson;
                        Gson gson = new Gson();
                        Comanda comanda;
                        ComandaQuery comandaQuery = new ComandaQuery();
                        
                        if (tavolo != null) {
                            System.out.println(">> Login effettuato con successo per un tavolo!");
                            int id_comanda;
                            
                            if (tavolo.isLibero()) {
                                System.out.println("LIBERO");
                                tavolo.setLibero(false);
                                comanda = new Comanda();
                                comanda.setStato(false);
                                comanda.setData(getHour());
                                tavolo.setComanda(comanda);
                                comandaQuery.insert(tavolo);
                                tavoloQuery.update(tavolo);
                            }
                            
                            comanda = comandaQuery.findByUsername(tavolo.getUsername());
                            if (comanda != null) {
                                PiattoOrdinatoQuery poq = new PiattoOrdinatoQuery();
                                ArrayList<PiattoOrdinato> piattiOrdinati = (ArrayList<PiattoOrdinato>) poq.findByComanda(comanda);
                                if (piattiOrdinati != null) {
                                    comanda.setPiattiOrdinati(piattiOrdinati);
                                }
                            } else {
                                comanda = new Comanda();
                                comanda.setStato(false);
                                comanda.setData(getHour());
                                tavolo.setComanda(comanda);
                                comandaQuery.insert(tavolo);
                                comanda = comandaQuery.findByUsername(tavolo.getUsername());
                            }
                            
                            tavolo.setComanda(comanda);
                            comandaJson = gson.toJson(comanda);
                            System.out.println(comandaJson);
                            id_comanda = comanda.getId();
                           
                            properties.put("UUID", uniqueId);
                            properties.put("nome", tavolo.getNome());
                            properties.put("password", password);
                            properties.put("id_comanda", id_comanda + "");
                            properties.put("comanda", comandaJson);
                            loginServerToTablet.sendMessage("", properties);
                        } else {
                            System.out.println(">> Login errato");
                            properties.put("UUID", uniqueId);
                            loginServerToTablet.sendMessage("", properties);
                        }
                    }
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
       });
        
        cucinaTabletToServer = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/cucinaTabletToServer");
        cucinaServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/cucinaServerToTablet");
        
        /**
         * Si occupa di fornire tutte le comande alla cucina
         */
        cucinaTabletToServer.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    String azione = msg.getStringProperty("action");
                    String uniqueId = msg.getStringProperty("UUID");
                    System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                    TavoloQuery tavoloQuery = new TavoloQuery();
                    int id_piatto_ordinato;
                    PiattoOrdinatoQuery poq = null;
                    PiattoOrdinato piatto = null;
                    ComandaQuery cmq = new ComandaQuery();
                    
                    switch (azione) {
                        case DbManager.COMANDE_GET_ALL:
                            List<Tavolo> tavoli = tavoloQuery.findAllComanda();
                            Gson gson = new Gson();
                            String json = gson.toJson(tavoli);

                            HashMap<String, String> properties = new HashMap<>();
                            properties.put("UUID", uniqueId);
                            properties.put("tavoli", json);

                            cucinaServerToTablet.sendMessage("Comande", properties);
                            break;
                        case DbManager.COMANDA_COMPLETATA:
                            id_piatto_ordinato = Integer.parseInt(msg.getStringProperty("id_piatto_ordinato"));
                            poq = new PiattoOrdinatoQuery();
                            piatto = poq.findById(id_piatto_ordinato);
                            piatto.setStato(true);
                            
                            System.out.println("ID: " + piatto.getId() + ", COMANDA: " + piatto.getComanda());
                            poq.update(piatto, piatto.getComanda());
                            break;
                        case DbManager.COMANDA_RIMOSSA:
                            id_piatto_ordinato = Integer.parseInt(msg.getStringProperty("id_piatto_ordinato"));
                            System.out.println("ID: " + id_piatto_ordinato);
                            poq = new PiattoOrdinatoQuery();
                            piatto = poq.findById(id_piatto_ordinato);
                            if (piatto != null)
                                poq.delete(piatto);
                            break;
                        default: break;
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        menuTabletToServer = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/menuTabletToServer");
        menuServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/menuServerToTablet");
        carrelloServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/carrelloServerToTablet");
        
        /**
         * Rende possibile il recupero di tutti i piatti presenti nel menu e la loro ordinazione
         */
        menuTabletToServer.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    String azione = msg.getStringProperty("action");
                    String uniqueId = msg.getStringProperty("UUID");
                    Gson gson = new Gson();
                    HashMap<String, String> properties = new HashMap<>();
                    
                    switch (azione) {
                        case DbManager.PIATTI_GET_ALL:
                            String type = msg.getStringProperty("type");
                            
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            PiattoQuery piattoQuery = new PiattoQuery();
                            Piatto p = new Piatto();
                            p.setCategoria(type);
                            List<Piatto> piatti = piattoQuery.findByCategoria(p, 0, 100);
                            String json = gson.toJson(piatti);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("piatti", json);
                            
                            menuServerToTablet.sendMessage("Piatti del menu", properties);
                            
                            System.out.println("Invio effettuato");
                            
                            break;
                        case DbManager.INSERT_ORDINE:
                            String username = msg.getStringProperty("username");
                            String ordineJson = msg.getStringProperty("ordine");
                            String id_comanda = msg.getStringProperty("comanda");
                            
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            int id;
                            if (id_comanda.equals("null")) {
                                id = -1;
                                TavoloQuery tavoloQuery = new TavoloQuery();
                                Tavolo tavolo = tavoloQuery.findByUsername(username);
                                id = tavolo.getComanda().getId();
                            } else {
                                id = Integer.parseInt(id_comanda);
                            }
                            
                            System.out.println("INSERISCO L'ORDINE PER IL TAVOLO: " + username + ", id comanda: " + id);
                            
                            ArrayList<PiattoOrdinato> carrello = gson.fromJson(ordineJson, new TypeToken<ArrayList<PiattoOrdinato>>(){}.getType());
                            PiattoOrdinatoQuery poq = new PiattoOrdinatoQuery();
                            for (PiattoOrdinato po : carrello) {
                                poq.insert(po, id);
                            }
                            
                            properties.put("comanda", id + "");
                            carrelloServerToTablet.sendMessage("Ordine ricevuto", properties);
                        default: break;
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        notificaTabletToServer = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/notificaTabletToServer");
        notificaServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/topic/notificaServerToTablet");
        
        /**
         * Gestisce il meccanismo dell'invio e ricezione delle notifiche
         */
        notificaTabletToServer.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    Gson gson = new Gson();
                    String uniqueId = msg.getStringProperty("UUID");
                    
                    String mess = ((TextMessage) msg).getText();
                    String action = msg.getStringProperty("action");
                    
                    if(action.equals(DbManager.NOTIFICA_SEND)) {
                        System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                        
                        String notificaJson = msg.getStringProperty("notifica");
                        Notifica notifica = gson.fromJson(notificaJson, Notifica.class);
                        NotificaQuery notificaQuery = new NotificaQuery();
                        Notifica new_notifica = notificaQuery.findById(notifica);
                        
                        if(new_notifica == null) {
                            notificaQuery.insert(notifica);

                            HashMap<String, String> properties = new HashMap<>();
                            properties.put("UUID", uniqueId);
                            properties.put("notifica", notificaJson);

                            notificaServerToTablet.sendMessage("Notifica", properties);
                            
                            System.out.println("Notifica inviata");
                        } else {
                            notificaQuery.update(notifica);
                        }
                    } else if(action.equals(DbManager.NOTIFICA_RECEIVE)) {
                        System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                        
                        String username = msg.getStringProperty("username");
                        int first = Integer.parseInt(msg.getStringProperty("first"));
                        int last = Integer.parseInt(msg.getStringProperty("last"));
                        List<Notifica> list;
                        NotificaQuery notificaQuery = new NotificaQuery();
                        Notifica notifica = new Notifica();
                        notifica.setDestinatario(username);
                        list = notificaQuery.findAll(notifica, first, last);
                        String json = gson.toJson(list);
                        HashMap<String, String> properties = new HashMap<>();
                        properties.put("UUID", uniqueId);
                        properties.put("notifica", json);

                        notificaServerToTablet.sendMessage("Notifiche", properties);

                        System.out.println("Invio effettuato");
                    }
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
       });
        
        salaTabletToServer = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/salaTabletToServer");
        salaServerToTablet = new SmartRestaurantDispatcher("ws://" + IP + ":" + PORTA + "/jms", "/queue/salaServerToTablet");
        
        /**
         * Gestisce tutte le operazioni possibili con i tavoli presenti nella sala (inserimento, modifiche, cancellazione e recupero in base ad un filtro)
         */
        salaTabletToServer.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                try {
                    String azione = msg.getStringProperty("action");
                    String uniqueId = msg.getStringProperty("UUID");
                    Gson gson = new Gson();
                    HashMap<String, String> properties = new HashMap<>();
                    TavoloQuery tavoloQuery = new TavoloQuery();
                    List<Tavolo> tavoli;
                    Tavolo tavolo;
                    String json;
                    
                    switch (azione) {
                        case DbManager.TAVOLO_GET_ALL_FREE:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            tavoli = tavoloQuery.findAllFree();
                            json = gson.toJson(tavoli);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("tavoli", json);
                            
                            salaServerToTablet.sendMessage("Tavoli liberi", properties);
                            
                            System.out.println("Invio effettuato");
                            break;
                        case DbManager.TAVOLO_GET_ALL_OCCUPIED:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            tavoli = tavoloQuery.findAllOccupied();
                            json = gson.toJson(tavoli);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("tavoli", json);
                            
                            salaServerToTablet.sendMessage("Tavoli liberi", properties);
                            
                            System.out.println("Invio effettuato");
                            break;
                        case DbManager.TAVOLO_GET_ALL_WANT_PAY:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            tavoli = tavoloQuery.findAllWantPay();
                            json = gson.toJson(tavoli);
                            System.out.println(json);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("tavoli", json);
                            
                            salaServerToTablet.sendMessage("Tavoli liberi", properties);
                            
                            System.out.println("Invio effettuato");
                            break;
                        case DbManager.TAVOLO_GET_ALL:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            tavoli = tavoloQuery.findAll();
                            json = gson.toJson(tavoli);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("tavoli", json);
                            
                            salaServerToTablet.sendMessage("Tavoli liberi", properties);
                            
                            System.out.println("Invio effettuato");
                            break;
                        case DbManager.TAVOLO_GET_BY_USERNAME:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            String username = msg.getStringProperty("username");
                            tavolo = tavoloQuery.findByUsername(username);
                            json = gson.toJson(tavolo);
                            
                            properties.put("UUID", uniqueId);
                            properties.put("tavolo", json);
                            
                            salaServerToTablet.sendMessage("tavolo", properties);
                            
                            System.out.println("Invio effettuato");
                        case DbManager.TAVOLO_CHANGE_STATE:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            json = msg.getStringProperty("tavolo");
                            tavolo = gson.fromJson(json, Tavolo.class);
                            
                            //Creo una nuova comanda per il tavolo
                            ComandaQuery cmq = new ComandaQuery();
                            Comanda comanda = new Comanda();
                            comanda.setStato(false);
                            comanda.setData(getHour());
                            tavolo.setComanda(comanda);
                            tavolo.setLibero(false);
                            cmq.insert(tavolo);                            
                            tavoloQuery.updateStato(tavolo);
                            break;
                        case DbManager.TAVOLO_CHANGE_PAY:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            json = msg.getStringProperty("tavolo");
                            tavolo = gson.fromJson(json, Tavolo.class);
                            tavoloQuery.updateVuolePagare(tavolo);
                            break;
                        case DbManager.TAVOLO_NEW:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            json = msg.getStringProperty("tavolo");
                            tavolo = gson.fromJson(json, Tavolo.class);
                            
                            Tavolo temp_tavolo = tavoloQuery.findByUsername(tavolo.getUsername());
                            if(temp_tavolo == null) {
                                tavoloQuery.insert(tavolo);
                            } else
                                tavoloQuery.update(tavolo);
                            break;
                        case DbManager.TAVOLO_REMOVE:
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId);
                            
                            json = msg.getStringProperty("tavolo");
                            tavolo = gson.fromJson(json, Tavolo.class);
                            tavoloQuery.delete(tavolo);
                            break;
                        case DbManager.TAVOLO_CHANDE_PAYED:
                            username = msg.getStringProperty("username");
                            uniqueId = msg.getStringProperty("UUID");
                            System.out.println("Ho ricevuto: " + msg.getStringProperty("action") + ", UUID: " + uniqueId + ", username: " + username);
                            
                            tavolo = tavoloQuery.findByUsername(username);
                            tavolo.setLibero(true);
                            tavolo.setVuolePagare(false);
                            tavoloQuery.setPagato(tavolo);
                            break;
                        /*case DbManager.INSERT_ORDINE:
                            String username = msg.getStringProperty("username");
                            String ordineJson = msg.getStringProperty("ordine");
                            String id_comanda = msg.getStringProperty("comanda");
                            int id;
                            if (id_comanda.equals("null")) {
                                id = -1;
                                TavoloQuery tavoloQuery = new TavoloQuery();
                                Tavolo tavolo = tavoloQuery.findByUsername(username);
                                id = tavolo.getComanda().getId();
                            } else {
                                id = Integer.parseInt(id_comanda);
                            }
                            System.out.println("INSERISCO L'ORDINE PER IL TAVOLO: " + username + ", id comanda: " + id);
                            System.out.println(">> ordine: " + ordineJson);
                            
                            ArrayList<PiattoOrdinato> carrello = gson.fromJson(ordineJson, new TypeToken<ArrayList<PiattoOrdinato>>(){}.getType());
                            PiattoOrdinatoQuery poq = new PiattoOrdinatoQuery();
                            for (PiattoOrdinato po : carrello) {
                                System.out.println("[" + po.getPiatto().getId() + "] " + po.getPiatto().getNome() + ", " + po.getQuantita() + " pezzi");
                                poq.insert(po, id);
                            }
                            
                            properties.put("comanda", id + "");
                            salaServerToTablet.sendMessage("Ordine ricevuto!", properties);*/
                        default: break;
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public static String getHour() {        
        final Calendar c = Calendar.getInstance();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        String ora = timeFormat.format(c.getTime());
        String data = dateFormat.format(c.getTime());
        System.out.println(data + " " + ora);
        return data + " " + ora;
    }
}
