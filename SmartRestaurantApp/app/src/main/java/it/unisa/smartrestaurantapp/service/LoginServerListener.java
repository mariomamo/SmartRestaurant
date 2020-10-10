package it.unisa.smartrestaurantapp.service;

import it.unisa.smartrestaurantapp.entity.Account;
import it.unisa.smartrestaurantapp.entity.Tavolo;

public interface LoginServerListener {
    void login(Account account);
    void login(Tavolo tavolo);
    void loginError(String msg);
}
