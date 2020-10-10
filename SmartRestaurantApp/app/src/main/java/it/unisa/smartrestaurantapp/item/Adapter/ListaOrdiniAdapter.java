package it.unisa.smartrestaurantapp.item.Adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;

/**
 * Questa classe è l'adapter per la visualizzazione dei
 * piatti ordinati.
 */
public class ListaOrdiniAdapter extends ArrayAdapter<PiattoOrdinato> {
    private LayoutInflater inflater;
    private ImageView iv_piatto;
    private TextView tv_categoria;
    private TextView tv_nome_piatto;
    private TextView tv_note;
    private TextView tv_prezzo;
    private HashMap<Integer, View> views;

    /**
     * Costruisce un adapter per la visualizzazione dei piatti ordinati
     * @param context contesto
     * @param resourceId resourceId
     * @param objects lista di PiattoOrdinato
     */
    public ListaOrdiniAdapter(Context context, int resourceId, final List<PiattoOrdinato> objects) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
        views = new HashMap();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < objects.size(); i++) {
                    View view = views.get(i);
                    if (view == null) {
                        view = creaView(i, view, null);
                        views.put(i, view);
                    }
                }
            }
        }).start();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(views.get(position) == null) {
            v = creaView(position, v, parent);
            views.put(position, v);
        }

        return views.get(position);
    }

    public View creaView(final int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.list_ordini_item, parent, false);

        //Ottengo i riferimenti agli oggetti grafici
        iv_piatto = v.findViewById(R.id.iv_piatto);
        tv_categoria = v.findViewById(R.id.tv_categoria);
        tv_nome_piatto = v.findViewById(R.id.tv_nome_piatto);
        tv_note = v.findViewById(R.id.tv_note);
        tv_prezzo = v.findViewById(R.id.tv_prezzo);

        //Creo il PiattoOrdinato
        PiattoOrdinato piatto = getItem(position);

        //Imposto i valori nei campi della view
        //iv_piatto.setImageBitmap(piatto.getPiatto().getFoto());
        tv_categoria.setText(piatto.getPiatto().getCategoria());
        tv_nome_piatto.setText(piatto.getPiatto().getNome());
        tv_note.setText(piatto.getNote());
        tv_prezzo.setText(piatto.getPiatto().getPrezzo() + "€");
        int resourceId = this.getContext().getResources().getIdentifier(piatto.getPiatto().getFoto(), "drawable", this.getContext().getPackageName());
        iv_piatto.setImageResource(resourceId);

        views.put(position, v);

        return v;
    }
}
