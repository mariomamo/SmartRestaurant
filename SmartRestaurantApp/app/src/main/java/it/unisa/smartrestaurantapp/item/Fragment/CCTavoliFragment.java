package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.ListItem;
import it.unisa.smartrestaurantapp.item.VisualizzatoreTavoliAstratto;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;
import it.unisa.smartrestaurantapp.service.TavoloService;

public class CCTavoliFragment extends Fragment {
    private VisualizzatoreTavoliAstratto vis;
    private ListView list_tavoli;
    private View oldTablePressed;
    public TavoloService tavoloService = new TavoloService();
    private CustomAdapter adapter;
    private ArrayList<Tavolo> tavoli;

    private SmartRestaurantDispatcher receiver;
    private String uniqueID = UUID.randomUUID().toString();

    /**
     * Costruisce un frammento per la visualizzazione dei tavoli
     * Il costruttore prende in input l'interfaccia VisalizzatoreTavoliAstratto.
     * Questa interfaccia serve per specificare il criterio con il quale
     * verranno prelevati i tavoli da visualizzare e le operazioni da
     * eseguire nell'interfaccia quando un valore cambia
     * @param v interfaccia
     */
    public CCTavoliFragment(VisualizzatoreTavoliAstratto v) {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/salaServerToTablet");
        this.vis = v;
        if(vis.isOccupato() == 0)
            tavoloService.requestTavoliLiberi(uniqueID);
        else if(vis.isOccupato() == 1)
            tavoloService.requestTavoliOccupati(uniqueID);
        else
            tavoloService.requestTavoliPerPagamento(uniqueID);
    }

    /**
     * Crea la view ed inserisce all'interno i relativi tavoli
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.cc_view_layout_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                list_tavoli = v.findViewById(R.id.list_tavoli);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String tavoliJson = message.getStringProperty("tavoli");
                            Log.d("CCameriere", "[" + getClass().getSimpleName() + "] Ho ricevuto: " + ((TextMessage) message).getText());

                            Gson gson = new Gson();
                            tavoli = gson.fromJson(tavoliJson, new TypeToken<List<Tavolo>>() {
                            }.getType());

                            if (tavoli.size() > 0) {
                                final ArrayList<ListItem> elementi = new ArrayList<>();

                                for (Tavolo t : tavoli) {
                                    elementi.add(new ListItem(t.getNome(), "null"));
                                }

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
                                                Tavolo t = new Tavolo();

                                                for (Tavolo tavolo : tavoli) {
                                                    if (tavolo.getNome().equalsIgnoreCase(nomeTavolo))
                                                        t = tavolo;
                                                }

                                                //Riempio i campi delle textView
                                                vis.cambiaDati(t);
                                            }
                                        });

                                        //Aggiungo il frammento
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.add(R.id.ll_info, (Fragment) vis, CCPagamentoListInfoFragment.TAG);
                                        ft.commit();

                                        //Clicco il primo elemento della lista
                                        list_tavoli.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                oldTablePressed = adapter.getTargetView();
                                                //Simula il click sull'item
                                                list_tavoli.performItemClick(
                                                        list_tavoli.getAdapter().getView(0, oldTablePressed, null),
                                                        0,
                                                        list_tavoli.getAdapter().getItemId(0));
                                            }
                                        });
                                    }
                                });
                            }
                        } catch(JMSException e){
                            e.printStackTrace();
                        }
                    }
                }, "UUID = '" + uniqueID + "'");
            }
        }).start();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
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
        if(view != null) {
            //Cambio il colore di sfondo
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.secondaryColor));
            //Ottengo il riferimento al bottone tramite la view oldPressed
            TextView btn_testo = target.findViewById(R.id.btn_name);
            //Modifico la grafica del bottone
            btn_testo.setTextColor(Color.WHITE);
            btn_testo.setTypeface(null, Typeface.BOLD);
        }
    }

    /***
     * Modifica la grafica di un bottone rendendolo 'non cliccato'
     * @param view view cliccata
     * @param target target
     */
    public void setItemNotClicked(View view, View target) {
        //Cambio il colore di sfondo
        target.setBackgroundColor(android.R.drawable.btn_default);
        //Ottengo il riferimento al bottone tramite la view oldPressed
        TextView btn_testo = target.findViewById(R.id.btn_name);
        //Modifico la grafica del bottone
        btn_testo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.primaryTextColor));
        btn_testo.setTypeface(null, Typeface.NORMAL);
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
