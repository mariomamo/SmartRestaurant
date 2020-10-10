package query;

import entity.Piatto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PiattoQueryTest {
    private static Piatto piattoAntipasto;
    private static Piatto piattoPrimo;
    private static Piatto piattoSecondo;
    private static Piatto piattoContorno;
    private static Piatto piattoDolce;
    private static Piatto piattoBevanda;
    
    public PiattoQueryTest() {
        PiattoQuery instance = new PiattoQuery();
        
        piattoAntipasto = new Piatto("Mari e monti", "Antipasti", "Carne e pesce", "Prosciutto, polpi, limone", 14.0f, "path_foto");
        piattoPrimo = new Piatto("Spaghetti", "Primi", "Pasta con salsa", "Pasta, salsa, pomodorini", 15.0f, "path_foto");
        piattoSecondo = new Piatto("Bistecca ai ferri", "Secondi", "Bistecca di maiale", "Carne di maiale", 25.0f, "path_foto");
        piattoContorno = new Piatto("Insalata", "Contorni", "Insalata estiva", "Rucola, scarole", 6.0f, "path_foto");
        piattoDolce = new Piatto("Crostata", "Dolci", "Crostata con marmellata", "Pasta sfoglia, marmellata di fragole", 22.0f, "path_foto");
        piattoBevanda = new Piatto("Acqua", "Bevande", "Acqua naturale", "", 2.0f, "path_foto");
        
        instance.insert(piattoAntipasto);
        instance.insert(piattoSecondo);
        instance.insert(piattoContorno);
        instance.insert(piattoDolce);
        instance.insert(piattoBevanda);
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
     * Test of insert method, of class PiattoQuery.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        PiattoQuery instance = new PiattoQuery();
        Piatto expResult = piattoPrimo;
        Piatto result = instance.insert(piattoPrimo);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByCategoria method, of class PiattoQuery.
     */
    @Test
    public void testFindByCategoria() {
        System.out.println("findByCategoria");
        int inizio = 0;
        int fine = 100;
        PiattoQuery instance = new PiattoQuery();
        List<Piatto> expResult = new ArrayList<>();
        expResult.add(piattoAntipasto);
        List<Piatto> result = instance.findByCategoria(piattoAntipasto, inizio, fine);
        assertTrue(result.containsAll(expResult) && expResult.containsAll(result));
    }
    
    /**
     * Test of findById method, of class PiattoQuery.
     */
    @Test
    public void testFindById() {
        try {
            System.out.println("findById");
            PiattoQuery instance = new PiattoQuery();
            Piatto expResult = piattoAntipasto;
            instance.insert(piattoAntipasto);
            
            Connection connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT * FROM Piatto AS p ORDER BY p.id DESC;";
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            Piatto p = null;
            
            if(rs.next()) 
                p = new Piatto(Long.parseLong(rs.getInt("id") + ""), rs.getString("nome"), rs.getString("categoria"), rs.getString("descrizione"), rs.getString("ingredienti"), rs.getFloat("prezzo"), rs.getString("foto"));
           
            Piatto result = instance.findById(p.getId().intValue());
            
            assertEquals(expResult, result);
        } catch (SQLException ex) {
            Logger.getLogger(PiattoOrdinatoQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
