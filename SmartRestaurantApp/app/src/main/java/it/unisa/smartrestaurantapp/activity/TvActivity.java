package it.unisa.smartrestaurantapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.Fragment.TvCartFragment;
import it.unisa.smartrestaurantapp.item.Fragment.TvCloseMealFragment;
import it.unisa.smartrestaurantapp.item.Fragment.TvLeftMenuFragment;
import it.unisa.smartrestaurantapp.service.NotificaService;

public class TvActivity extends AppCompatActivity implements TvActivityCallback {

    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private Tavolo tavolo;
    private TvLeftMenuFragment tvLeftMenuFragment;

    private TextView tvNomeTavolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_main);

        //Creo l'oggetto fragment per il menù
        tvLeftMenuFragment = new TvLeftMenuFragment(this);

        //Ottengo il fragment manager
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.leftMenuContainer, tvLeftMenuFragment, "tv_view_layout_fragment");
        ft.commit();

        //Recupero il riferimento al Tavolo
        tavolo = (Tavolo) getIntent().getSerializableExtra("account");

        tvNomeTavolo = findViewById(R.id.nomeTavolo);

        tvNomeTavolo.setText(tavolo.getNome());
    }

    /***
     * Cambia il frame 'centrale' con quello che gli viene passato
     * @param fragment frame da inserire
     */
    public static void changeCenterView(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.centerViewContainer, fragment, "tv_view_layout_fragment");
        ft.commit();
    }

    /**
     * Apre la schermata relativa al carrello degli acquisti
     * @param v button cliccato
     */
    public void carrello(View v) {
        tvLeftMenuFragment.removeMenuClick();

        changeCenterView(new TvCartFragment(this));
    }

    /**
     * Apre la schermata relativa al riepilogo e chiusura pasto
     * @param v button cliccato
     */
    public void chiudiPasto(View v) {
        tvLeftMenuFragment.removeMenuClick();

        changeCenterView(new TvCloseMealFragment(this));
    }

    /**
     * Richiede il logout dall'account Tavolo
     * @param v
     */
    public void logout(View v) {
        dialogLogout();
    }

    /**
     * Fa comparire una dialogBox per l'inserimento della password del Tavolo
     */
    private void dialogLogout(){
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.dialog_box_login, null);

        Log.d("TV", "Creating AlertDialog.Builder");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setMessage("Inserire la password");

        final EditText etPassword = view.findViewById(R.id.etAlertPassword);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int witch) {
                String password = etPassword.getText().toString();

                try{
                    if(password.length() > 0 && password.equals(tavolo.getPassword())) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password errata", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error in onClick DialogBuilder: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Indietro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }

    @Override
    public Tavolo getAccount() {
        return tavolo;
    }

    @Override
    public void update(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    @Override
    public void onBackPressed() {
        
    }
}
