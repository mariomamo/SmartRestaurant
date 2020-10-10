/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.Notifica.Categoria;
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
public class NotificaTest {
    
    public NotificaTest() {
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
     * Test of getId method, of class Notifica.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        long expResult = 1L;
        long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Notifica.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        long id = 2L;
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setId(id);
        long result = instance.getId();
        assertEquals(id, result);
    }

    /**
     * Test of getDestinatario method, of class Notifica.
     */
    @Test
    public void testGetDestinatario() {
        System.out.println("getDestinatario");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = "ccameriere";
        String result = instance.getDestinatario();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDestinatario method, of class Notifica.
     */
    @Test
    public void testSetDestinatario() {
        System.out.println("setDestinatario");
        String destinatario = "exchef";
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setDestinatario(destinatario);
        String result = instance.getDestinatario();
        assertEquals(destinatario, result);
    }

    /**
     * Test of getMittente method, of class Notifica.
     */
    @Test
    public void testGetMittente() {
        System.out.println("getMittente");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = "exchef";
        String result = instance.getMittente();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMittente method, of class Notifica.
     */
    @Test
    public void testSetMittente() {
        System.out.println("setMittente");
        String mittente = "tavolo9";
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setMittente(mittente);
        String result = instance.getMittente();
        assertEquals(mittente, result);
    }

    /**
     * Test of getTesto method, of class Notifica.
     */
    @Test
    public void testGetTesto() {
        System.out.println("getTesto");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = "testo notifica";
        String result = instance.getTesto();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTesto method, of class Notifica.
     */
    @Test
    public void testSetTesto() {
        System.out.println("setTesto");
        String testo = "nuovo testo notifica";
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setTesto(testo);
        String result = instance.getTesto();
        assertEquals(testo, result);
    }

    /**
     * Test of isLetta method, of class Notifica.
     */
    @Test
    public void testIsLetta() {
        System.out.println("isLetta");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        boolean expResult = false;
        boolean result = instance.isLetta();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLetta method, of class Notifica.
     */
    @Test
    public void testSetLetta() {
        System.out.println("setLetta");
        boolean letta = true;
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setLetta(letta);
        boolean result = instance.isLetta();
        assertEquals(letta, result);
    }

    /**
     * Test of getCategoria method, of class Notifica.
     */
    @Test
    public void testGetCategoria() {
        System.out.println("getCategoria");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = Categoria.AIUTO;
        String result = instance.getCategoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCategoria method, of class Notifica.
     */
    @Test
    public void testSetCategoria() {
        System.out.println("setCategoria");
        String categoria = Categoria.PAGAMENTO;
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setCategoria(categoria);
        String result = instance.getCategoria();
        assertEquals(categoria, result);
    }

    /**
     * Test of getData method, of class Notifica.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = "2020/02/02";
        String result = instance.getData();
        assertEquals(expResult, result);
    }

    /**
     * Test of setData method, of class Notifica.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "2020/02/03";
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        instance.setData(data);
        String result = instance.getData();
        assertEquals(data, result);
    }

    /**
     * Test of toString method, of class Notifica.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        String expResult = "Notifica{id=1, destinatario='ccameriere', categoria='Aiuto', mittente='exchef', testo='testo notifica', letta=false, data='2020/02/02'}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Notifica.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        Notifica instance = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");
        
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
       
        result = instance.equals(instance);
        expResult = true;
        assertEquals(expResult, result);
        
        result = instance.equals(null);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "cameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/02");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "ccameriere", Categoria.PAGAMENTO, "exchef", "testo notifica", false, "2020/02/02");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "ccameriere", Categoria.AIUTO, "tavolo9", "testo notifica", false, "2020/02/02");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "notifica", false, "2020/02/02");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", true, "2020/02/02");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Notifica(1L, "ccameriere", Categoria.AIUTO, "exchef", "testo notifica", false, "2020/02/03");;
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
    }
    
}
