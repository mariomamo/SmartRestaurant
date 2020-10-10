package query;

import entity.Comanda;
import entity.Piatto;
import entity.PiattoOrdinato;
import entity.Tavolo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TavoloQueryTest {
    private static ArrayList<Tavolo> tavoli;
    private static Tavolo tavoloComanda;
    private static Comanda comanda;
    private static Piatto piattoAntipasto;
    private static Piatto piattoPrimo;
    private static Piatto piattoSecondo;
    private static Piatto piattoContorno;
    private static Piatto piattoDolce;
    private static Piatto piattoBevanda; 
    private static PiattoOrdinato piattoOrdinatoAntipasto;
    private static PiattoOrdinato piattoOrdinatoPrimo;
    private static PiattoOrdinato piattoOrdinatoSecondo;
    private static PiattoOrdinato piattoOrdinatoContorno;
    private static PiattoOrdinato piattoOrdinatoDolce;
    private static PiattoOrdinato piattoOrdinatoBevanda;
    
    public TavoloQueryTest() {
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
        
        ComandaQuery comandaQuery = new ComandaQuery();
        PiattoOrdinatoQuery piattoOrdinatoQuery = new PiattoOrdinatoQuery();
        
        piattoOrdinatoAntipasto = new PiattoOrdinato(piattoAntipasto, false, "2020/02/03", "");
        piattoOrdinatoPrimo = new PiattoOrdinato(piattoPrimo, false, "2020/02/03", "");
        piattoOrdinatoSecondo = new PiattoOrdinato(piattoSecondo, false, "2020/02/03", "");
        piattoOrdinatoContorno= new PiattoOrdinato(piattoContorno, false, "2020/02/03", "");
        piattoOrdinatoDolce = new PiattoOrdinato(piattoDolce, false, "2020/02/03", "");
        piattoOrdinatoBevanda = new PiattoOrdinato(piattoBevanda, false, "2020/02/03", "");
        
        ArrayList<PiattoOrdinato> piattiOrdinati = new ArrayList<>();
        piattiOrdinati.add(piattoOrdinatoAntipasto);
        piattiOrdinati.add(piattoOrdinatoPrimo);
        piattiOrdinati.add(piattoOrdinatoSecondo);
        piattiOrdinati.add(piattoOrdinatoContorno);
        piattiOrdinati.add(piattoOrdinatoDolce);
        piattiOrdinati.add(piattoOrdinatoBevanda);
        
        comanda = new Comanda();
        comanda.setId(0);
        comanda.setData("2020/02/02");
        comanda.setPiattiOrdinati(piattiOrdinati);
        comanda.setRecensione("");
        comanda.setStato(false);
        comanda.setTotale(84.0f);
        
        tavoli = new ArrayList<>();
        
        tavoli.add(new Tavolo("tavolo90", "pass", "Tavolo 90", 4, false, false));
        tavoli.add(new Tavolo("tavolo91", "pass", "Tavolo 91", 4, false, false));
        tavoli.add(new Tavolo("tavolo92", "pass", "Tavolo 92", 4, true, false));
        tavoli.add(new Tavolo("tavolo93", "pass", "Tavolo 93", 4, true, true));
        tavoli.add(new Tavolo("tavolo94", "pass", "Tavolo 94", 4, true, true));
        tavoli.add(new Tavolo("tavolo95", "pass", "Tavolo 95", 4, true, true));
        
        TavoloQuery tavoloQuery = new TavoloQuery();
        
        tavoli.forEach((t) -> {
            tavoloQuery.insert(t);
        });
        
        tavoloComanda = new Tavolo("tavolo85", "pass", "Tavolo 85", 4, false, false);
        tavoloComanda.setComanda(comanda);
    }
    
    @AfterClass
    public static void tearDownClass() {
        TavoloQuery tavoloQuery = new TavoloQuery();
        
        tavoli.forEach((t)-> {
            tavoloQuery.delete(t);
        });
        
        tavoloQuery.delete(tavoloComanda);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class TavoloQuery.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        Tavolo tavolo = tavoli.get(0);
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = tavoli.get(0);
        Tavolo result = instance.login(tavolo);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of insert method, of class TavoloQuery.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Tavolo tavolo = new Tavolo("tavolo101", "pass", "Tavolo 101", 4, false, false);
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = new Tavolo("tavolo101", "pass", "Tavolo 101", 4, false, false);
        Tavolo result = instance.insert(tavolo);
        instance.delete(tavolo);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class TavoloQuery.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Tavolo tavolo = tavoli.get(0);
        tavolo.setNome("Nuovo Tavolo 100");
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = tavolo;
        Tavolo result = instance.update(tavolo);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateStato method, of class TavoloQuery.
     */
    @Test
    public void testUpdateStato() {
        System.out.println("updateStato");
        Tavolo tavolo = tavoli.get(0);
        tavolo.setLibero(true);
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = tavolo;
        Tavolo result = instance.updateStato(tavolo);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateVuolePagare method, of class TavoloQuery.
     */
    @Test
    public void testUpdateVuolePagare() {
        System.out.println("updateVuolePagare");
        Tavolo tavolo = tavoli.get(0);
        tavolo.setVuolePagare(true);
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = tavolo;
        Tavolo result = instance.updateVuolePagare(tavolo);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setPagato method, of class TavoloQuery.
     */
    @Test
    public void testsetPagato() {
        System.out.println("setPagato");
        Tavolo tavolo = tavoli.get(0);
        Tavolo expResult = tavolo;
        expResult.setLibero(true);
        expResult.setVuolePagare(false);
        TavoloQuery instance = new TavoloQuery();
        Tavolo result = instance.setPagato(tavolo);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class TavoloQuery.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Tavolo tavolo = new Tavolo("tavolo101", "pass", "Tavolo 101", 4, false, false);
        TavoloQuery instance = new TavoloQuery();
        instance.insert(tavolo);
        instance.delete(tavolo);
        Tavolo expResult = null;
        Tavolo result = instance.login(tavolo);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class TavoloQuery.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        TavoloQuery instance = new TavoloQuery();
        List<Tavolo> expResult = tavoli;
        List<Tavolo> result = instance.findAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of findAllFree method, of class TavoloQuery.
     */
    @Test
    public void testFindAllFree() {
        System.out.println("findAllFree");
        TavoloQuery instance = new TavoloQuery();
        List<Tavolo> expResult = new ArrayList<>();
        tavoli.stream().filter((t) -> (t.isLibero())).forEachOrdered((t) -> {
            expResult.add(t);
        });
        
        List<Tavolo> result = instance.findAllFree();        
        assertEquals(expResult, result);
    }

    /**
     * Test of findAllOccupied method, of class TavoloQuery.
     */
    @Test
    public void testFindAllOccupied() {
        System.out.println("findAllOccupied");
        TavoloQuery instance = new TavoloQuery();
        List<Tavolo> expResult = new ArrayList<>();
        tavoli.stream().filter((t) -> (!t.isLibero())).forEachOrdered((t) -> {
            expResult.add(t);
        });;
        List<Tavolo> result = instance.findAllOccupied();
        assertEquals(expResult, result);     
    }

    /**
     * Test of findAllComanda method, of class TavoloQuery.
     */
    @Test
    public void testFindAllComanda() {
        System.out.println("findAllComanda");
        TavoloQuery instance = new TavoloQuery();
        ComandaQuery comandaQuery = new ComandaQuery();
        
        instance.insert(tavoloComanda);
        comandaQuery.insert(tavoloComanda);
        
        PiattoOrdinatoQuery piattoOrdinatoQuery = new PiattoOrdinatoQuery();
        
        int id = comandaQuery.findByUsername(tavoloComanda.getUsername()).getId();
        
        piattoOrdinatoQuery.insert(piattoOrdinatoAntipasto, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoPrimo, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoSecondo, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoContorno, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoDolce, id);
        piattoOrdinatoQuery.insert(piattoOrdinatoBevanda, id);
        
        
        List<Tavolo> expResult = new ArrayList<>();
        expResult.add(tavoloComanda);
        List<Tavolo> result = instance.findAllComanda();
        instance.delete(tavoloComanda);
        
        ArrayList<Tavolo> tavoloExpected = new ArrayList<>();
        tavoloExpected.add(tavoloComanda);
        assertEquals(tavoloExpected, result);
    }

    /**
     * Test of findAllWantPay method, of class TavoloQuery.
     */
    @Test
    public void testFindAllWantPay() {
        System.out.println("findAllWantPay");
        TavoloQuery instance = new TavoloQuery();
        List<Tavolo> expResult = new ArrayList<>();
        tavoli.stream().filter((t) -> (t.vuolePagare())).forEachOrdered((t) -> {
            expResult.add(t);
        });
        List<Tavolo> result = instance.findAllWantPay();
        assertEquals(expResult, result);
    }

    /**
     * Test of findByUsername method, of class TavoloQuery.
     */
    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");
        String username = "tavolo90";
        TavoloQuery instance = new TavoloQuery();
        Tavolo expResult = tavoli.get(0);
        Tavolo result = instance.findByUsername(username);
        assertEquals(expResult, result);
    }
}
