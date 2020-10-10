package it.unisa.smartrestaurantapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Account;
import it.unisa.smartrestaurantapp.entity.PiattoOrdinato;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.service.LoginService;
import it.unisa.smartrestaurantapp.service.NotificaService;
import it.unisa.smartrestaurantapp.service.LoginServerListener;

public class LoginActivity extends AppCompatActivity {
    public LinearLayout llError;
    public EditText etUsername;
    public EditText etPassword;
    private TextView tv_errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        llError = findViewById(R.id.error);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        tv_errore = findViewById(R.id.tv_errore);
    }

    /**
     * Verifica l'esistenza dell'Account o del Tavolo
     * @param v la view rappresentante il button cliccato
     */
    public void login(View v) {
        final LoginService loginService = new LoginService(new LoginServerListener() {
            @Override
            public void login(Account account) {
                Log.d("MY-DEBUG-LOGIN", "Login effettuato: " + account.getUsername() + " tipo: " + account.getTipo());

                llError.setVisibility(View.INVISIBLE);

                Intent i = null;
                if (account.getTipo() == Account.TYPE_PROPRIETARIO) {
                    i = new Intent(getApplicationContext(), PrActivity.class);
                }
                else if (account.getTipo() == Account.TYPE_CAPO_CAMERIERE) {
                    i = new Intent(getApplicationContext(), CCActivity.class);
                }
                else if (account.getTipo() == Account.TYPE_EXECUTIVE_CHEF) {
                    i = new Intent(getApplicationContext(), ECActivity.class);
                }

                if (i != null) {
                    i.putExtra("account", account);
                    startActivity(i);
                }
            }

            @Override
            public void login(Tavolo tavolo) {
                Log.d("MY-DEBUG-LOGIN", "Login effettuato per: " + tavolo.getUsername());

//                for (PiattoOrdinato po : tavolo.getComanda().getPiattiOrdinati()) {
//                    Log.d("MY-DEBUG-LOGIN", "Login effettuato per: " + po.getPiatto().getNome());
//                }

                llError.setVisibility(View.INVISIBLE);

                Intent i = new Intent(getApplicationContext(), TvActivity.class);
                i.putExtra("account", tavolo);
                startActivity(i);
            }

            @Override
            public void loginError(String msg) {
                llError.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_errore.setText("Credenziali errate");
                        llError.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        //Controllo che il campo username o password non sia vuoto
        if (etUsername.getText().length() == 0) {
            llError.post(new Runnable() {
                @Override
                public void run() {
                    tv_errore.setText("Inserire un username");
                    llError.setVisibility(View.VISIBLE);
                }
            });
        } else if (etPassword.getText().length() == 0) {
            llError.post(new Runnable() {
                @Override
                public void run() {
                    tv_errore.setText("Inserire una password");
                    llError.setVisibility(View.VISIBLE);
                }
            });
        } else {
            llError.setVisibility(View.INVISIBLE);
            loginService.checkLogin(etUsername.getText().toString(), etPassword.getText().toString());
        }
    }

    /**
     * Impedisce il backpressed
     */
    @Override
    public void onBackPressed() { }
}
