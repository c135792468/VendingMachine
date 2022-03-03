/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachine.service.VendingMachineItemNotFoundException;
import com.sg.vendingmachine.service.VendingMachineItemSoldOutException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 69591
 */
@Component
public class VendingMachineController {
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    /*private VendingMachineDao dao;

    public VendingMachineController(VendingMachineView view, VendingMachineDao dao) {
        this.view = view;
        this.dao = dao;
    }*/
    @Autowired
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    //program start
    public void run() {
        boolean keepGoing = true;
        String menuSelection = " ";
        try{
        while (keepGoing) {
            //print menu
            getMenu();
            //get user input money
            Double money = getInputMoney();

            if(money.equals(0.00)){
                keepGoing = false;
            }
            else{
                //get user selected item
                menuSelection = getMenuSelection();
                //get changes
                getChanges(money, menuSelection);
            }
               
        } 
        exitMessage();
        }catch (VendingMachinePersistenceException e){
            view.displayErrorMessage(e.getMessage());
                }
    }
    //getting all the item from the file and store to a item list and return it
    private void getMenu() throws VendingMachinePersistenceException{
        List<Item> itemList = service.getAllItems();
        view.printMenu(itemList);
    }

    private String getMenuSelection(){
        return view.getSelectedItem();
    }

    private Double getInputMoney(){
        return view.getInputMoney();
    }
    
    private void getChanges(Double money, String itemSelected) throws VendingMachinePersistenceException{
        try {
            //getting the selected item
            Item currentItem = service.getItem(itemSelected);
            //send the selected item and input money to get changes
            List<Coin> refund = service.getChanges(money, currentItem);
            view.displayChanges(refund);
        } catch (VendingMachineItemSoldOutException 
                | VendingMachineItemNotFoundException 
                | VendingMachineInsufficientFundsException e ) {
            view.displayErrorMessage(e.getMessage());
        } 
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
}
