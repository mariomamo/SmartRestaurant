package it.unisa.smartrestaurantapp.item.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.CustomAdapterCustomAdapter;
import it.unisa.smartrestaurantapp.item.CustomAdapterFragment;
import it.unisa.smartrestaurantapp.service.ComandaService;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class ECOrderFragment extends Fragment {
    private GridLayout gvOrdini;
    public static Tavolo t;
    private ArrayList<Tavolo> tavoli = new ArrayList<>();
    private ComandaService comandaService = new ComandaService();
    private SmartRestaurantDispatcher receiver;
    private String uniqueID = UUID.randomUUID().toString();
    private CustomAdapterCustomAdapter ca;
    private static final int MAX_COMANDE = 4;
    private int inizio = 0;
    private int fine = MAX_COMANDE;
    private HashMap<Integer, Fragment> frammenti;
    private Button bt_dietro;
    private Button bt_avanti;

    public ECOrderFragment() {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/cucinaServerToTablet");
        comandaService.requestAllComande(uniqueID);
        frammenti = new HashMap<>();
        Log.d("MY-DEBUG-EC", "UUID: " + uniqueID);
    }

    /**
     * Crea la view ed inserisce all'interno gli ordini dei tavoli
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.ec_view_layout_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
//                //Setto il custom adapter che mi fa visualizzare il nome del tavolo e la list view con i suoi ordini
//                CustomAdapterCustomAdapter ca = new CustomAdapterCustomAdapter(v.getContext(), R.layout.list_item_listview, ComandaService.tavoli);
//                gvOrdini.setAdapter(ca);

                Log.d("DEBUG-ECO-FRAGMENT", "CHIAMATO");
                gvOrdini = v.findViewById(R.id.gv_ordini);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {

                        try {
                            String tavoliJson = message.getStringProperty("tavoli");
//                            Log.d("MY-DEBUG-EC", "HO RICEVUTO: " + message.getStringProperty("UUID"));
//                            Log.d("MY-DEBUG-EC", "HO RICEVUTO: " + tavoliJson);

                            Gson gson = new Gson();
                            tavoli = gson.fromJson(tavoliJson, new TypeToken<List<Tavolo>>(){}.getType());
//                            Log.d("MY-DEBUG-EC", "[ELEMENTO]: " + tavoli);

//                            for (Tavolo t : tavoli) {
//                                Log.d("MY-DEBUG-EC", "[ELEMENTO]: " + t.getNome());
//                            }

                            loadComande();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }, "UUID = '" + uniqueID + "'");

                bt_dietro = v.findViewById(R.id.bt_dietro);
                bt_avanti = v.findViewById(R.id.bt_avanti);

                bt_avanti.post(new Runnable() {
                    @Override
                    public void run() {
                        bt_dietro.setEnabled(false);
                    }
                });

                bt_dietro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comandeDietro();
                    }
                });

                bt_avanti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comandeAvanti();
                    }
                });
            }
        }).start();

        return v;
    }

    public void comandeAvanti() {
        inizio = fine;
        fine += MAX_COMANDE;
        loadComande();
    }

    public void comandeDietro() {
        fine = inizio;
        inizio -= MAX_COMANDE;
        loadComande();
    }

    public void loadComande() {
        gvOrdini.post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                rimuoviFrammenti(fm);

                //Log.d("MY-DEBUG-COMANDE", "inizio: " + inizio + " fine: " + fine + " tavoli size: " + tavoli.size());

                if (inizio == 0) {
                    bt_dietro.setEnabled(false);
                } else {
                    bt_dietro.setEnabled(true);
                }
                if (fine >= tavoli.size()) {
                    bt_avanti.setEnabled(false);
                } else {
                    bt_avanti.setEnabled(true);
                }
                for (int i = inizio; i < tavoli.size() && i < fine; i++) {
                    Tavolo t = tavoli.get(i);
                    CustomAdapterFragment f = new CustomAdapterFragment(t);
                    ft.add(R.id.gv_ordini, f);
                    frammenti.put(i, f);
                }

                ft.commit();
            }
        });
    }

    public void rimuoviFrammenti(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        for (int valore : frammenti.keySet()) {
            Fragment f = frammenti.get(valore);
            ft.remove(f);
        }

        frammenti.clear();

        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onStop() {
        super.onStop();
        receiver.unsubscribe();
        receiver.disconnessione();
    }
}