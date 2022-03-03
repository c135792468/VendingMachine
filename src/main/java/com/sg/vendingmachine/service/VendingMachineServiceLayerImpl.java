/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 69591
 */
@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    VendingMachineDao dao;
    VendingMachineAuditDao auditDao;
    
    @Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    //check user input for money amount
    public List<Coin> getChanges(Double inputAmount, Item currentItem) throws
            VendingMachineInsufficientFundsException,
            VendingMachinePersistenceException {
            //check if user inputMoney is less than the item price
            if(inputAmount < Double.parseDouble(currentItem.getPrice())){
               throw new VendingMachineInsufficientFundsException(
                     "ERROR:  Insufficient Funds: " +inputAmount
               );
            }
            //else return changes
            auditDao.writeAuditEntry(
            "Item " + currentItem.getName() + " Sold.");
            return dao.getChanges(inputAmount, currentItem);
    }

    @Override
    //check if item is in the vending machine
    public Item getItem(String item) throws 
            VendingMachineItemSoldOutException,
            VendingMachineItemNotFoundException,
            VendingMachinePersistenceException {
        //check if item exist
        if(null == dao.getItem(item)){
            throw new VendingMachineItemNotFoundException(
                "ERROR: Could not get item. item name: "
                + item
                + " not found");
            }
        //check if item sold out
        else if("0".equals(dao.getItem(item).getNum())){
            throw new VendingMachineItemSoldOutException(
                "ERROR: Could not get item. item name: "
                + item
                + " sold out");  
        }
        else
            return dao.getItem(item);
        }
}
