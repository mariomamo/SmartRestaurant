package query;

import entity.Comanda;
import entity.Piatto;
import entity.Tavolo;
import entity.PiattoOrdinato;
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

public class PiattoOrdinatoQueryTest {
    private static Tavolo tavolo;
    private static Comanda comanda;
    private static PiattoOrdinato piattoOrdinato;
    private static Piatto piattoAntipasto;
    private static Piatto piattoPrimo;
    private static Piatto piattoSecondo;
    private static Piatto piattoContorno;
    private static Piatto piattoDolce;
    private static Piatto piattoBevanda; 
    private static int id;
    
    public PiattoOrdinatoQueryTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        piattoAntipasto = new Piatto("Mari e monti", "Antipasti", "Carne e pesce", "Prosciutto, polpi, limone", 14.0f, "path_foto");
        piattoPrimo = new Piatto("Spaghetti", "Primi", "Pasta con salsa", "Pasta, salsa, pomodorini", 15.0f, "path_foto");
        piattoSecondo = new Piatto("Bistecca ai ferri", "Secondi", "Bistecca di maiale", "Carne di maiale", 25.0f, "path_foto");
        piattoContorno = new Piatto("Insalata", "Contorni", "Insalata estiva", "Rucola, scarole", 6.0f, "path_foto");
        piattoDolce = new Piatto("Crostata", "Dolci", "Crostata con marmellata", "Pasta sfoglia, marmellata di fragole", 22.0f, "path_foto");
        piattoBevanda = new Piatto("Acqua", "Bevande", "Acqua naturale", "", 2.0f, "path_foto");
        
        PiattoQuery instance = new PiattoQuery();
        instance.insert(piattoAntipasto);
        instance.insert(piattoSecondo);
        instance.insert(piattoContorno);
        instance.insert(piattoDolce);
        instance.insert(piattoBevanda);
        
        piattoAntipasto = instance.findByCategoria(piattoAntipasto, 0, 1).get(0);
        piattoPrimo = instance.findByCategoria(piattoPrimo, 0, 1).get(0);
        piattoSecondo = instance.findByCategoria(piattoSecondo, 0, 1).get(0);
        piattoContorno = instance.findByCategoria(piattoContorno, 0, 1).get(0);
        piattoDolce = instance.findByCategoria(piattoDolce, 0, 1).get(0);
        piattoBevanda = instance.findByCategoria(piattoBevanda, 0, 1).get(0);
        
        piattoOrdinato = new PiattoOrdinato(new Piatto(), false, "2020/02/02", "");
        
        TavoloQuery tavoloQuery = new TavoloQuery();
        ComandaQuery comandaQuery = new ComandaQuery();
        PiattoOrdinatoQuery piattoOrdinatoQuery = new PiattoOrdinatoQuery();
        
        PiattoOrdinato piattoOrdinatoAntipasto = new PiattoOrdinato(piattoAntipasto, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoPrimo = new PiattoOrdinato(piattoPrimo, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoSecondo = new PiattoOrdinato(piattoSecondo, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoContorno= new PiattoOrdinato(piattoContorno, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoDolce = new PiattoOrdinato(piattoDolce, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoBevanda = new PiattoOrdinato(piattoBevanda, false, "2020/02/03", "");
        
        comanda = new Comanda();
        comanda.setId(0);
        comanda.setData("2020/02/02");
        comanda.setRecensione("");
        comanda.setStato(false);
        comanda.setTotale(200.0f);
        
        tavolo = new Tavolo("tavolo101", "pass", "Tavolo 101", 4, false, false);
        tavolo.setComanda(comanda);
        
        tavoloQuery.insert(tavolo);
        comandaQuery.insert(tavolo);
        
        id = comandaQuery.findByUsername(tavolo.getUsername()).getId();
        
        piattoOrdinatoQuery.insert(piattoOrdinatoAntipasto, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoPrimo, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoSecondo, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoContorno, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoDolce, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoBevanda, id);
    }
    
    @AfterClass
    public static void tearDownClass() {
        TavoloQuery tavoloQuery = new TavoloQuery();
        tavoloQuery.delete(tavolo);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class PiattoOrdinatoQuery.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        int idComanda = id;
        PiattoOrdinatoQuery instance = new PiattoOrdinatoQuery();
        PiattoOrdinato piattoOrdinato = new PiattoOrdinato(piattoAntipasto, false, "2020/02/02", "");
        PiattoOrdinato expResult = piattoOrdinato;
        PiattoOrdinato result = instance.insert(piattoOrdinato, idComanda);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class PiattoOrdinatoQuery.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        PiattoOrdinato piattoOrdinato = new PiattoOrdinato(piattoAntipasto, false, "2020/02/02", "");
        piattoOrdinato.setNote("nuove note");
        piattoOrdinato.setId(2L);
        int idComanda = id;
        PiattoOrdinatoQuery instance = new PiattoOrdinatoQuery();
        PiattoOrdinato expResult = piattoOrdinato;
        PiattoOrdinato result = instance.update(piattoOrdinato, idComanda);
        assertEquals(expResult, result);
    }

    /**
     * Test of findByComanda method, of class PiattoOrdinatoQuery.
     */
    @Test
    public void testFindByComanda() {
        System.out.println("findByComanda");
        PiattoOrdinatoQuery instance = new PiattoOrdinatoQuery();
        List<PiattoOrdinato> expResult = comanda.getPiattiOrdinati();
        List<PiattoOrdinato> result = instance.findByComanda(comanda);
        assertEquals(expResult, result);
    }   
    
    /**
     * Test of findById method, of class PiattoOrdinatoQuery.
     */
    @Test
    public void testFindById() {
        try {
            System.out.println("findById");
            PiattoOrdinatoQuery instance = new PiattoOrdinatoQuery();
            PiattoOrdinato expResult = new PiattoOrdinato(piattoAntipasto, false, "2020/02/02", "");
            instance.insert(new PiattoOrdinato(piattoAntipasto, false, "2020/02/02", ""), id);
            
            Connection connection = DriverManagerConnectionPool.getConnection();
            String query = "SELECT * FROM PiattoOrdinato AS po ORDER BY po.id DESC;";
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            PiattoOrdinato p = null;
            
            if(rs.next()) {
                p = new PiattoOrdinato();
                p.setId(rs.getLong("id"));
                
                Piatto piatto = new PiattoQuery().findById(Integer.parseInt(rs.getInt("id_piatto") + ""));
                p.setPiatto(piatto);
                
                p.setStato(rs.getBoolean("stato"));
                p.setData(rs.getString("data"));
                p.setNote(rs.getString("note"));
                p.setQuantita(Integer.parseInt(rs.getInt("quantita") + ""));
                p.setComanda(rs.getInt("comanda"));
            }
            
            int id = p.getId().intValue();
            PiattoOrdinato result = instance.findById(id);
            
            assertEquals(expResult, result);
        } catch (SQLException ex) {
            Logger.getLogger(PiattoOrdinatoQueryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Test of delete method, of class PiattoOrdinatoQuery.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        PiattoOrdinatoQuery instance = new PiattoOrdinatoQuery();
        PiattoOrdinato piattoOrdinato = new PiattoOrdinato(1L, piattoAntipasto, false, "2020/02/02", "", 1);
        instance.delete(piattoOrdinato);
    } 
}
