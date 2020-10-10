package it.unisa.smartrestaurantapp.item.Fragment;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.function.Predicate;

import javax.jms.Message;
import javax.jms.MessageListener;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.TvActivityCallback;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Adapter.TvCartAdapter;
import it.unisa.smartrestaurantapp.item.CustomAdapterListener;
import it.unisa.smartrestaurantapp.item.TvCartAdapterListener;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.service.PiattoService;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class TvCartFragment extends Fragment {
    private Tavolo tavolo;

    private ListView lvPiatti;
    private TvActivityCallback callback;
    private double totaleCosto;
    private TextView tvTotale;

    private static SmartRestaurantDispatcher receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/carrelloServerToTablet");

    /**
     * Crea un TvCartFragment dove inserire i piatti ordinati del carrello
     * @param callback classe di callback
     */
    public TvCartFragment(final TvActivityCallback callback) {
        this.callback = callback;
    }

    /**
     * Crea la view ed inserisce all'interno gli elementi del carrello
     * @param inflater inflater
     * @param container container dove inserire il fragment
     * @param savedInstanceState dati salvati
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tv_cart_layout_fragment, container, false);

        tavolo = callback.getAccount();

        final TvCartAdapter ca = new TvCartAdapter(getContext(), R.layout.list_item_with_image, new ArrayList<PiattoOrdinato>(), true, listener);
        //TvCartAdapter ca_ordinati = new TvCartAdapter(getContext(), R.layout.list_item_with_image, new ArrayList<PiattoOrdinato>(), false, listener);

        //Prendo il riferimento al ListView presente nel layout dell'adapter
        lvPiatti = v.findViewById(R.id.list_piatti);

        lvPiatti.setAdapter(ca);

        //Prendo i piatti ordinati dalla comanda
        final ArrayList<PiattoOrdinato> listPiattiOrdinati = tavolo.getCarrello();

        for(PiattoOrdinato p: listPiattiOrdinati) {
            ca.add(p);
            totaleCosto += p.getPiatto().getPrezzo() * p.getQuantita();
            //tavolo.getComanda().addPiatto(p);
        }

        //Mostro il totale da pagare
        tvTotale = v.findViewById(R.id.totale);

        tvTotale.setText("Totale: " + totaleCosto + "€");

        //Setto il listener per la conferma dell'ordine
        Button btnConferma = v.findViewById(R.id.conferma);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Confermato", Toast.LENGTH_LONG).show();
                tvTotale.setText("0");

                listPiattiOrdinati.removeIf(new Predicate<PiattoOrdinato>() {
                    @Override
                    public boolean test(PiattoOrdinato p) {
                        return (p.getQuantita() == 0);
                    }
                });

                tavolo.setCarrello(listPiattiOrdinati);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String id = message.getStringProperty("comanda");
                            int id_comanda = -1;
                            if (id != null) {
                                id_comanda = Integer.parseInt(id);
                                Tavolo t = callback.getAccount();
                                t.getComanda().setId(id_comanda);
                                callback.update(t);
                            }

                            //Ripulisco la listView rimuovendo i piatti presenti
                            lvPiatti.post(new Runnable() {
                                @Override
                                public void run() {
                                    lvPiatti.setAdapter(new TvCartAdapter(getContext(), R.layout.list_item_with_image, new ArrayList<PiattoOrdinato>(), true, listener));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                PiattoService.sendOrdine(tavolo);
                //Si deve distinguere tra quelli già ordinati e quelli no
            }
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MY-DEBUG", "Activity distrutta");
    }

    final CustomAdapterListener listener = new CustomAdapterListener() {
        @Override
        public void doAction(PiattoOrdinato piattoOrdinato) {
            tavolo.getCarrello().remove(piattoOrdinato);
            lvPiatti.setAdapter(new TvCartAdapter(getContext(), R.layout.list_item_with_image, tavolo.getCarrello(), true, listener));
            Toast.makeText(getContext(), "Piatto rimosso", Toast.LENGTH_SHORT).show();
            tvTotale.setText(calcolaCosto(tavolo.getCarrello()) + "");
        }
    };

    public float calcolaCosto(ArrayList<PiattoOrdinato> listPiattiOrdinati) {
        float totale = 0;
        for(PiattoOrdinato p: listPiattiOrdinati) {
            totale += p.getPiatto().getPrezzo() * p.getQuantita();
        }

        return totale;
    }
}
