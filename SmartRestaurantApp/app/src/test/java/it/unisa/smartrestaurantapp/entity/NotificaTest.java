package it.unisa.smartrestaurantapp.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotificaTest {
    private Notifica notifica;

    @Before
    public void setUp() throws Exception {
        long id = 1l;
        String destinatario = "mario";
        String categoria = Notifica.Categoria.PAGAMENTO;
        String mittente = "tavolo1";
        String testo = "voglio pagare";
        boolean letta = false;
        String data = "2020/18/02 00:21";

        notifica = new Notifica();
        notifica = new Notifica(id, destinatario, categoria, mittente, testo, letta, data);
        Assert.assertEquals(notifica.toString(), "Notifica{" +
                "id=" + id +
                ", destinatario='" + destinatario + '\'' +
                ", categoria='" + categoria + '\'' +
                ", mittente='" + mittente + '\'' +
                ", testo='" + testo + '\'' +
                ", letta=" + letta +
                ", data='" + data + '\'' +
                '}');
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setId() {
        notifica.setId(2l);
        Assert.assertEquals(notifica.getId(), 2l);
    }

    @Test
    public void setDestinatario() {
        notifica.setDestinatario("nicola");
        Assert.assertEquals(notifica.getDestinatario(), "nicola");
    }

    @Test
    public void setMittente() {
        notifica.setMittente("andrea");
        Assert.assertEquals(notifica.getMittente(), "andrea");
    }

    @Test
    public void setTesto() {
        notifica.setTesto("ciao");
        Assert.assertEquals(notifica.getTesto(), "ciao");
    }

    @Test
    public void setLetta() {
        notifica.setLetta(true);
        Assert.assertEquals(notifica.isLetta(), true);
    }

    @Test
    public void setCategoria() {
        notifica.setCategoria(Notifica.Categoria.AIUTO);
        Assert.assertEquals(notifica.getCategoria(), Notifica.Categoria.AIUTO);
    }

    @Test
    public void setData() {
        notifica.setData("2020/18/02");
        Assert.assertEquals(notifica.getData(), "2020/18/02");
    }
}