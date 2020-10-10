package it.unisa.smartrestaurantapp.item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.service.ComandaService;

public class CustomAdapterSingoloOrdine extends ArrayAdapter<PiattoOrdinato> {
    private LayoutInflater inflater;
    private HashMap<Integer, View> views;
    private CustomAdapterListener listener;
    private boolean first;

    /**
     * Viene usato per creare il singolo piatto dell'ordine di un tavolo, da visualizzare nella sezione "Ordini" dell'Executive Chef.
     * @param context
     * @param resourceId
     * @param objects
     */
    public CustomAdapterSingoloOrdine(Context context, int resourceId, final List<PiattoOrdinato> objects, CustomAdapterListener listener) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
        views = new HashMap<>();
        this.listener = listener;
        this.first = true;

        //Pre carico gli elementi per evitare il lag
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

    /**
     * Inserisce il nome del piatto ordinato all'interno della view
     * @param position posizione all'interno della view
     * @param v
     * @param parent
     * @return view creata
     */
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (views.get(position) == null) {
            View view = creaView(position, v, parent);
            views.put(position, view);
        }

        return views.get(position);
    }

    public View creaView(final int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.list_item_with_buttons, parent, false);

        final PiattoOrdinato piattoOrdinato = getItem(position);
        Log.d("PROVA-DEBUG-SO", "POSIZIONE: " + piattoOrdinato.getPiatto().getNome());

        TextView tvName = v.findViewById(R.id.tv_nome_piatto_ordine);
        TextView tvNotePiatto = v.findViewById(R.id.tv_note_piatto_ordine);
        TextView tv_categoria = v.findViewById(R.id.tv_categoria);
        Button btnPositive = v.findViewById(R.id.btn_positive);
        Button btnNegavite = v.findViewById(R.id.btn_negative);
        final LinearLayout ll_piatto_ordinato = v.findViewById(R.id.ll_piatto_ordinato);

        if (position == 0) {
            tv_categoria.setVisibility(View.VISIBLE);
            tv_categoria.setText(piattoOrdinato.getPiatto().getCategoria());
            tv_categoria.setPadding(0, 0, 0, 0);
            first = false;
        } else if (position > 0) {
            PiattoOrdinato old = getItem(position - 1);

            Log.d("PROVA-DEBUG-SO", "LAST: " + old.getPiatto().getCategoria().toLowerCase() + " CURRENT: " + piattoOrdinato.getPiatto().getCategoria().toLowerCase() + " esito: " + !getItem(position - 1).getPiatto().getCategoria().toLowerCase().equals(piattoOrdinato.getPiatto().getCategoria().toLowerCase()));

            //TOGLIERE LAST!
            if (!old.getPiatto().getCategoria().toLowerCase().equals(piattoOrdinato.getPiatto().getCategoria().toLowerCase())) {
                Log.d("PROVA-DEBUG-SO", "-- Sono dentro --");
                tv_categoria.setVisibility(View.VISIBLE);
                tv_categoria.setText(piattoOrdinato.getPiatto().getCategoria());
            } else {
                tv_categoria.setVisibility(View.GONE);
                tv_categoria.setPadding(0, 0, 0, 0);
            }
        }

        tvName.setText(piattoOrdinato.getPiatto().getNome());
        tvNotePiatto.setText(piattoOrdinato.getNote());

        Log.d("DEBUG-EC", "[" + getClass().getSimpleName() + "] sono stato chiamato per il piatto " + piattoOrdinato.getPiatto().getNome());

        if (piattoOrdinato.getStato()) {
            setChecked(ll_piatto_ordinato);
        } else {
            setNotChecked(ll_piatto_ordinato);
        }

        btnPositive.setTag(position);
        btnNegavite.setTag(position);

        btnNegavite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = buildDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MY-DEBUG-COMANDA", "Nome: " + piattoOrdinato.getPiatto().getNome() + " id: " + piattoOrdinato.getId());
                        ComandaService.segnaComeNonFatto(piattoOrdinato, UUID.randomUUID().toString());
                        try {
                            listener.doAction(piattoOrdinato);
                        } catch (NullPointerException ex) {
                            Log.d("MY-DEBUG-COMANDA", "NullPointerException");
                            ex.printStackTrace();
                        }
                        //setNotChecked(ll_piatto_ordinato);
                    }
                });
                dialog.setView(getComanda(piattoOrdinato));
                dialog.show();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = buildDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ComandaService.segnaComeFatto(piattoOrdinato, UUID.randomUUID().toString());
                        piattoOrdinato.setStato(true);
                        setChecked(ll_piatto_ordinato);
                    }
                });

                dialog.setView(getComanda(piattoOrdinato));
                dialog.show();
            }
        });

        return v;
    }

    public void setChecked(LinearLayout ll_piatto_ordinato) {
        ll_piatto_ordinato.setBackgroundColor(Color.rgb(0, 187, 45));
        //Ottengo il riferimento alla voce del menù
        TextView tv_nome_piatto_ordine = ll_piatto_ordinato.findViewById(R.id.tv_nome_piatto_ordine);
//        //Barro il testo
//        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(tv_nome_piatto_ordine.getText().toString());
//        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
//        spanBuilder.setSpan(
//                strikethroughSpan, // Span to add
//                0, // Start
//                tv_nome_piatto_ordine.getText().toString().length(), // End of the span (exclusive)
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Text changes will not reflect in the strike changing
//        );
//        tv_nome_piatto_ordine.setText(spanBuilder);
    }

    public void setNotChecked(LinearLayout ll_piatto_ordinato) {
        ll_piatto_ordinato.setBackgroundColor(Color.TRANSPARENT);
    }

    public AlertDialog buildDialog(DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Modifica ordine");
        builder.setMessage("Vuoi veramente segnare l'ordine come \"preparato\" ?");
        builder.setPositiveButton("Si", listener);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    public View getComanda(PiattoOrdinato piattoOrdinato) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_item_with_buttons, null);

        Button btn_negative = view.findViewById(R.id.btn_negative);
        Button btn_positive = view.findViewById(R.id.btn_positive);
        btn_negative.setVisibility(View.INVISIBLE);
        btn_positive.setVisibility(View.INVISIBLE);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 0, 0, 0);

        TextView tv_categoria = view.findViewById(R.id.tv_categoria);
        TextView tv_nome_piatto_ordine = view.findViewById(R.id.tv_nome_piatto_ordine);
        TextView tv_note_piatto_ordine = view.findViewById(R.id.tv_note_piatto_ordine);

        tv_categoria.setText(piattoOrdinato.getPiatto().getCategoria());
        tv_nome_piatto_ordine.setText(piattoOrdinato.getPiatto().getNome());
        tv_note_piatto_ordine.setText(piattoOrdinato.getNote());

        tv_categoria.setLayoutParams(params);
        tv_nome_piatto_ordine.setLayoutParams(params);
        tv_note_piatto_ordine.setLayoutParams(params);
        return view;
    }

    public void cancellaView(int pos) {
        Log.d("MY-DEBUG-VIEW", "Rimuovo la view: " + pos);
        for (int i = pos; i < views.size() - 1; i++) {
            Log.d("MY-DEBUG-VIEW", "i: " + i + " size: " + views.size() + " pos: " + pos);
            View successiva = views.get(i+1);
            views.put(pos, successiva);
        }

        views.remove(views.size() - 1);
        Log.d("MY-DEBUG-VIEW",  "New size: " + views.size());
    }

    @Override
    public void remove(@Nullable PiattoOrdinato object) {
        super.remove(object);
        Log.d("MY-DEBUG-VIEW", object.getPiatto().getNome() + " id: " + object.getId());
    }
}
