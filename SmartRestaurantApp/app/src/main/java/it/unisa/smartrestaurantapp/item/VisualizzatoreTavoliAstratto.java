package it.unisa.smartrestaurantapp.item;

import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.Collection;

import it.unisa.smartrestaurantapp.entity.Tavolo;
import it.unisa.smartrestaurantapp.service.TavoloService;

/**
 * Questa interfaccia contiene i due metodi 'cambiaDati' e 'getTavoli'.
 * Il metodo 'cambiaDati' è un metodo che si occupa di come devono
 * essere aggiornati i dati (modifica della grafica).
 * Il metodo 'getTavoli' specifica il criterio con il quale
 * i tavoli devono essere restituiti.
 * Questa interfaccia deve essere implementata da un frammento
 * che viene passato come argomento ad un frammento
 * CCTavoliFragment.
 */
public interface VisualizzatoreTavoliAstratto {
    /**
     * Questo metodo restituisce una lista di tavoli
     * @param service TavoloService dal quale prelevare i dati
     * @return Lista di tavoli
     */
    void requestTavoli(TavoloService service, String UUD);

    /**
     * Questo metodo si occupa di gestire il
     * refresh dei dati quando cambiano
     * @param tavolo tavolo
     */
    void cambiaDati(Tavolo tavolo);

    int isOccupato();
}
