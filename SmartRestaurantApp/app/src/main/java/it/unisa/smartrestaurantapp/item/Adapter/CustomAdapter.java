package it.unisa.smartrestaurantapp.item.Adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.item.ListItem;

public class CustomAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater inflater;
    private int pos;
    private View targetView;
    private HashMap<Integer, View> views;

    /**
     * Adapter per mostrare dati contenuti in una lista di "ListItem"
     * @param context contesto dove inserire i dati
     * @param resourceId file di layout
     * @param objects "ListItem" da mostrare
     * @param pos posizione
     */
    public CustomAdapter(Context context, int resourceId, List<ListItem> objects, int pos) {
        super(context, resourceId, objects);

        inflater = LayoutInflater.from(context);
        views = new HashMap<>();

        this.pos = pos;
    }

    /**
     * Restituisce la view selezionata
     * @param position posizione della view
     * @param v view
     * @param parent view padre
     * @return view selezionata
     */
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(views.get(position) == null) {
            View view = inflater.inflate(R.layout.list_item, parent, false);

            if (position == pos) {
                targetView = view;
                //Cambio il colore di sfondo
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.primaryColor));
                //Ottengo il riferimento al bottone tramite la view oldPressed
                TextView btn_testo = view.findViewById(R.id.btn_name);
                //Modifico la grafica del bottone
                btn_testo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.primaryTextColor));
                btn_testo.setTypeface(null, Typeface.NORMAL);
            }

            ListItem c = getItem(position);

            TextView nameTextView;

            nameTextView = view.findViewById(R.id.btn_name);

            nameTextView.setText(c.getName());

            views.put(position, view);

        }

        return views.get(position);
    }

    /**
     * Restituisce la target view
     * @return target view
     */
    public View getTargetView() {
        return targetView;
    }
}
