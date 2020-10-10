package it.unisa.smartrestaurantapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Piatto;

public class ECDishDetails extends AppCompatActivity {
    public Piatto p;

    public TextView tvNome;
    public TextView tvDescrizione;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ec_dish_details);

        p = (Piatto) getIntent().getSerializableExtra("piatto");

        Log.d("EC", "Pop-up per piatto " + p);

        tvNome = findViewById(R.id.tv_nome);
        tvDescrizione = findViewById(R.id.tv_descrizione);
        ivPhoto = findViewById(R.id.iv_foto);

        tvNome.setText(p.getNome());
        tvDescrizione.setText(p.getDescrizione());
        int resourceId = this.getResources().getIdentifier(p.getFoto(), "drawable", this.getPackageName());
        ivPhoto.setImageResource(resourceId);
    }

    /**
     * Riporta alla schemata precedente
     * @param v
     */
    public void close(View v) {
        Log.d("EC", "Click on close");

        Intent i = new Intent();

        setResult(RESULT_CANCELED, i);

        finish();
    }
}
