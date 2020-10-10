package it.unisa.smartrestaurantapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Piatto;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;

public class TvDishDetails extends AppCompatActivity {
    private Tavolo t;
    private Piatto p;

    private LinearLayout llAllergeni;
    private TextView tvNome;
    private TextView tvDescrizione;
    private TextView tvQuantiy;
    private EditText etAllergie;
    private ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_dish_details);

        t = (Tavolo) getIntent().getSerializableExtra("account");
        p = (Piatto) getIntent().getSerializableExtra("piatto");

        llAllergeni = findViewById(R.id.ll_allergeni);
        tvNome = findViewById(R.id.tv_nome);
        tvDescrizione = findViewById(R.id.tv_descrizione);
        tvQuantiy = findViewById(R.id.tv_quantity);
        etAllergie = findViewById(R.id.et_allergie);
        ivFoto = findViewById(R.id.iv_foto);

        tvNome.setText(p.getNome());
        tvDescrizione.setText(p.getDescrizione());
        int resourceId = this.getResources().getIdentifier(p.getFoto(), "drawable", this.getPackageName());
        ivFoto.setImageResource(resourceId);
    }

    /**
     * Aggiunge o rimuove piatti dal carrello
     * @param v
     */
    public void changeQuantity(View v) {
        Button b = (Button) v;

        if(b.getText().equals("+")) {
            int q = Integer.parseInt(tvQuantiy.getText().toString());
            tvQuantiy.setText(q + 1 + "");
        }
        else if(!tvQuantiy.getText().equals("1")) {
            int q = Integer.parseInt(tvQuantiy.getText().toString());
            tvQuantiy.setText(q - 1 + "");
        }
    }

    /**
     * Abilita il campo di testo per indicare le allergie
     * @param v
     */
    public void check(View v) {
        CheckBox c = (CheckBox) v;

        if(c.isChecked())
            etAllergie.setVisibility(View.VISIBLE);
        else
            etAllergie.setVisibility(View.INVISIBLE);
    }

    /**
     * Completa l'ordine e lo salva nel carrello
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void complete(View v) {
        int q = Integer.parseInt(tvQuantiy.getText().toString());

        Log.d("TV", "Invio al carrello " + q + " quantità");

        if(q == 0)
            close(null);
        else {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = localDateTime.format(formatter);

            String note = etAllergie.getText().toString();

            if(etAllergie.isShown() && (note.length() == 0 || note == null || note.length() > 255)) {
                if (note.length() > 255) {
                    Toast.makeText(getApplicationContext(), "Lunghezza massima per le note: 255", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Inserire le note", Toast.LENGTH_LONG).show();
                }
            } else {
                PiattoOrdinato po = new PiattoOrdinato(p, false, formattedDateTime, note);
                po.setQuantita(q);
                t.addToCart(po);

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                i.putExtra("account", t);

                finish();
            }
        }
    }

    /**
     * Riporta alla schemata precedente
     * @param v
     */
    public void close(View v) {
        Intent i = new Intent();

        setResult(RESULT_CANCELED, i);

        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
