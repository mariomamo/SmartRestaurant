package query;

import entity.Notifica;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificaQueryTest {
    private static ArrayList<Notifica> notifiche;
    private static Notifica notifica;
    
    public NotificaQueryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        notifiche = new ArrayList<>();
        
        notifica = new Notifica(0, "ccameriere", Notifica.Categoria.AIUTO, "exchef", "Necessito aiuto", false, "2020/02/02");
        
        notifiche.add(new Notifica(1, "ccameriere", Notifica.Categoria.AIUTO, "exchef", "Necessito aiuto", false, "2020/02/03"));
        notifiche.add(new Notifica(2, "ccameriere", Notifica.Categoria.AIUTO, "exchef", "Necessito aiuto", true, "2020/02/03"));
        
        NotificaQuery notificaQuery = new NotificaQuery();
        
        for(Notifica n: notifiche)
            notificaQuery.insert(n);
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
     * Test of insert method, of class NotificaQuery.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        NotificaQuery instance = new NotificaQuery();
        Notifica expResult = notifica;
        Notifica result = instance.insert(notifica);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class NotificaQuery.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        NotificaQuery instance = new NotificaQuery();
        notifica.setTesto("Nuovo testo per la notifica");
        Notifica expResult = notifica;
        Notifica result = instance.update(notifica);
        assertEquals(expResult, result);
    }

    /**
     * Test of findById method, of class NotificaQuery.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        NotificaQuery instance = new NotificaQuery();
        Notifica expResult = instance.findAll(notifica, 0, 1).get(0);
        Notifica result = instance.findById(expResult);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class NotificaQuery.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        int inizio = 0;
        int fine = 100;
        NotificaQuery instance = new NotificaQuery();
        List<Notifica> expResult = notifiche;
        List<Notifica> result = instance.findAll(notifiche.get(0), inizio, fine);
        System.out.println(result);
        System.out.println(expResult);
        assertTrue(result.containsAll(expResult) && expResult.containsAll(result));
    }
}
