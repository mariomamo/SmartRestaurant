package it.unisa.smartrestaurantapp.item.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.TvActivityCallback;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Adapter.TvCloseMealAdapter;
import it.unisa.smartrestaurantapp.service.NotificaService;
import it.unisa.smartrestaurantapp.service.TavoloService;

public class TvCloseMealFragment extends Fragment {
    private Tavolo tavolo;

    private ListView lvPiatti;
    private RatingBar rb;
    private EditText etReview;
    private TvActivityCallback callback;

    private String uniqueID = UUID.randomUUID().toString();

    /**
     * Crea un TvCloseMealFragment contenente i piatti ordinati durante il pasto
     * @param callback classe di callback
     */
    public TvCloseMealFragment(TvActivityCallback callback) {
        this.callback = callback;
    }

    /**
     * Crea la view ed inserisce all'interno gli elementi del carrello per il pagamento
     * @param inflater inflater
     * @param container container dove inserire il fragment
     * @param savedInstanceState dati salvati
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tv_close_meal_layout_fragment, container, false);

        tavolo = callback.getAccount();

        TvCloseMealAdapter ca = new TvCloseMealAdapter(getContext(), R.layout.list_item_close_meal, new ArrayList<PiattoOrdinato>());

        //Prendo il riferimento al ListView presente nel layout dell'adapter
        lvPiatti = v.findViewById(R.id.list_piatti);

        lvPiatti.setAdapter(ca);

        //Prendo i piatti ordinati dalla comanda
        ArrayList<PiattoOrdinato> listPiatti = tavolo.getComanda().getPiattiOrdinati();

        //Calcolo il prezzo totale (solo dei piatti preparati)
        float tot = 0;

        for(PiattoOrdinato p: listPiatti) {
            ca.add(p);

            if(p.getStato()) {
                tot += p.getPiatto().getPrezzo() * p.getQuantita();
            }
        }

        //Imposto il listener per l'item
        lvPiatti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PiattoOrdinato item = (PiattoOrdinato) lvPiatti.getItemAtPosition(position);

                Toast.makeText(v.getContext(), item.getPiatto().getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        rb = v.findViewById(R.id.ratingBar);
        etReview = v.findViewById(R.id.review);

        //Mostro il totale da pagare
        TextView tvTotale = v.findViewById(R.id.totale);

        tvTotale.setText("Totale: " + tot + "€");

        //Setto il listener per il pagamento
        Button btnConferma = v.findViewById(R.id.conferma);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //Prendo la recensione
                int rate = rb.getNumStars();
                String review = etReview.getText().toString();

                if(review.length() > 255) {
                    Toast.makeText(v.getContext(), "La recensione non deve superare i 255 caratteri", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(review.length() > 0) {
                    tavolo.getComanda().setRecensione(rate + " " + review);
                }

                TavoloService tavoloService = new TavoloService();
                NotificaService notificaService = new NotificaService();
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = localDateTime.format(formatter);
                notificaService.sendNotifica(uniqueID, new Notifica(0, "ccameriere", Notifica.Categoria.PAGAMENTO, callback.getAccount().getUsername(), "Voglio pagare", false, formattedDateTime));
                tavolo.setVuolePagare(true);
                tavoloService.sendChangePayTavolo(tavolo);

                Toast.makeText(v.getContext(), "Attendi l'arrivo di un cameriere per il pagamento", Toast.LENGTH_LONG).show();
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
}
