/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author 69591
 */
public interface VendingMachineServiceLayer {
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    List<Coin> getChanges(Double amount, Item currentItem)throws 
            VendingMachineInsufficientFundsException,
            VendingMachinePersistenceException;         
    Item getItem(String item)throws 
            VendingMachineItemSoldOutException,
            VendingMachineItemNotFoundException,
            VendingMachinePersistenceException;
}
