package query;

import entity.Comanda;
import entity.Piatto;
import entity.PiattoOrdinato;
import entity.Tavolo;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class ComandaQueryTest {
    private static Tavolo tavolo;
    private static Tavolo tavoloFind;
    private static Comanda comanda;
    private static Comanda comandaFind;
    private static Piatto piattoAntipasto;
    private static Piatto piattoPrimo;
    private static Piatto piattoSecondo;
    private static Piatto piattoContorno;
    private static Piatto piattoDolce;
    private static Piatto piattoBevanda; 
    private static int id;
    
    public ComandaQueryTest() {
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
        
        TavoloQuery tavoloQuery = new TavoloQuery();
        ComandaQuery comandaQuery = new ComandaQuery();
        PiattoOrdinatoQuery piattoOrdinatoQuery = new PiattoOrdinatoQuery();
        
        PiattoOrdinato piattoOrdinatoAntipasto = new PiattoOrdinato(piattoAntipasto, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoPrimo = new PiattoOrdinato(piattoPrimo, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoSecondo = new PiattoOrdinato(piattoSecondo, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoContorno= new PiattoOrdinato(piattoContorno, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoDolce = new PiattoOrdinato(piattoDolce, false, "2020/02/03", "");
        PiattoOrdinato piattoOrdinatoBevanda = new PiattoOrdinato(piattoBevanda, false, "2020/02/03", "");
        
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
        
        tavolo = new Tavolo("tavolo101", "pass", "Tavolo 101", 4, false, false);
        tavolo.setComanda(comanda);
        tavoloQuery.insert(tavolo);
        
        comandaFind = new Comanda();
        comandaFind.setId(1);
        comandaFind.setData("2020/02/03");
        comandaFind.setPiattiOrdinati(piattiOrdinati);
        comandaFind.setRecensione("");
        comandaFind.setStato(false);
        comandaFind.setTotale(84f);
        
        tavoloFind = new Tavolo("tavolo102", "pass", "Tavolo 102", 4, false, false);
        tavoloFind.setComanda(comandaFind);
        
        tavoloQuery.insert(tavoloFind);
        comandaQuery.insert(tavoloFind);
        
        id = comandaQuery.findByUsername(tavoloFind.getUsername()).getId();
        
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
        tavoloQuery.delete(tavoloFind);
    }
    
    @Test
    public void testInsert() {
        System.out.println("insert");
        ComandaQuery instance = new ComandaQuery();
        Comanda expResult = comanda;
        Comanda result = instance.insert(tavolo);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");
        String username = tavoloFind.getUsername();
        ComandaQuery instance = new ComandaQuery();
        Comanda expResult = comandaFind;
        Comanda result = instance.findByUsername(username);
        assertEquals(expResult, result);
    }   
}
