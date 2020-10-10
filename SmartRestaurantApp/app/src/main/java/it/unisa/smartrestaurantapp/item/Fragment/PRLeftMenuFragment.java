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
import it.unisa.smartrestaurantapp.activity.PrActivity;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapter;
import it.unisa.smartrestaurantapp.item.Fragment.PRViewFragment;
import it.unisa.smartrestaurantapp.item.ListItem;

public class PRLeftMenuFragment extends Fragment {
    private View oldItemPressed;
    private CustomAdapter generalMenuAdapter;
    private ListView listView;

    /**
     * Crea il menu laterale per il proprietario
     */
    public PRLeftMenuFragment() {}

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

        ArrayList<ListItem> menu = new ArrayList();
        menu.add(new ListItem("Gestione sala", "null"));

        listView = v.findViewById(R.id.menu);

        generalMenuAdapter = new CustomAdapter(v.getContext(), R.layout.list_item, menu, 0);

        listView.setAdapter(generalMenuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldItemPressed != null) {
                    setItemNotClicked(view, oldItemPressed);
                }

                switch (position) {
                    case 0:
                        PRViewFragment prViewFragment = new PRViewFragment();
                        PrActivity.changeCenterView(prViewFragment);
                        break;
                    case 1:
                        prViewFragment = new PRViewFragment();
                        PrActivity.changeCenterView(prViewFragment);
                        break;
                    default:
                        break;
                }

                oldItemPressed = view;

                setItemClicked(view, oldItemPressed);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Salva in oldPressed l'elemento in posizione 'pos'
                while ((oldItemPressed = generalMenuAdapter.getTargetView()) == null) {}

                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        //Simula il click sull'item
                        listView.performItemClick(
                                listView.getAdapter().getView(0, oldItemPressed, null),
                                0,
                                listView.getAdapter().getItemId(0));
                    }
                });
            }
        }).start();

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
}
