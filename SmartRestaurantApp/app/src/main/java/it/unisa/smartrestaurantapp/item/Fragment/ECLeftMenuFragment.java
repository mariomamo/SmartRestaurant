package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import it.unisa.smartrestaurantapp.activity.ECActivity;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.ListItem;
import it.unisa.smartrestaurantapp.service.NotificaService;

public class ECLeftMenuFragment extends Fragment {
    private View oldItemPressed;
    private CustomAdapter generalMenuAdapter;
    private ListView listView;

    private NotificaService notificaService = new NotificaService();
    private String uniqueID = UUID.randomUUID().toString();

    public ECLeftMenuFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tv_menu_layout_fragment, container, false);

        Button btnChiamaCameriere = v.findViewById(R.id.btn_chiama_cameriere);
        btnChiamaCameriere.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("CCameriere", "Invio una notifica");

                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = localDateTime.format(formatter);

                notificaService.sendNotifica(uniqueID, new Notifica(0, "ccameriere", Notifica.Categoria.AIUTO, "exchef", "Ho bisogno di assistenza", false, formattedDateTime));

                Toast.makeText(getContext(), "Notifica inviata", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Ottengo il riferimento alla listView
        listView = getView().findViewById(R.id.menu);

        //Creo le voci del menù
        ArrayList<ListItem> menu = new ArrayList();
        menu.add(new ListItem("Ordini", "null"));
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

                        //Quando si clicca sul menù laterale si cambia il frammento
                        //In questo caso viene cambiato in base alla posizione
                        //dell'elemento, ma si può fare meglio
                        switch (position) {
                            case 0:
                                ECOrderFragment ecOrderFragment = new ECOrderFragment();
                                ECActivity.changeCenterView(ecOrderFragment);
                                break;
                            case 1:
                                ECDishFragment ecDishesFragment = new ECDishFragment("Chef");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 2:
                                ecDishesFragment = new ECDishFragment("Antipasti");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 3:
                                ecDishesFragment = new ECDishFragment("Primi");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 4:
                                ecDishesFragment = new ECDishFragment("Secondi");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 5:
                                ecDishesFragment = new ECDishFragment("Contorni");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 6:
                                ecDishesFragment = new ECDishFragment("Dolci");
                                ECActivity.changeCenterView(ecDishesFragment);
                                break;
                            case 7:
                                ecDishesFragment = new ECDishFragment("Bevande");
                                ECActivity.changeCenterView(ecDishesFragment);
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
     * @param view
     * @param target
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
     * @param view
     * @param target
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
}
