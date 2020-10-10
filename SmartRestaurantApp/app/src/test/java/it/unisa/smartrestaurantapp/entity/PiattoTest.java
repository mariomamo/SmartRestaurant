package it.unisa.smartrestaurantapp.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PiattoTest {
    private Piatto piatto;

    @Before
    public void setUp() throws Exception {
        Long id = 1l;
        String nome = "Nome";
        String categoria = "cat";
        String descrizione = "bello";
        String ingredienti = "";
        Float prezzo = 10f;
        String foto = "foto";

        piatto = new Piatto(nome, categoria, descrizione, ingredienti, prezzo, foto);
        piatto = new Piatto(id, nome, categoria, descrizione, ingredienti, prezzo, foto);
        Assert.assertEquals(piatto.toString(), "Piatto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", ingredienti='" + ingredienti + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", foto=" + foto +
                '}');
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
        Assert.assertEquals(piatto.getId(), (Long)1l);
    }

    @Test
    public void setId() {
        piatto.setId(2l);
        Assert.assertEquals(piatto.getId(), (Long)2l);
    }

    @Test
    public void getNome() {
        Assert.assertEquals(piatto.getNome(), "Nome");
    }

    @Test
    public void setNome() {
        piatto.setNome("nome");
        Assert.assertEquals(piatto.getNome(), "nome");
    }

    @Test
    public void getCategoria() {
        Assert.assertEquals(piatto.getCategoria(), "cat");
    }

    @Test
    public void setCategoria() {
        piatto.setCategoria("categoria");
        Assert.assertEquals(piatto.getCategoria(), "categoria");
    }

    @Test
    public void getIngredienti() {
        Assert.assertEquals(piatto.getIngredienti(), "");
    }

    @Test
    public void setIngredienti() {
        piatto.setIngredienti("tanti");
        Assert.assertEquals(piatto.getIngredienti(), "tanti");
    }

    @Test
    public void getDescrizione() {
        Assert.assertEquals(piatto.getDescrizione(), "bello");
    }

    @Test
    public void setDescrizione() {
        piatto.setDescrizione("ok");
        Assert.assertEquals(piatto.getDescrizione(), "ok");
    }

    @Test
    public void getPrezzo() {
        Assert.assertEquals(piatto.getPrezzo(), (Float)10f);
    }

    @Test
    public void setPrezzo() {
        piatto.setPrezzo(1f);
        Assert.assertEquals(piatto.getPrezzo(), (Float)1f);
    }

    @Test
    public void getFoto() {
        Assert.assertEquals(piatto.getFoto(), "foto");
    }

    @Test
    public void setFoto() {
        piatto.setFoto("foto");
        Assert.assertEquals(piatto.getFoto(), "foto");
    }
}