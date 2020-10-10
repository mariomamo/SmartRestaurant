package query;

import entity.Account;
import org.junit.Assert;
import org.junit.Test;

public class AccountQueryTest {
    private Account proprietario;
    private Account exchef;
    private Account ccameriere;
    
    
    public AccountQueryTest() {
        AccountQuery instance = new AccountQuery();
        proprietario = new Account("proprietario", "pass", "Bob", 0);
        exchef = new Account("exchef", "pass", "Tom", 2);
        ccameriere = new Account("ccameriere", "pass", "Joe", 1);
    }

    @Test
    public void testLogin() {
        System.out.println("login");
        AccountQuery instance = new AccountQuery();
        Account expResult = proprietario;
        Account result = instance.login(proprietario);
        Assert.assertEquals(expResult, result);
        
        expResult = new Account("exchef", "pass", "Tom", 2);
        result = instance.login(exchef);
        Assert.assertEquals(expResult, result);
        
        expResult = ccameriere;
        result = instance.login(ccameriere);
        Assert.assertEquals(expResult, result);
        
        Account account = new Account("nonEsiste", "nopass", "", 0);
        expResult = null;
        result = instance.login(account);
        Assert.assertEquals(expResult, result);
        
        instance.close();
        instance.login(account);
        Assert.assertEquals(expResult, result);
    }  
}
