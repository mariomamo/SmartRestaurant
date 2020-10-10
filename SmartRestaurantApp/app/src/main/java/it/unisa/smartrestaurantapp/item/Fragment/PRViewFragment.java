package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.ListItem;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;
import it.unisa.smartrestaurantapp.service.TavoloService;

public class PRViewFragment extends Fragment {
    private View oldTablePressed;
    private CustomAdapter adapter;
    private ListView list_tavoli;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etNome;
    private EditText etPosti;
    private Switch swStato;
    private Button salva;
    private Button elimina;
    private Switch sw_stato;
    private SmartRestaurantDispatcher salaServerToTablet;
    private static SmartRestaurantDispatcher receiver;
    private TavoloService tavoloService = new TavoloService();
    private String uniqueID = UUID.randomUUID().toString();
    private ArrayList<Tavolo> tavoli = new ArrayList<>();

    /**
     * Crea un frammento per la visualizzazione dei tavoli presenti nella sala
     */
    public PRViewFragment() {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/salaServerToTablet");
        tavoloService.requestAllTavoli(uniqueID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salaServerToTablet = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/salaServerToTablet");
        tavoli = new ArrayList<>();
    }

    /**
     * Crea la view contente i tavoli
     * @param inflater inflater
     * @param container container dove inserire il frammento
     * @param savedInstanceState dati salvati
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.pr_view_layout_fragment, container, false);

        etUsername = v.findViewById(R.id.username);
        etPassword = v.findViewById(R.id.password);
        etNome = v.findViewById(R.id.nome);
        etPosti = v.findViewById(R.id.posti);
        swStato = v.findViewById(R.id.stato);
        salva = v.findViewById(R.id.salva);
        elimina = v.findViewById(R.id.elimina);
        sw_stato = v.findViewById(R.id.stato);

        sw_stato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(v);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                list_tavoli = v.findViewById(R.id.list_tavoli);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String tavoliJson = message.getStringProperty("tavoli");
                            Log.d("Proprietario", "[" + getClass().getSimpleName() + "] Ho ricevuto: " + ((TextMessage) message).getText());

                            Gson gson = new Gson();
                            tavoli = gson.fromJson(tavoliJson, new TypeToken<List<Tavolo>>(){}.getType());

                            final ArrayList<ListItem> elementi = new ArrayList<>();

                            for(Tavolo t: tavoli) {
                                elementi.add(new ListItem(t.getNome(), "null"));
                            }

                            //Aggiungo il pulsante '+' per aggiungere un nuovo tavolo
                            elementi.add(new ListItem("+", "null"));

                            list_tavoli.post(new Runnable() {
                                public void run() {
                                    adapter = new CustomAdapter(v.getContext(), R.layout.list_item, elementi, 0);
                                    list_tavoli.setAdapter(adapter);

                                    list_tavoli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            //Se c'era già un pulsante cliccato lo riporto nello stato iniziale
                                            if (oldTablePressed != null) {
                                                setItemNotClicked(view, oldTablePressed);
                                            }

                                            //Salvo il nuovo pulsante che è stato premuto
                                            oldTablePressed = view;
                                            setItemClicked(view, oldTablePressed);

                                            //======================= CARICAMENTO DATI TAVOLO =======================
                                            TextView btn_testo = oldTablePressed.findViewById(R.id.btn_name);
                                            String nomeTavolo = btn_testo.getText().toString();

                                            //Ottengo il riferimento al tavolo tramite il nome del pulsante nella lista
                                            Tavolo t = null;

                                            for(Tavolo tavolo: tavoli) {
                                                if(tavolo.getNome().equalsIgnoreCase(nomeTavolo))
                                                    t = tavolo;
                                            }

                                            cambiaDatiTavolo(t);
                                            check(null);
                                        }
                                    });
                                }
                            });
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }, "UUID = '" + uniqueID + "'");
            }
        }).start();

        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tavolo t = new Tavolo();
                String temp = etUsername.getText().toString();

                if (temp != null && temp.length() > 0 && temp.length() <= 50 && temp.matches("[a-zA-Z0-9]*"))
                    t.setUsername(temp);
                else {
                    Toast.makeText(v.getContext(), "Username non rispetta il formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                temp = etPassword.getText().toString();

                if (temp != null && temp.length() > 2 && temp.length() <= 50 && temp.matches("[a-zA-Z0-9]*")) {
                    t.setPassword(temp);
                } else {
                    Toast.makeText(v.getContext(), "Password non rispetta il formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                temp = etNome.getText().toString();

                if (temp != null && temp.length() > 0 && temp.length() <= 70 && temp.matches("[a-zA-Z0-9 ]*")) {
                    t.setNome(temp);
                } else {
                    Toast.makeText(v.getContext(), "Nome non rispetta il formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                temp = etPosti.getText().toString();

                if (temp != null && temp.length() > 0 && Integer.valueOf((temp)) > 0 && Integer.valueOf((temp)) <= 999) {
                    t.setPosti(Integer.valueOf((temp)));
                } else {
                    Toast.makeText(v.getContext(), "Posti non rispetta il formato", Toast.LENGTH_SHORT).show();
                    return;
                }

                t.setLibero(swStato.isChecked());
                t.setVuolePagare(false);

                tavoloService.sendNewTavolo(t);
                tavoloService.requestAllTavoli(uniqueID);
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tavolo t = new Tavolo();
                String temp = etUsername.getText().toString();
                t.setUsername(temp);

                tavoloService.sendRemove(t);
                tavoloService.requestAllTavoli(uniqueID);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /***
     * Modifica la grafica di un bottone rendendolo 'cliccato'
     * @param view view cliccata
     * @param target target
     */
    public void setItemClicked(View view, View target) {
        //Cambio il colore di sfondo
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.secondaryColor));
        //Ottengo il riferimento al bottone tramite la view oldPressed
        TextView btn_testo = target.findViewById(R.id.btn_name);
        //Modifico la grafica del bottone
        btn_testo.setTextColor(Color.WHITE);
        btn_testo.setTypeface(null, Typeface.BOLD);
    }

    /***
     * Modifica la grafica di un bottone rendendolo 'non cliccato'
     * @param view view cliccata
     * @param target target
     */
    public void setItemNotClicked(View view, View target) {
        //Cambio il colore di sfondo
        target.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primaryColor));
        //Ottengo il riferimento al bottone tramite la view oldPressed
        TextView btn_testo = target.findViewById(R.id.btn_name);
        //Modifico la grafica del bottone
        btn_testo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.primaryTextColor));
        btn_testo.setTypeface(null, Typeface.NORMAL);
    }

    /**
     * Aggiorna la view con i nuovi dati del tavolo
     * @param tavolo tavolo contenente i nuovi dati
     */
    public void cambiaDatiTavolo(Tavolo tavolo) {
        if(tavolo != null) {
            etUsername.setText(tavolo.getUsername());
            etPassword.setText(tavolo.getPassword());
            etNome.setText(tavolo.getNome());
            etPosti.setText("" + tavolo.getPosti());
            swStato.setChecked(tavolo.isLibero());

            etUsername.setEnabled(false);
        } else {
            etUsername.setText("");
            etPassword.setText("");
            etNome.setText("");
            etPosti.setText("");
            swStato.setChecked(false);

            etUsername.setEnabled(true);
        }
    }

    /**
     * Cambia il testo del check
     * @param v view cliccata
     */
    public void check(View v) {
        if(swStato.isChecked()) {
            swStato.setText("Occupato");
        } else {
            swStato.setText("Libero");
        }
    }
}
