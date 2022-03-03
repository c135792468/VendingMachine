/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 69591
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    public Item availableItem;
    public Item unavailableItem;
    
    public VendingMachineDaoStubImpl(){
        availableItem = new Item("Water", "1.00", "3");
        unavailableItem = new Item("Cook", "1.50", "0");
    }
    
    public VendingMachineDaoStubImpl(Item testItem){
        this.availableItem = testItem;
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(availableItem);
        itemList.add(unavailableItem);
        return itemList;
    }

    @Override
    public Item getItem(String item) throws VendingMachinePersistenceException {
        if(item.equals(availableItem.getName())){
            return availableItem;
        }
        else if(item.equals(unavailableItem.getName())){
            return unavailableItem;
        }
        else{
            return null;
        }
    }

    @Override
    public List<Coin> getChanges(Double amount, Item currentItem) throws VendingMachinePersistenceException {
        List<Coin> CoinList = new ArrayList<>();
        if(amount < Double.parseDouble(currentItem.getPrice())){
            return null;
        }
        else{
            return CoinList;
        }
    }
    
}
