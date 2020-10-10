package it.unisa.smartrestaurantapp.entity;

import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account();
        account.setNome("Gino");
        account.setUsername("Proprietario");
        account.setPassword("pass");
        account.setTipo(Account.TYPE_PROPRIETARIO);
        Assert.assertEquals(account.toString(), "Account{" +
                "username='Proprietario'" +
                ", password='pass'" +
                ", nome='Gino'" +
                ", tipo=" + Account.TYPE_PROPRIETARIO +
                '}');

        account = new Account("Proprietario", "pass", "Gino", Account.TYPE_PROPRIETARIO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUsername() {
        Assert.assertEquals(account.getNome(), "Gino");
    }

    @Test
    public void setUsername() {
        Assert.assertEquals(account.getUsername(), "Proprietario");
    }

    @Test
    public void getPassword() {
        Assert.assertEquals(account.getPassword(), "pass");
    }

    @Test
    public void setPassword() {
        account.setPassword("pass2");
        Assert.assertEquals(account.getPassword(), "pass2");
    }

    @Test
    public void getNome() {
        Assert.assertEquals(account.getNome(), "Gino");
    }

    @Test
    public void setNome() {
        account.setNome("Mario");
        Assert.assertEquals(account.getNome(), "Mario");
    }

    @Test
    public void getTipo() {
        Assert.assertEquals(account.getTipo(), (Integer)Account.TYPE_PROPRIETARIO);
    }

    @Test
    public void setTipo() {
        account.setTipo(Account.TYPE_EXECUTIVE_CHEF);
        Assert.assertEquals(account.getTipo(), (Integer)Account.TYPE_EXECUTIVE_CHEF);
    }
}