package it.unisa.smartrestaurantapp.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import it.unisa.smartrestaurantapp.entity.Account;
import it.unisa.smartrestaurantapp.entity.Comanda;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

/**
 * Classe che si occupa della gestione dell'account
 */
public class LoginService {
    private LoginServerListener listener;
    private SmartRestaurantDispatcher sender;
    private SmartRestaurantDispatcher receiver;

    /**
     * Crea un login service per effettuare il login
     * @param listener classe listener
     */
    public LoginService(LoginServerListener listener) {
        sender = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/loginTabletToServer");
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/topic/loginServerToTablet");

        this.listener = listener;
    }

    /**
     * Metodo che verifica la presenza dell'account all'interno del database
     * @param username l'username dell'account
     * @param password la password dell'account
     * @return Account corrispondente all'username e la password se esistono all'iterno del database, null altrimenti
     */
    public void checkLogin(final String username, String password) {
        String uniqueID = UUID.randomUUID().toString();

        receiver.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    String tipo = message.getStringProperty("tipo");
                    String nome = message.getStringProperty("nome");
                    String password = message.getStringProperty("password");

                    Log.d("MY-DEBUG-LOGIN", tipo + ", " + nome + ", " + password);

                    if (nome == null) {
                        sender.disconnessione();
                        receiver.unsubscribe();
                        listener.loginError("Credenziali errate!");
                    } else if (tipo == null) {
                        Tavolo tavolo = new Tavolo();
                        tavolo.setNome(message.getStringProperty("nome"));
                        tavolo.setUsername(username);
                        tavolo.setPassword(password);
                        String comanda = message.getStringProperty("id_comanda");

                        if (!comanda.equals("null")) {
                            String comandaJson = message.getStringProperty("comanda");
                            Comanda c = new Gson().fromJson(comandaJson, Comanda.class);
                            tavolo.setComanda(c);
                        }

                        listener.login(tavolo);
                        sender.disconnessione();
                        receiver.unsubscribe();
                    } else {
                        Account account = new Account();
                        account.setNome(message.getStringProperty("nome"));
                        account.setTipo(Integer.parseInt(message.getStringProperty("tipo")));
                        account.setUsername(username);
                        Log.d("MY-DEBUG", "[PARAM]: " + account.getNome() + ", " + account.getTipo() + ", " + account.getUsername());
                        sender.disconnessione();
                        receiver.unsubscribe();
                        listener.login(account);
                    }

                } catch (JMSException e) {
                    e.printStackTrace();
                    listener.loginError(e.getMessage());
                } finally {
                    sender.disconnessione();
                    receiver.unsubscribe();
                }
            }
        }, "UUID = '" + uniqueID + "'");

        HashMap<String, String> properties = new HashMap<>();
        properties.put("UUID", uniqueID);
        properties.put("username", username);
        properties.put("password", password);

        sender.sendMessage("", properties);
    }
}