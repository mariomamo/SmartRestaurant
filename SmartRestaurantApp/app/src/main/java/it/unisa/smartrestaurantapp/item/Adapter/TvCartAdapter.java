package it.unisa.smartrestaurantapp.item.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.item.CustomAdapterListener;

public class TvCartAdapter extends ArrayAdapter<PiattoOrdinato> {
    private LayoutInflater inflater;
    private boolean cart;
    private CustomAdapterListener listener;

    /**
     * Crea un TvCartAdapter che mostra i piatti ordinati nel carrello
     * @param context contesto dal quale è stata richiesta la creazione
     * @param resourceId resourceId del layout
     * @param objects piatti ordinati da mostrare
     * @param cart
     */
    public TvCartAdapter(Context context, int resourceId, List<PiattoOrdinato> objects, boolean cart, CustomAdapterListener listener) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
        this.cart = cart;
        this.listener = listener;
    }

    /**
     * Inserisce il nome, il prezzo e la quantità del piatto ordinato all'interno della view
     * Setta i listener dei bottoni "-" e "+"
     * @param position posizione all'interno della view
     * @param v view
     * @param parent view padre
     * @return view creata
     */
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        Log.d("MY-DEBUG", "[CustomAdapterPiatto] sono stato chiamato");

        final PiattoOrdinato piatto = getItem(position);

        if(v == null) {
            v = inflater.inflate(R.layout.list_item_cart, parent, false);
        }

        TextView tvName = v.findViewById(R.id.name);
        TextView tvPrice = v.findViewById(R.id.price);
        TextView tvQ = v.findViewById(R.id.quantity);
        TextView tvNote = v.findViewById(R.id.note);
        ImageView iv_trash = v.findViewById(R.id.iv_remove);

        iv_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.doAction(piatto);
            }
        });

        tvName.setText(piatto.getPiatto().getNome());
        tvPrice.setText(piatto.getPiatto().getPrezzo() + "€");
        tvQ.setText(piatto.getQuantita() + "");

        if (piatto.getNote() != null && piatto.getNote().length() > 0) {
            tvNote.setText(piatto.getNote());
            tvNote.setVisibility(View.VISIBLE);
        }

        //Setto i listener dei bottoni
        Button btnPlus = v.findViewById(R.id.plus);
        Button btnMinus = v.findViewById(R.id.minus);

        if (cart) {
            btnPlus.setVisibility(View.VISIBLE);
            btnMinus.setVisibility(View.VISIBLE);
            tvQ.setVisibility(View.VISIBLE);

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout ll = (LinearLayout) v.getParent();

                    TextView tvQ = ll.findViewById(R.id.quantity);

                    piatto.setQuantita(piatto.getQuantita() + 1);

                    tvQ.setText(piatto.getQuantita() + "");
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (piatto.getQuantita() > 0) {
                        LinearLayout ll = (LinearLayout) v.getParent();

                        TextView tvQ = ll.findViewById(R.id.quantity);

                        piatto.setQuantita(piatto.getQuantita() - 1);

                        tvQ.setText(piatto.getQuantita() + "");
                    }
                }
            });
        } else {
            btnPlus.setVisibility(View.INVISIBLE);
            btnMinus.setVisibility(View.INVISIBLE);
            tvQ.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}
