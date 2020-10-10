package it.unisa.smartrestaurantapp.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TavoloTest {
    private Tavolo tavolo;
    private PiattoOrdinato po;
    private Piatto p;
    private Comanda comanda;

    @Before
    public void setUp() throws Exception {
        String username = "tavolo1";
        String password = "pass";
        String nome = "Tavolo 1";
        Integer posti = 6;
        Boolean libero = true;
        Boolean vuolePagare = false;
        boolean stato = false;
        String data = "2020/18/02";
        int id = 1;

        ArrayList<PiattoOrdinato> piattiOrdinati = new ArrayList<>();
        po = new PiattoOrdinato();

        p = new Piatto();
        p.setId(1l);
        p.setNome("Nome");
        p.setCategoria("cat");
        p.setIngredienti("");
        p.setDescrizione("bello");
        p.setPrezzo(10f);
        p.setFoto("foto");

        po.setStato(true);
        po.setPiatto(p);
        po.setQuantita(1);
        po.setData(data);
        po.setNote("");

        piattiOrdinati.add(po);
        String recensione = "Bella";
        Float totale = 10f;

        comanda = new Comanda();
        comanda = new Comanda();
        comanda.setId(id);
        comanda.setStato(stato);
        comanda.setData(data);
        comanda.setPiattiOrdinati(piattiOrdinati);
        comanda.setRecensione(recensione);

        tavolo = new Tavolo();
        tavolo = new Tavolo(username, password, nome, posti, libero, vuolePagare);

        Assert.assertEquals(tavolo.toString(), "Tavolo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + nome + '\'' +
                ", posti=" + posti +
                ", libero=" + libero +
                '}');
    }

    @Test
    public void setUsername() {
        tavolo.setUsername("ciao");
        Assert.assertEquals(tavolo.getUsername(), "ciao");
    }

    @Test
    public void setPassword() {
        tavolo.setPassword("ciaociao");
        Assert.assertEquals(tavolo.getPassword(), "ciaociao");
    }

    @Test
    public void setNome() {
        tavolo.setNome("NOME");
        Assert.assertEquals(tavolo.getNome(), "NOME");
    }

    @Test
    public void setPosti() {
        tavolo.setPosti(6);
        Assert.assertEquals(tavolo.getPosti(), (Integer)6);
    }

    @Test
    public void setLibero() {
        tavolo.setLibero(true);
        Assert.assertEquals(tavolo.isLibero(), true);
    }

    @Test
    public void setVuolePagare() {
        tavolo.setVuolePagare(true);
        Assert.assertEquals(tavolo.vuolePagare(), true);
    }

    @Test
    public void setComanda() {
        tavolo.setComanda(comanda);
        Assert.assertEquals(tavolo.getComanda(), comanda);
    }

    @Test
    public void setCarrello() {
        ArrayList<PiattoOrdinato> carrello = new ArrayList<>();
        tavolo.setCarrello(carrello);
        Assert.assertEquals(tavolo.getCarrello(), carrello);
    }

    @Test
    public void addToCart() {
        tavolo.addToCart(po);
        Assert.assertEquals(tavolo.getCarrello().get(0), po);
    }

    @Test
    public void spostaDalCarrelloAllaComanda() {
        tavolo.spostaDalCarrelloAllaComanda();
    }
}