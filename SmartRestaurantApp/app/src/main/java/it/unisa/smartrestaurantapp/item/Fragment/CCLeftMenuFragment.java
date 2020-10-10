package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import java.util.ArrayList;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.CCActivity;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.ListItem;

public class CCLeftMenuFragment extends Fragment {
    private View oldItemPressed;
    private CustomAdapter generalMenuAdapter;
    private ListView listView;
    private CCTavoliFragment ccTavoliFragment;

    /**
     * Crea il menu laterale per il capo cameriere
     */
    public CCLeftMenuFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Crea la view contente il menu
     * @param inflater inflater
     * @param container container dove inserire il frammento
     * @param savedInstanceState dati salvati
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.left_menu_layout_fragment, container, false);
        return v;
    }

    /**
     * Inserisce gli elementi ed i collegamenti nel menu
     */
    @Override
    public void onStart() {
        super.onStart();

        //Ottengo il riferimento alla listView
        listView = getView().findViewById(R.id.menu);

        //Creo le voci del menù
        ArrayList<ListItem> menu = new ArrayList();
        menu.add(new ListItem("Notifiche", "null"));
        //menu.add(new ListItem("Tutti i tavoli", "null"));
        menu.add(new ListItem("Tavoli liberi", "null"));
        menu.add(new ListItem("Tavoli occupati", "null"));
        menu.add(new ListItem("Pagamenti", "null"));

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
                                CCTavoloNotificheFragment ccTavoloNotificheFragment = new CCTavoloNotificheFragment();
                                CCActivity.changeCenterView(ccTavoloNotificheFragment);
                                break;
                            case 1:
                                ccTavoliFragment = new CCTavoliFragment(new CCTavoliViewGenerale(false));
                                CCActivity.changeCenterView(ccTavoliFragment);
                                break;
                            case 2:
                                ccTavoliFragment = new CCTavoliFragment(new CCTavoliViewGenerale(true));
                                CCActivity.changeCenterView(ccTavoliFragment);
                                break;
                            case 3:
                                ccTavoliFragment = new CCTavoliFragment(new CCPagamentoListInfoFragment(callback));
                                CCActivity.changeCenterView(ccTavoliFragment);
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

    final PagamentoCallback callback = new PagamentoCallback() {
        @Override
        public void cambiaDati() {
            ccTavoliFragment = new CCTavoliFragment(new CCPagamentoListInfoFragment(callback));
            CCActivity.changeCenterView(ccTavoliFragment);
        }
    };
}
