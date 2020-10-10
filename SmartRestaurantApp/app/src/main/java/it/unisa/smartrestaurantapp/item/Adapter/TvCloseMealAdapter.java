package it.unisa.smartrestaurantapp.item.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;

public class TvCloseMealAdapter extends ArrayAdapter<PiattoOrdinato> {
    private LayoutInflater inflater;

    public TvCloseMealAdapter(Context context, int resourceId, List<PiattoOrdinato> objects) {
        super(context, resourceId, objects);

        inflater = LayoutInflater.from(context);
    }

    /**
     * Inserisce il nome, il prezzo e la quantità del piatto ordinato e consumato all'interno della view
     * @param position posizione all'interno della view
     * @param v
     * @param parent
     * @return view creata
     */
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null) {
            v = inflater.inflate(R.layout.list_item_close_meal, parent, false);
        }

        PiattoOrdinato piatto = getItem(position);

        TextView tvName = v.findViewById(R.id.name);
        TextView tvPrice = v.findViewById(R.id.price);
        TextView tvQuantity = v.findViewById(R.id.quantity);
        TextView tvDone = v.findViewById(R.id.done);
        TextView tvNote = v.findViewById(R.id.note);

        tvName.setText(piatto.getPiatto().getNome());
        tvPrice.setText(piatto.getPiatto().getPrezzo() * piatto.getQuantita() + "€");
        tvQuantity.setText("Quantità: " + piatto.getQuantita());

        if(piatto.getStato()) {
            tvDone.setTextColor(v.getResources().getColor(R.color.buttonSave));
            tvDone.setText("Preparato");
        } else {
            tvDone.setTextColor(v.getResources().getColor(R.color.buttonCanc));
            tvDone.setText("In preparazione");
            tvPrice.setText("0€");
        }

        if(piatto.getNote() != null && piatto.getNote().length() > 0) {
            tvNote.setText(piatto.getNote());
            tvNote.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
