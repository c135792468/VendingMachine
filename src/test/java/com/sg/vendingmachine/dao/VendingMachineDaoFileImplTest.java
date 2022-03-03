/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 69591
 */
public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao;
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testItem.txt";
        testDao = new VendingMachineDaoFileImpl(testFile); 
    }
    
    @AfterEach
    public void tearDown()throws Exception {
    }

    @Test
    public void testGetAllItems() throws Exception{
        Item item2 = new Item("Water", "1.00", "3");
        List<Item> allItem = testDao.getAllItems();
        assertNotNull(allItem, "check if there is an item match");
        assertEquals(3, allItem.size(), "check if item size match");
        assertTrue(testDao.getAllItems().contains(item2), "check if item match");
    }
    
    @Test
    public void testGetItem() throws Exception{
        Item item = new Item("Cook", "1.50", "0");
        
        Item retrievedItem = testDao.getItem(item.getName());
        
        assertEquals(item.getName(),
                retrievedItem.getName(), "check if item name match");
        assertEquals(item.getPrice(),
                retrievedItem.getPrice(), "check if item price match");
        assertEquals(item.getNum(),
                retrievedItem.getNum(), "check if item number match");
    }
    
    @Test
    public void testGetChanges() throws Exception{
        Item item = new Item("Water", "1.00", "5");
        
        List<Coin> retrievedchanges = new ArrayList<Coin>();
        List<Coin> changes = testDao.getChanges(1.00, item);
        List<Coin> changes2 = testDao.getChanges(2.00, item);
        assertEquals(retrievedchanges, changes,
                "Checking full pay no changes.");
        assertEquals(4, changes2.size(),
                "Checking changes had 4 quarter.");
        assertTrue(changes2.contains(Coin.QUARTER));
        assertFalse(changes2.contains(Coin.DIME));
        assertFalse(changes2.contains(Coin.PENNY));
        assertFalse(changes2.contains(Coin.NICKLE));
        

    }
}
