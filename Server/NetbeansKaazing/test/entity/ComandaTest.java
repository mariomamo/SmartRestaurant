/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
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
public class ComandaTest {
    
    public ComandaTest() {
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
     * Test of getId method, of class Comanda.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Comanda instance = new Comanda();
        instance.setId(1);        
        Integer expResult = 1;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Comanda.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer id = 1;
        Comanda instance = new Comanda();
        instance.setId(id);
        Integer result = instance.getId();
        assertEquals(id, result);
    }

    /**
     * Test of getStato method, of class Comanda.
     */
    @Test
    public void testGetStato() {
        System.out.println("getStato");
        Comanda instance = new Comanda();
        instance.setStato(false);
        Boolean expResult = false;
        Boolean result = instance.getStato();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStato method, of class Comanda.
     */
    @Test
    public void testSetStato() {
        System.out.println("setStato");
        Boolean stato = true;
        Comanda instance = new Comanda();
        instance.setStato(stato);
        Boolean result = instance.getStato();
        assertEquals(stato, result);
    }

    /**
     * Test of getData method, of class Comanda.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Comanda instance = new Comanda();
        instance.setData("2020/02/02");
        String expResult = "2020/02/02";
        String result = instance.getData();
        assertEquals(expResult, result);
    }

    /**
     * Test of setData method, of class Comanda.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "2020/02/02";
        Comanda instance = new Comanda();
        instance.setData(data);
        String result = instance.getData();
        assertEquals(data, result);
    }

    /**
     * Test of getPiattiOrdinati method, of class Comanda.
     */
    @Test
    public void testGetPiattiOrdinati() {
        System.out.println("getPiattiOrdinati");
        Comanda instance = new Comanda();
        instance.setPiattiOrdinati(new ArrayList<>());
        ArrayList<PiattoOrdinato> expResult = new ArrayList<>();
        ArrayList<PiattoOrdinato> result = instance.getPiattiOrdinati();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPiattiOrdinati method, of class Comanda.
     */
    @Test
    public void testSetPiattiOrdinati() {
        System.out.println("setPiattiOrdinati");
        ArrayList<PiattoOrdinato> piattiOrdinati = new ArrayList<>();
        Comanda instance = new Comanda();
        instance.setPiattiOrdinati(piattiOrdinati);
        ArrayList<PiattoOrdinato> result = instance.getPiattiOrdinati();
        assertEquals(piattiOrdinati, result);
    }

    /**
     * Test of addPiatto method, of class Comanda.
     */
    @Test
    public void testAddPiatto() {
        System.out.println("addPiatto");
        PiattoOrdinato piattoOrdinato = new PiattoOrdinato();
        Comanda instance = new Comanda();
        instance.addPiatto(piattoOrdinato);
        PiattoOrdinato result = instance.getPiattiOrdinati().get(0);
        assertEquals(piattoOrdinato, result);
    }

    /**
     * Test of getRecensione method, of class Comanda.
     */
    @Test
    public void testGetRecensione() {
        System.out.println("getRecensione");
        Comanda instance = new Comanda();
        instance.setRecensione("recensione");
        String expResult = "recensione";
        String result = instance.getRecensione();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRecensione method, of class Comanda.
     */
    @Test
    public void testSetRecensione() {
        System.out.println("setRecensione");
        String recensione = "recensione";
        Comanda instance = new Comanda();
        instance.setRecensione(recensione);
        String result = instance.getRecensione();
        assertEquals(recensione, result);
    }

    /**
     * Test of getTotale method, of class Comanda.
     */
    @Test
    public void testGetTotale() {
        System.out.println("getTotale");
        Comanda instance = new Comanda();
        Float expResult = 0f;
        Float result = instance.getTotale();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTotale method, of class Comanda.
     */
    @Test
    public void testSetTotale() {
        System.out.println("setTotale");
        Float totale = 0f;
        Comanda instance = new Comanda();
        instance.setTotale(totale);
        Float result = instance.getTotale();
        assertEquals(totale, result);
    }

    /**
     * Test of toString method, of class Comanda.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Comanda instance = new Comanda();
        String expResult = "Comanda{id=null, stato=null, data=null, piattoOrdinato=[], recensione='null', totale=null}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Comanda.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Comanda obj = new Comanda();
        Comanda instance = new Comanda();
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
       
        result = instance.equals(instance);
        expResult = true;
        assertEquals(expResult, result);
        
        result = instance.equals(null);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(2);
        obj.setData("2020/02/03");
        obj.setPiattiOrdinati(new ArrayList<>());
        obj.setRecensione("recensione");  
        obj.setTotale(10.0f);
        obj.setStato(false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(1);
        obj.setData("2020/02/03");
        obj.setPiattiOrdinati(new ArrayList<>());
        obj.setRecensione("recensione");  
        obj.setTotale(10.0f);
        obj.setStato(false);
        
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(1);
        obj.setData("2020/02/02");
        ArrayList<PiattoOrdinato> list = new ArrayList<>();
        list.add(new PiattoOrdinato());
        obj.setPiattiOrdinati(list);
        obj.setRecensione("recensione");  
        obj.setTotale(10.0f);
        obj.setStato(false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(1);
        obj.setData("2020/02/02");
        obj.setPiattiOrdinati(new ArrayList<>());
        obj.setRecensione("r");  
        obj.setTotale(10.0f);
        obj.setStato(false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(1);
        obj.setData("2020/02/02");
        obj.setPiattiOrdinati(new ArrayList<>());
        obj.setRecensione("recensione");  
        obj.setTotale(11.0f);
        obj.setStato(false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Comanda();
        obj.setId(1);
        obj.setData("2020/02/02");
        obj.setPiattiOrdinati(new ArrayList<>());
        obj.setRecensione("recensione");  
        obj.setTotale(10.0f);
        obj.setStato(true);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        Object object = new Tavolo();
        result = instance.equals(object);
        expResult = false;
        assertEquals(expResult, result);
    }
    
}
