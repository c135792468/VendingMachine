/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author 69591
 */
public class VendingMachineServiceLayerImplTest {
    
    VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest() {
     /*   VendingMachineDao testDao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao testAudit = new VendingMachineAuditDaoStubImpl();
        
        service = new VendingMachineServiceLayerImpl(testDao, testAudit); */
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        service = 
            ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
        }
    
      
        
     
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetValidItem() {
        Item item = new Item("Water", "1.00", "3");
        try{
            service.getItem(item.getName());
        } catch(VendingMachineItemSoldOutException
                | VendingMachineItemNotFoundException
                | VendingMachinePersistenceException e){
    // ASSERT
        fail("Item was valid. No exception should have been thrown.");
        }
    }
    
    @Test
    public void testGetItemNotFound() {
        Item item = new Item("unknown", "1.00", "3");
        try{
            service.getItem(item.getName());
            fail("Expected ItemNotFound Exception was not thrown");
        } catch(VendingMachineItemSoldOutException
                | VendingMachinePersistenceException e){
    // ASSERT
        fail("Incorrect exception was thrown.");
        } catch(VendingMachineItemNotFoundException e){
            return;
        }
    }

    @Test
    public void testGetItemSoldOut() {
        Item item = new Item("Cook", "1.50", "0");
        try{
            service.getItem(item.getName());
            fail("Expected ItemSoldOut Exception was not thrown");
        } catch(VendingMachineItemNotFoundException
                | VendingMachinePersistenceException e){
    // ASSERT
        fail("Incorrect exception was thrown.");
        } catch(VendingMachineItemSoldOutException e){
            return;
        }
    }

    @Test
    public void testGetAllItem() throws Exception {
        Item item = new Item("Water", "1.00", "3");
        
        assertEquals(2, service.getAllItems().size());
        assertTrue(service.getAllItems().contains(item));
    }
    
    @Test
    public void testGetValidChanges() {
        Item item = new Item("Water", "1.00", "3");
        try{
            service.getChanges(1.00, item);
        } catch(VendingMachineInsufficientFundsException
                | VendingMachinePersistenceException e){
    // ASSERT
        fail("Change was valid. No exception should have been thrown.");
        }
    }
    
    @Test
    public void testGetInvalidChanges() {
        Item item = new Item("Water", "1.00", "3");
        try{
            service.getChanges(.90, item);
            fail("Expected InsufficientFunds Exception was not thrown");
        } catch( VendingMachinePersistenceException e){
    // ASSERT
        fail("Incorrect exception was thrown.");
        } catch (VendingMachineInsufficientFundsException e ){
            return;
        }
    }
}
