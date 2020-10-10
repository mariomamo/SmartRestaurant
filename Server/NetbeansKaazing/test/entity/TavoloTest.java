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
public class TavoloTest {
    
    public TavoloTest() {
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
     * Test of getUsername method, of class Tavolo.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Tavolo instance = new Tavolo();
        instance.setUsername("username");
        String expResult = "username";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUsername method, of class Tavolo.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "newUsername";
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setUsername(username);
        String result = instance.getUsername();
        assertEquals(username, result);
    }

    /**
     * Test of getPassword method, of class Tavolo.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Tavolo.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "newPassword";
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setPassword(password);
        String result = instance.getPassword();
        assertEquals(password, result);
    }

    /**
     * Test of getNome method, of class Tavolo.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        String expResult = "nome";
        String result = instance.getNome();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNome method, of class Tavolo.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "newNome";
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setNome(nome);
        String result = instance.getNome();
        assertEquals(nome, result);
    }

    /**
     * Test of getPosti method, of class Tavolo.
     */
    @Test
    public void testGetPosti() {
        System.out.println("getPosti");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        Integer expResult = 4;
        Integer result = instance.getPosti();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPosti method, of class Tavolo.
     */
    @Test
    public void testSetPosti() {
        System.out.println("setPosti");
        Integer posti = 5;
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setPosti(posti);
        Integer result = instance.getPosti();
        assertEquals(posti, result);
    }

    /**
     * Test of isLibero method, of class Tavolo.
     */
    @Test
    public void testIsLibero() {
        System.out.println("isLibero");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        Boolean expResult = false;
        Boolean result = instance.isLibero();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLibero method, of class Tavolo.
     */
    @Test
    public void testSetLibero() {
        System.out.println("setLibero");
        Boolean libero = true;
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setLibero(libero);
        Boolean result = instance.isLibero();
        assertEquals(libero, result);
    }

    /**
     * Test of vuolePagare method, of class Tavolo.
     */
    @Test
    public void testVuolePagare() {
        System.out.println("vuolePagare");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        boolean expResult = false;
        boolean result = instance.vuolePagare();
        assertEquals(expResult, result);
    }

    /**
     * Test of setVuolePagare method, of class Tavolo.
     */
    @Test
    public void testSetVuolePagare() {
        System.out.println("setVuolePagare");
        boolean v = true;
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setVuolePagare(v);
        boolean result = instance.vuolePagare();
        assertEquals(v, result);
    }

    /**
     * Test of getComanda method, of class Tavolo.
     */
    @Test
    public void testGetComanda() {
        System.out.println("getComanda");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        Comanda expResult = new Comanda();
        Comanda result = instance.getComanda();
        assertEquals(expResult, result);
    }

    /**
     * Test of setComanda method, of class Tavolo.
     */
    @Test
    public void testSetComanda() {
        System.out.println("setComanda");
        Comanda comanda = new Comanda();
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.setComanda(comanda);
        Comanda result = instance.getComanda();
        assertEquals(comanda, result);
    }

    /**
     * Test of getCarrello method, of class Tavolo.
     */
    @Test
    public void testGetCarrello() {
        System.out.println("getCarrello");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        ArrayList<PiattoOrdinato> expResult = new ArrayList<>();
        ArrayList<PiattoOrdinato> result = instance.getCarrello();
        assertEquals(expResult, result);
    }

    /**
     * Test of addToCart method, of class Tavolo.
     */
    @Test
    public void testAddToCart() {
        System.out.println("addToCart");
        PiattoOrdinato piattoOrdinato = new PiattoOrdinato();
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        instance.addToCart(piattoOrdinato);
        PiattoOrdinato result = instance.getCarrello().get(0);
        assertEquals(piattoOrdinato, result);
    }

    /**
     * Test of spostaDalCarrelloAllaComanda method, of class Tavolo.
     */
    @Test
    public void testSpostaDalCarrelloAllaComanda() {
        System.out.println("spostaDalCarrelloAllaComanda");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        ArrayList<PiattoOrdinato> expresult = instance.getCarrello();
        instance.spostaDalCarrelloAllaComanda();
        ArrayList<PiattoOrdinato> result = instance.getComanda().getPiattiOrdinati();
        assertEquals(expresult, result);
    }

    /**
     * Test of toString method, of class Tavolo.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        String expResult = "Tavolo{username='username', password='password', name='nome', posti=4, libero=false}";
        String result = instance.toString();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Tavolo.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Tavolo("username", "password", "nome", 4, false, false);
        Tavolo instance = new Tavolo("username", "password", "nome", 4, false, false);
        
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
       
        result = instance.equals(instance);
        expResult = true;
        assertEquals(expResult, result);
        
        result = instance.equals(null);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo("username", "password", "n", 4, false, false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo("username", "password", "nome", 5, false, false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo("username", "password", "nome", 4, true, false);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo("username", "password", "nome", 4, false, true);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
    }    
}
