package it.unisa.smartrestaurantapp.item.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.activity.CCActivity;
import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.item.VisualizzatoreTavoliAstratto;
import it.unisa.smartrestaurantapp.service.TavoloService;

/**
 * Questo metodo viene utilizzato per realizzare la grafica
 * del CapoCameriere per rendere un tavolo libero
 * o occupato.
 */
public class CCTavoliViewGenerale extends Fragment implements VisualizzatoreTavoliAstratto {
    private TextView username;
    private TextView password;
    private TextView nome;
    private TextView posti;
    private Button bt_azione;
    private boolean occupato;
    private TavoloService tavoloService;

    public CCTavoliViewGenerale() {
        this.tavoloService = new TavoloService();
    }

    /**
     * Costruttore del frammento per visualizzare i tavoli
     * @param occupato se uguale a true allora il frammento viene utilizzato per gestire
     *                 i tavoli liberi. Altrimenti quelli occupati
     */
    public CCTavoliViewGenerale(boolean occupato) {
        this.occupato = occupato;
    }

    /**
     * Crea la view ed inserisce all'interno i relativi tavoli
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cc_view_tavoli_liberi_fragment, null, false);

        //Ottengo i riferimenti agli oggetti grafici
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        nome = view.findViewById(R.id.nome);
        posti = view.findViewById(R.id.posti);
        bt_azione = view.findViewById(R.id.bt_azione);

        //Preparo il bottone 'Occupa' oppure 'Libera'
        if (occupato) {
            bt_azione.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bordo_rosso_state));
            bt_azione.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttonCanc));
            bt_azione.setText("Libera");
        }
        else {
            bt_azione.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.bordo_verde_state));
            bt_azione.setTextColor(ContextCompat.getColor(view.getContext(), R.color.buttonSave));
            bt_azione.setText("Occupa");
        }

        //Setto l'OnClick
        bt_azione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tavolo tavolo = new Tavolo();
                tavolo.setUsername(username.getText().toString());
                tavolo.setPassword(password.getText().toString());
                tavolo.setNome(nome.getText().toString());
                tavolo.setPosti(Integer.parseInt(posti.getText().toString()));
                tavolo.setLibero(occupato);

                CCActivity.getService().sendChangeStateTavolo(tavolo);

                //Aggiorno la pagina
                CCActivity.changeCenterView(new CCTavoliFragment(new CCTavoliViewGenerale(occupato)));
            }
        });

        return view;
    }

    /**
     * Richiedo i nuovi tavoli
     * @param service TavoloService dal quale prelevare i dati
     * @param UUID stringa univoca per identificare la richiesta
     */
    @Override
    public void requestTavoli(TavoloService service, String UUID) {
        if (occupato)
            CCActivity.getService().requestTavoliLiberi(UUID);
        else
            CCActivity.getService().requestTavoliOccupati(UUID);
    }

    /**
     * Cambia i dati della view con quelli di un nuovo tavolo
     * @param tavolo tavolo contenente i nuovi dati
     */
    @Override
    public void cambiaDati(Tavolo tavolo) {
        username.setText(tavolo.getUsername());
        password.setText(tavolo.getPassword());
        nome.setText(tavolo.getNome());
        posti.setText(tavolo.getPosti() + "");
    }

    @Override
    public int isOccupato() {
        if(occupato)
            return 1;
        else
            return 0;
    }
}
