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
public class PiattoTest {
    
    public PiattoTest() {
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
     * Test of getId method, of class Piatto.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        Long expResult = 1L;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Piatto.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = 2L;
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setId(id);
        Long result = instance.getId();
        assertEquals(id, result);
    }

    /**
     * Test of getNome method, of class Piatto.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "nome";
        String result = instance.getNome();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNome method, of class Piatto.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "newNome";
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setNome(nome);
        String result = instance.getNome();
        assertEquals(nome, result);
    }

    /**
     * Test of getCategoria method, of class Piatto.
     */
    @Test
    public void testGetCategoria() {
        System.out.println("getCategoria");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "categoria";
        String result = instance.getCategoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCategoria method, of class Piatto.
     */
    @Test
    public void testSetCategoria() {
        System.out.println("setCategoria");
        String categoria = "newCategoria";
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setCategoria(categoria);
        String result = instance.getCategoria();
        assertEquals(categoria, result);
    }

    /**
     * Test of getIngredienti method, of class Piatto.
     */
    @Test
    public void testGetIngredienti() {
        System.out.println("getIngredienti");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "ingredienti";
        String result = instance.getIngredienti();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIngredienti method, of class Piatto.
     */
    @Test
    public void testSetIngredienti() {
        System.out.println("setIngredienti");
        String ingredienti = "newIngredienti";
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setIngredienti(ingredienti);
        String result = instance.getIngredienti();
        assertEquals(ingredienti, result);
    }

    /**
     * Test of getDescrizione method, of class Piatto.
     */
    @Test
    public void testGetDescrizione() {
        System.out.println("getDescrizione");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "descrizione";
        String result = instance.getDescrizione();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescrizione method, of class Piatto.
     */
    @Test
    public void testSetDescrizione() {
        System.out.println("setDescrizione");
        String descrizione = "newDescrizione";
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setDescrizione(descrizione);
        String result = instance.getDescrizione();
        assertEquals(descrizione, result);
    }

    /**
     * Test of getPrezzo method, of class Piatto.
     */
    @Test
    public void testGetPrezzo() {
        System.out.println("getPrezzo");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        Float expResult = 10.0f;
        Float result = instance.getPrezzo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrezzo method, of class Piatto.
     */
    @Test
    public void testSetPrezzo() {
        System.out.println("setPrezzo");
        Float prezzo = 11.0f;
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setPrezzo(prezzo);
        Float result = instance.getPrezzo();
        assertEquals(prezzo, result);
    }

    /**
     * Test of getFoto method, of class Piatto.
     */
    @Test
    public void testGetFoto() {
        System.out.println("getFoto");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "path foto";
        String result = instance.getFoto();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFoto method, of class Piatto.
     */
    @Test
    public void testSetFoto() {
        System.out.println("setFoto");
        String foto = "new path foto";
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        instance.setFoto(foto);
        String result = instance.getFoto();
        assertEquals(foto, result);
    }

    /**
     * Test of toString method, of class Piatto.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        String expResult = "Piatto{id=1, nome='nome', categoria='categoria', ingredienti='ingredienti', descrizione='descrizione', prezzo=10.0, foto=path foto}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Piatto.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");;
        Piatto instance = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
       
        result = instance.equals(instance);
        expResult = true;
        assertEquals(expResult, result);
        
        result = instance.equals(null);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "n", "categoria", "descrizione", "ingredienti", 10.0f, "path foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "nome", "c", "descrizione", "ingredienti", 10.0f, "path foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "nome", "categoria", "d", "ingredienti", 10.0f, "path foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "nome", "categoria", "descrizione", "i", 10.0f, "path foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 11.0f, "path foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Piatto(1L, "nome", "categoria", "descrizione", "ingredienti", 10.0f, "foto");
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
    }
    
}
