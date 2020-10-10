package it.unisa.smartrestaurantapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.item.Fragment.PRLeftMenuFragment;
import it.unisa.smartrestaurantapp.service.TavoloService;

public class PrActivity extends AppCompatActivity {
    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static TavoloService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pr_main);
        service = new TavoloService();

        //Creo l'oggetto fragment per il menù
        PRLeftMenuFragment prLeftMenuFragment = new PRLeftMenuFragment();

        //Ottengo il fragment manager
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.leftMenuContainer, prLeftMenuFragment, "pr_view_layout_fragment");
        ft.commit();
    }

    /***
     * Cambia il frame 'centrale' con quello che gli viene passato
     * @param fragment frame da inserire
     */
    public static void changeCenterView(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.centerViewContainer, fragment, "pr_view_layout_fragment");
        ft.commit();
    }

    public static TavoloService getService() {
        return service;
    }

    /**
     * Richiede il logout
     * @param v
     */
    public void logout(View v) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    /**
     * Impedisce il backpressed
     */
    @Override
    public void onBackPressed() {

    }
}
