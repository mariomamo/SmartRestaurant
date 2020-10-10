package it.unisa.smartrestaurantapp.item;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;

public class CustomAdapterCustomAdapter extends ArrayAdapter<Tavolo> implements CustomAdapterListener {
    private LayoutInflater inflater;
    private int resource;
    private HashMap<Integer, View> views;

    private ArrayList<PiattoOrdinato> ordine;

    public CustomAdapterCustomAdapter(Context context, int resourceId, List<Tavolo> objects) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
        this.resource = resourceId;
        views = new HashMap<>();
    }

    /**
     * Inserisce il nome del tavolo all'interno della view e crea le list view con i suoi ordini
     * @param position posizione all'interno della view
     * @param v
     * @param parent
     * @return view creata
     */
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        if (views.get(position) == null) {
            v = inflater.inflate(this.resource, parent, false);
            Tavolo tavolo = getItem(position);

//            Log.d("MY-DEBUG-EC", "### POS: " + position);
//            for (PiattoOrdinato po : tavolo.getComanda().getPiattiOrdinati()) {
//                Log.d("MY-DEBUG-EC", "POS: " + position + " -> " + po.getPiatto().getNome());
//            }
//            Log.d("MY-DEBUG-EC", "### FINE POS: " + position);
//
//            Log.d("DEBUG-EC", "Ho letto il tavolo " + tavolo.getNome());
            ordine = tavolo.getComanda().getPiattiOrdinati();

//            Log.d("DEBUG-EC", "[" + getClass().getSimpleName() + "] Inflate del tavolo " + tavolo.getNome());

            TextView tvNomeTavolo = v.findViewById(R.id.tv_nome_tavolo);
            LinearLayout ll = v.findViewById(R.id.lv_ordini);

            //Inserisco il nome del tavolo
            tvNomeTavolo.setText(tavolo.getNome());

            LinearLayout llMain = new LinearLayout(getContext());
            llMain.setOrientation(LinearLayout.VERTICAL);

            /*Antipasti*/
            ArrayList<PiattoOrdinato> antipasti = new ArrayList<>();
            /*Primi*/
            ArrayList<PiattoOrdinato> primi = new ArrayList<>();
            /*Secondi*/
            ArrayList<PiattoOrdinato> secondi = new ArrayList<>();
            /*Chef*/
            ArrayList<PiattoOrdinato> chef = new ArrayList<>();

            for(PiattoOrdinato p: ordine) {
                switch(p.getPiatto().getCategoria().toLowerCase()) {
                    case "antipasti":
                        antipasti.add(p);
                        break;
                    case "primi":
                        primi.add(p);
                        break;
                    case "secondi":
                        secondi.add(p);
                        break;
                    case "chef":
                        chef.add(p);
                        break;
                    default:
                        break;
                }
            }

            attachPiatto(antipasti, llMain);
            attachPiatto(primi, llMain);
            attachPiatto(secondi, llMain);
            attachPiatto(chef, llMain);

            ll.addView(llMain);

            views.put(position, v);
        }
        /*if(v == null) {
            v = inflater.inflate(this.resource, parent, false);
        }*/

        return views.get(position);
    }

    public void attachPiatto(ArrayList<PiattoOrdinato> lista, LinearLayout layout) {
        if(lista.size() > 0) {
            CustomAdapterSingoloOrdine ca = new CustomAdapterSingoloOrdine(getContext(), R.layout.list_item_with_buttons, lista, this);

//            for (PiattoOrdinato p : antipasti) {
//                TextView tvAntipasti = new TextView(getContext());
//                tvAntipasti.setText(p.getPiatto().getNome());
//                llMain.addView(tvAntipasti);
//
//            }

            ListView lvAntipasti = new ListView(getContext());
            TextView tvAntipasti = new TextView(getContext());

            tvAntipasti.setTextSize(18);
            tvAntipasti.setTextColor(Color.BLACK);
            tvAntipasti.setPadding(0, 20, 0, 0);

            lvAntipasti.setAdapter(ca);

            tvAntipasti.setText("Antipasti");

            layout.addView(tvAntipasti);
            layout.addView(lvAntipasti);
        }
    }


    @Override
    public void doAction(PiattoOrdinato piattoOrdinato) {
        Log.d("MY-CUSTOMADAPTERCOMANDA", "IMPLEMENTARE IL METODO doAction");
    }
}
