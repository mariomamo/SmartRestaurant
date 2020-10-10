package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.TvActivity;
import it.unisa.smartrestaurantapp.activity.TvActivityCallback;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.ListItem;
import it.unisa.smartrestaurantapp.service.NotificaService;
import it.unisa.smartrestaurantapp.service.TavoloService;

public class TvLeftMenuFragment extends Fragment {
    private View oldItemPressed;
    private CustomAdapter generalMenuAdapter;
    private ListView listView;
    private TvActivityCallback callback;
    private NotificaService notificaService = new NotificaService();
    private String uniqueID = UUID.randomUUID().toString();

    /**
     * Crea il menu laterale per il "Tavolo"
     * @param callback classe di callback
     */
    public TvLeftMenuFragment(TvActivityCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Crea la view per mostrare il menu
     * @param inflater inflater
     * @param container container dove inserire il frammento
     * @param savedInstanceState dati salvati
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tv_menu_layout_fragment, container, false);

        Button btnChiamaCameriere = v.findViewById(R.id.btn_chiama_cameriere);
        btnChiamaCameriere.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = localDateTime.format(formatter);
                notificaService.sendNotifica(uniqueID, new Notifica(0, "ccameriere", Notifica.Categoria.AIUTO, callback.getAccount().getUsername(), "Ho bisogno di assistenza", false, formattedDateTime));

                Toast.makeText(getContext(), "Notifica inviata, resta in attesa", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    /**
     * Creo il menu e vi associo i collegamenti
     */
    @Override
    public void onStart() {
        super.onStart();

        //Ottengo il riferimento alla listView
        listView = getView().findViewById(R.id.menu);

        //Creo le voci del menù
        ArrayList<ListItem> menu = new ArrayList();
        menu.add(new ListItem("Specialità dello chef", "null"));
        menu.add(new ListItem("Antipasti", "null"));
        menu.add(new ListItem("Primi", "null"));
        menu.add(new ListItem("Secondi", "null"));
        menu.add(new ListItem("Contorni", "null"));
        menu.add(new ListItem("Dolci", "null"));
        menu.add(new ListItem("Bevande", "null"));

        //Istanzio l'adapter
        generalMenuAdapter = new CustomAdapter(getView().getContext(), R.layout.list_item, menu, 0);

        //Eseguo delle modifiche all'UI
        listView.post(new Runnable() {
            @Override
            public void run() {

                //Setto l'adapter
                listView.setAdapter(generalMenuAdapter);

                //Setto il listener
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (oldItemPressed != null) {
                            setItemNotClicked(view, oldItemPressed);
                        }

                        switch (position) {
                            case 0:
                                TvDishFragment tvDishesFragment = new TvDishFragment("Chef", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 1:
                                tvDishesFragment = new TvDishFragment("Antipasti", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 2:
                                tvDishesFragment = new TvDishFragment("Primi", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 3:
                                tvDishesFragment = new TvDishFragment("Secondi", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 4:
                                tvDishesFragment = new TvDishFragment("Contorni", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 5:
                                tvDishesFragment = new TvDishFragment("Dolci", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            case 6:
                                tvDishesFragment = new TvDishFragment("Bevande", callback);
                                TvActivity.changeCenterView(tvDishesFragment);
                                break;
                            default:
                                break;
                        }

                        //Salvo la view selezionata
                        oldItemPressed = view;

                        //Coloro la view selezionata
                        setItemClicked(view, oldItemPressed);
                    }
                });
            }
        });

        //Simulo il click sul list item
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ((oldItemPressed = generalMenuAdapter.getTargetView()) == null) {}

                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.performItemClick(
                                listView.getAdapter().getView(0, oldItemPressed, null),
                                0,
                                listView.getAdapter().getItemId(0));
                    }
                });
            }
        }).start();
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
        target.setBackgroundColor(android.R.drawable.btn_default);
        //Ottengo il riferimento al bottone tramite la view oldPressed
        TextView btn_testo = target.findViewById(R.id.btn_name);
        //Modifico la grafica del bottone
        btn_testo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.primaryTextColor));
        btn_testo.setTypeface(null, Typeface.NORMAL);
    }

    /**
     * Rende l'ultimo elemento cliccato "non cliccato"
     */
    public void removeMenuClick() {
        setItemNotClicked(oldItemPressed, oldItemPressed);
    }
}
