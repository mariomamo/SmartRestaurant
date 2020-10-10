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
public class AccountTest {
    
    public AccountTest() {
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
     * Test of getUsername method, of class Account.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Account instance = new Account("username", "password", "nome", 0);
        String expResult = "username";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUsername method, of class Account.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "newUsername";
        Account instance = new Account("username", "password", "nome", 0);
        instance.setUsername(username);
        String result = instance.getUsername();
        assertEquals(username, result);
    }

    /**
     * Test of getPassword method, of class Account.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Account instance = new Account("username", "password", "nome", 0);
        String expResult = "password";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Account.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "newPassword";
        Account instance = new Account("username", "password", "nome", 0);
        instance.setPassword(password);String result = instance.getPassword();
        assertEquals(password, result);
    }

    /**
     * Test of getNome method, of class Account.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Account instance = new Account("username", "password", "nome", 0);
        String expResult = "nome";
        String result = instance.getNome();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNome method, of class Account.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "newNome";
        Account instance = new Account("username", "password", "nome", 0);
        instance.setNome(nome);
        String result = instance.getNome();
        assertEquals(nome, result);
    }

    /**
     * Test of getTipo method, of class Account.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Account instance = new Account("username", "password", "nome", 0);
        Integer expResult = 0;
        Integer result = instance.getTipo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTipo method, of class Account.
     */
    @Test
    public void testSetTipo() {
        System.out.println("setTipo");
        Integer tipo = 0;
        Account instance = new Account("username", "password", "nome", 0);
        instance.setTipo(tipo);
        Integer result = instance.getTipo();
        assertEquals(tipo, result);
    }

    /**
     * Test of toString method, of class Account.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Account instance = new Account("username", "password", "nome", 0);
        String expResult = "Account{username='username', password='password', nome='nome', tipo=0}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Account.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Account("username", "password", "nome", 0);
        
        Account instance = new Account("username", "password", "nome", 0);
        
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
       
        result = instance.equals(instance);
        expResult = true;
        assertEquals(expResult, result);
        
        result = instance.equals(null);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Account();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Account("username", "password", "n", 0);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Account("username", "password", "nome", 1);
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
        
        obj = new Tavolo();
        result = instance.equals(obj);
        expResult = false;
        assertEquals(expResult, result);
    }
}
