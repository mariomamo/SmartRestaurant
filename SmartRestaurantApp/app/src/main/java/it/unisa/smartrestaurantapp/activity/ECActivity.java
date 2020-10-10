package it.unisa.smartrestaurantapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.item.Fragment.ECLeftMenuFragment;
import it.unisa.smartrestaurantapp.item.Fragment.ECOrderFragment;

public class ECActivity extends AppCompatActivity {
    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private ECOrderFragment ecOrderFragment = new ECOrderFragment();
    private ECLeftMenuFragment ecLeftMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ec_main);

        //Creo l'oggetto fragment per il menù
        ecLeftMenuFragment = new ECLeftMenuFragment();

        //Ottengo il fragment manager
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.leftMenuContainer, ecLeftMenuFragment, "ec_view_layout_fragment");
        ft.commit();
    }

    /***
     * Cambia il frame 'centrale' con quello che gli viene passato
     * @param fragment frame da inserire
     */
    public static void changeCenterView(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.ECcenterViewContainer, fragment);
        ft.commit();
    }

    /**
     * Richiede il logout
     * @param v
     */
    public void logout(View v) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void onClickPositive(View v) {
        Button b = (Button) v;

        Toast.makeText(getApplicationContext(), "Positive " + b.getTag(), Toast.LENGTH_SHORT);
        Log.d("EC", "[" + getClass().getSimpleName() + "] Positive " + b.getTag());
    }

    @Override
    public void onBackPressed() {

    }
}
