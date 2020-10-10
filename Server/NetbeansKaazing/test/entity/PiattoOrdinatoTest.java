/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Syrenne
 */
public class PiattoOrdinatoTest {
    
    public PiattoOrdinatoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getComanda method, of class PiattoOrdinato.
     */
    @Test
    public void testGetComanda() {
        System.out.println("getComanda");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        int expResult = 1;
        int result = instance.getComanda();
        assertEquals(expResult, result);
    }

    /**
     * Test of setComanda method, of class PiattoOrdinato.
     */
    @Test
    public void testSetComanda() {
        System.out.println("setComanda");
        int comanda = 0;
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setComanda(comanda);
        int result = instance.getComanda();
        assertEquals(comanda, result);
    }

    /**
     * Test of getId method, of class PiattoOrdinato.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        Long expResult = 1L;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class PiattoOrdinato.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = 2L;
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setId(id);
        Long result = instance.getId();
        assertEquals(id, result);
    }

    /**
     * Test of getPiatto method, of class PiattoOrdinato.
     */
    @Test
    public void testGetPiatto() {
        System.out.println("getPiatto");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        Piatto expResult = new Piatto();
        Piatto result = instance.getPiatto();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPiatto method, of class PiattoOrdinato.
     */
    @Test
    public void testSetPiatto() {
        System.out.println("setPiatto");
        Piatto piatto = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setPiatto(piatto);
        Piatto result = instance.getPiatto();
        assertEquals(piatto, result);
    }

    /**
     * Test of getStato method, of class PiattoOrdinato.
     */
    @Test
    public void testGetStato() {
        System.out.println("getStato");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        Boolean expResult = false;
        Boolean result = instance.getStato();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStato method, of class PiattoOrdinato.
     */
    @Test
    public void testSetStato() {
        System.out.println("setStato");
        Boolean stato = true;
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setStato(stato);
        Boolean result = instance.getStato();
        assertEquals(stato, result);
    }

    /**
     * Test of getData method, of class PiattoOrdinato.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        String expResult = "2020/02/02";
        String result = instance.getData();
        assertEquals(expResult, result);
    }

    /**
     * Test of setData method, of class PiattoOrdinato.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "2020/02/03";
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setData(data);
        String result = instance.getData();
        assertEquals(data, result);
    }

    /**
     * Test of getNote method, of class PiattoOrdinato.
     */
    @Test
    public void testGetNote() {
        System.out.println("getNote");
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        String expResult = "note";
        String result = instance.getNote();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNote method, of class PiattoOrdinato.
     */
    @Test
    public void testSetNote() {
        System.out.println("setNote");
        String note = "new note";
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setNote(note);
        String result = instance.getNote();
        assertEquals(note, result);
    }

    /**
     * Test of getQuantita method, of class PiattoOrdinato.
     */
    @Test
    public void testGetQuantita() {
        System.out.println("getQuantita");
        PiattoOrdinato instance = new PiattoOrdinato(new Piatto(), false, "2020/02/02", "note", 1);
        int expResult = 1;
        int result = instance.getQuantita();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuantita method, of class PiattoOrdinato.
     */
    @Test
    public void testSetQuantita() {
        System.out.println("setQuantita");
        int quantita = 2;
        PiattoOrdinato instance = new PiattoOrdinato(1L, new Piatto(), false, "2020/02/02", "note", 1);
        instance.setQuantita(quantita);
        int result = instance.getQuantita();
        assertEquals(quantita, result);
    }
}
