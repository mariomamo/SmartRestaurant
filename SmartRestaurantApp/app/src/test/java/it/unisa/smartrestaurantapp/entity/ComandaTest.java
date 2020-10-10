package it.unisa.smartrestaurantapp.entity;

import org.assertj.core.api.AssertDelegateTarget;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ComandaTest {
    private Comanda comanda;
    private ArrayList<PiattoOrdinato> piattiOrdinati;
    private Piatto p = new Piatto();
    private PiattoOrdinato po;

    @Before
    public void setUp() throws Exception {
        int id = 1;
        boolean stato = true;
        String data = "2020/02/17 22:40";

        piattiOrdinati = new ArrayList<>();
        po = new PiattoOrdinato();

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
        comanda.setId(id);
        comanda.setStato(stato);
        comanda.setData(data);
        comanda.setPiattiOrdinati(piattiOrdinati);
        comanda.setRecensione(recensione);
        Assert.assertEquals(comanda.toString(), "Comanda{" +
                "id=" + id +
                ", stato=" + stato +
                ", data=" + data +
                ", piattoOrdinato=" + piattiOrdinati +
                ", recensione='" + recensione + '\'' +
                ", totale=" + totale +
                '}');
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
        Assert.assertEquals(comanda.getId(), (Integer)1);
    }

    @Test
    public void setId() {
        comanda.setId(2);
        Assert.assertEquals(comanda.getId(), (Integer)2);
    }

    @Test
    public void getStato() {
        Assert.assertEquals(comanda.getStato(), true);
    }

    @Test
    public void setStato() {
        comanda.setStato(false);
        Assert.assertEquals(comanda.getStato(), false);
    }

    @Test
    public void getData() {
        Assert.assertEquals(comanda.getData(), "2020/02/17 22:40");
    }

    @Test
    public void setData() {
        comanda.setData("2020/02/18 22:40");
        Assert.assertEquals(comanda.getData(), "2020/02/18 22:40");
    }

    @Test
    public void getPiattiOrdinati() {
        Assert.assertEquals(comanda.getPiattiOrdinati(), piattiOrdinati);
    }

    @Test
    public void setPiattiOrdinati() {
        ArrayList<PiattoOrdinato> lista = new ArrayList<>();
        PiattoOrdinato po = new PiattoOrdinato();

        po.setPiatto(p);
        po.setStato(true);
        po.setPiatto(p);
        po.setQuantita(1);
        po.setData("2020/02/17 22:40");
        po.setNote("");
        lista.add(po);
        comanda.setPiattiOrdinati(lista);
        Assert.assertEquals(comanda.getPiattiOrdinati(), lista);
    }

    @Test
    public void addPiatto() {
        comanda.addPiatto(po);
        Assert.assertEquals(comanda.getPiattiOrdinati().get(1), po);
    }

    @Test
    public void getRecensione() {
        Assert.assertEquals(comanda.getRecensione(), "Bella");
    }

    @Test
    public void setRecensione() {
        comanda.setRecensione("Ci torno sicuro");
        Assert.assertEquals(comanda.getRecensione(), "Ci torno sicuro");
    }

    @Test
    public void getTotale() {
        Assert.assertEquals(comanda.getTotale(), (Float)10f);
    }
}