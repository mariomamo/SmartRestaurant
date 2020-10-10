package it.unisa.smartrestaurantapp.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DbManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getIp() {
        Assert.assertEquals(DbManager.getIp(), DbManager.getIp());
    }

    @Test
    public void getPorta() {
        Assert.assertEquals(DbManager.getPorta(), DbManager.getPorta());
    }
}