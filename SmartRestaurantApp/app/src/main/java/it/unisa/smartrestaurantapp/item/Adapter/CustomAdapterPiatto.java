package it.unisa.smartrestaurantapp.item.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Piatto;

public class CustomAdapterPiatto extends ArrayAdapter<Piatto> {
    private LayoutInflater inflater;

    /**
     * Crea un adapter per la visualizzazione dei piatti
     * @param context contesto dove far visualizzare il piatto
     * @param resourceId file di layout
     * @param objects piatti da visualizzare
     */
    public CustomAdapterPiatto(Context context, int resourceId, List<Piatto> objects) {
        super(context, resourceId, objects);

        inflater = LayoutInflater.from(context);
    }

    /**
     * Inserisce la foto ed il nome del piatto all'interno della view
     * @param position posizione all'interno della view
     * @param v view
     * @param parent view padre
     * @return view creata
     */
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null) {
            v = inflater.inflate(R.layout.list_item_with_image, parent, false);
        }


        Piatto piatto = getItem(position);

        ImageView ivPhoto = v.findViewById(R.id.photo);
        TextView tvName = v.findViewById(R.id.name);

        int resourceId = this.getContext().getResources().getIdentifier(piatto.getFoto(), "drawable", this.getContext().getPackageName());
        ivPhoto.setImageResource(resourceId);
        tvName.setText(piatto.getNome());

        return v;
    }
}
