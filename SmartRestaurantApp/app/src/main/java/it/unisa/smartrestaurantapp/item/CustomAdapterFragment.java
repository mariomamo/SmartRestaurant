package it.unisa.smartrestaurantapp.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;

public class CustomAdapterFragment extends Fragment implements CustomAdapterListener {
    private Tavolo tavolo;
    private ArrayList<PiattoOrdinato> ordine;
    private LinearLayout lv_ordini;

    public CustomAdapterFragment(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_item_listview, container, false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(params);

        ordine = tavolo.getComanda().getPiattiOrdinati();

        TextView tvNomeTavolo = v.findViewById(R.id.tv_nome_tavolo);
        lv_ordini = v.findViewById(R.id.lv_ordini);

        //Inserisco il nome del tavolo
        tvNomeTavolo.setText(tavolo.getNome());

        //LinearLayout llMain = new LinearLayout(getContext());
        //llMain.setOrientation(LinearLayout.VERTICAL);

        /*Antipasti*/
        ArrayList<PiattoOrdinato> antipasti = new ArrayList<>();
        /*Primi*/
        ArrayList<PiattoOrdinato> primi = new ArrayList<>();
        /*Secondi*/
        ArrayList<PiattoOrdinato> secondi = new ArrayList<>();
        /*Chef*/
        ArrayList<PiattoOrdinato> chef = new ArrayList<>();
        /*Bevande*/
        ArrayList<PiattoOrdinato> bevande = new ArrayList<>();
        /*contorni*/
        ArrayList<PiattoOrdinato> contorni = new ArrayList<>();
        /*dolci*/
        ArrayList<PiattoOrdinato> dolci = new ArrayList<>();

        for(PiattoOrdinato p: ordine) {
            switch(p.getPiatto().getCategoria().toLowerCase()) {
                case "antipasti":
                    antipasti.add(p);
                    break;
                case "primi":
                    primi.add(p);
                    //Log.d("MY-DEBUG-COMANDA-PRIMI", p.getPiatto().getNome() + ", " + p.getPiatto().getCategoria());
                    break;
                case "secondi":
                    secondi.add(p);
                    break;
                case "chef":
                    chef.add(p);
                    break;
                case "bevande":
                    bevande.add(p);
                    break;
                case "contorni":
                    bevande.add(p);
                    break;
                case "dolci":
                    bevande.add(p);
                    break;
                default:
                    break;
            }
        }

        ordine.clear();
        ordine.addAll(antipasti);
        ordine.addAll(primi);
        ordine.addAll(secondi);
        ordine.addAll(chef);
        ordine.addAll(contorni);
        ordine.addAll(dolci);
        ordine.addAll(bevande);

        Log.d("MY-DEBUG-ORDINE", "### INIZIO ###");
        for (PiattoOrdinato po : ordine) {
            Log.d("MY-DEBUG-ORDINE", po.getPiatto().getCategoria() + " - " + po.getPiatto().getNome());
        }
        Log.d("MY-DEBUG-ORDINE", "### FINE ###");

        attachPiatto(ordine, lv_ordini, "antipasti");
//        attachPiatto(primi, lv_ordini, "primi");
//        attachPiatto(secondi, lv_ordini, "secondi");
//        attachPiatto(chef, lv_ordini, "chef");
//        attachPiatto(bevande, lv_ordini, "bevande");

        //ll.addView(llMain);
        /*if(v == null) {
            v = inflater.inflate(this.resource, parent, false);
        }*/

        return v;
    }

    public void attachPiatto(ArrayList<PiattoOrdinato> lista, LinearLayout layout, String header) {
        layout.removeAllViews();
        if(lista.size() > 0) {
            CustomAdapterSingoloOrdine ca = new CustomAdapterSingoloOrdine(getContext(), R.layout.list_item_with_buttons, lista, this);

//            for (PiattoOrdinato p : antipasti) {
//                TextView tvAntipasti = new TextView(getContext());
//                tvAntipasti.setText(p.getPiatto().getNome());
//                llMain.addView(tvAntipasti);
//
//            }

            ListView lvAntipasti = new ListView(getContext());
//            TextView tvAntipasti = new TextView(getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.height = 370;
            lvAntipasti.setLayoutParams(params);

//            tvAntipasti.setTextSize(18);
//            tvAntipasti.setTextColor(Color.BLACK);
//            tvAntipasti.setPadding(0, 20, 0, 0);

            lvAntipasti.setAdapter(ca);

//            tvAntipasti.setText(header);

//            layout.addView(tvAntipasti);
            layout.addView(lvAntipasti);
        }
    }

    @Override
    public void doAction(PiattoOrdinato piattoOrdinato) {
        ordine.remove(piattoOrdinato);
        attachPiatto(ordine, lv_ordini, "antipasti");
    }
}
