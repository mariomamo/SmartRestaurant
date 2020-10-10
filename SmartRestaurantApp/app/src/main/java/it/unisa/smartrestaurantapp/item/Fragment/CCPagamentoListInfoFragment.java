package it.unisa.smartrestaurantapp.item.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Adapter.ListaOrdiniAdapter;
import it.unisa.smartrestaurantapp.item.VisualizzatoreTavoliAstratto;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;
import it.unisa.smartrestaurantapp.service.TavoloService;

/**
 * Questo frammento si occupa di visulizzare la grafica per la
 * visualizzazione di tutti i piatti ordinati da un tavolo
 */
public class CCPagamentoListInfoFragment extends Fragment implements VisualizzatoreTavoliAstratto {
    public static final String TAG = "CCPagamentoListInfo_TAG";
    private TextView tv_totale;
    private ListView lv_piatti;
    private TavoloService tavoloService;
    private Button btnAzione;
    private SmartRestaurantDispatcher receiver;
    private String uniqueID = UUID.randomUUID().toString();
    private PagamentoCallback callback;

    /**
     * Costruisce un frammento per la visualizzazione dei tavoli che hanno richiesto il pagamento
     */
    public CCPagamentoListInfoFragment(PagamentoCallback callback) {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/salaServerToTablet");
        this.tavoloService = new TavoloService();
        this.callback = callback;
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
        final View v = inflater.inflate(R.layout.cc_pagamento_list_info, null, false);
        tv_totale = v.findViewById(R.id.tv_totale);
        lv_piatti = v.findViewById(R.id.lv_piatti);
        btnAzione = v.findViewById(R.id.bt_azione);

        new Thread(new Runnable() {
            @Override
            public void run() {
                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String tavoloJson = message.getStringProperty("tavolo");
                            Log.d("CCameriere", "[" + getClass().getSimpleName() + "] Ho ricevuto: " + message.getStringProperty("UUID"));

                            Gson gson = new Gson();
                            Tavolo tavolo = gson.fromJson(tavoloJson, Tavolo.class);
                            tavolo.setVuolePagare(false);
                            tavolo.getComanda().setStato(true);
                            tavoloService.sendChangePayTavolo(tavolo);

                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }, "UUID = '" + uniqueID + "'");
            }
        }).start();

        return v;
    }

    /**
     * Richiede i tavoli da visualizzare
     * @param service TavoloService dal quale prelevare i dati
     * @param UUD stringa univoca per il riconoscimento della richiesta
     */
    @Override
    public void requestTavoli(TavoloService service, String UUD) {
        tavoloService.requestTavoliPerPagamento(UUD);
    }

    /**
     * Cambia i dati della view con quelli di un nuovo tavolo
     * @param tavolo tavolo contenente i nuovi dati
     */
    @Override
    public void cambiaDati(final Tavolo tavolo) {
        btnAzione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tavoloService.setPagato(tavolo, uniqueID);
                tavoloService.requestTavolo(uniqueID, tavolo.getUsername());
                Toast.makeText(getContext(), "Pagamento confermato", Toast.LENGTH_LONG).show();
                callback.cambiaDati();
            }
        });

        tv_totale.setText(tavolo.getComanda().getTotale() + "€");
        ListaOrdiniAdapter adapter = new ListaOrdiniAdapter(getActivity().getApplicationContext(), R.layout.cc_pagamento_list_info, tavolo.getComanda().getPiattiOrdinati());
        lv_piatti.setAdapter(adapter);
    }

    @Override
    public int isOccupato() {
        return 2;
    }
}
