package it.unisa.smartrestaurantapp.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PiattoOrdinatoTest {
    private PiattoOrdinato po;
    private Piatto p = new Piatto();

    @Before
    public void setUp() throws Exception {
        Long id = 1l;
        boolean stato = true;
        String data = "2020/02/17 22:40";

        p.setId(1l);
        p.setNome("Nome");
        p.setCategoria("cat");
        p.setIngredienti("");
        p.setDescrizione("bello");
        p.setPrezzo(10f);
        p.setFoto("foto");

        po = new PiattoOrdinato(p, stato, data, "");
        po = new PiattoOrdinato(id, p, stato, data, "");

        Assert.assertEquals(po.toString(), "PiattoOrdinato{" +
                "id=" + id +
                ", piatto=" + p +
                ", stato=" + stato +
                ", data=" + data +
                ", note=''}");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setId() {
        po.setId(2l);
        Assert.assertEquals(po.getId(), (Long)2l);
    }

    @Test
    public void setPiatto() {
    }

    @Test
    public void setStato() {
    }

    @Test
    public void setData() {
    }

    @Test
    public void setNote() {
    }

    @Test
    public void setQuantita() {
    }
}