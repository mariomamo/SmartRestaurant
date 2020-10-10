package it.unisa.smartrestaurantapp.activity;

import android.os.Parcelable;

import it.unisa.smartrestaurantapp.entity.Tavolo;

public interface TvActivityCallback {
    Tavolo getAccount();
    void update(Tavolo tavolo);
}
