/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 69591
 */
@Component
public class VendingMachineView {
    private UserIO io;
    
    @Autowired
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public String getSelectedItem(){
        return io.readString("Please enter the item name from the above choices.");
    }
    
    public Double getInputMoney(){
        return io.readDouble("Please put in some amount of money.");
    } 
    
    public void printMenu(List<Item> itemList) {
        io.print("Main Menu");
        for (Item currentItem : itemList) {
            if(!"0".equals(currentItem.getNum())){
                String itemInfo = String.format("Item name:%s, Price:%s, Inventory:%s.",
                currentItem.getName(),
                currentItem.getPrice(),
                currentItem.getNum());
                io.print(itemInfo);
        }
        }
        io.print("'0' to exit.");
    }
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    
    public void displayChanges(List<Coin> CoinList) {
        io.print("Here is your Changes:");
        CoinList.stream()
                .forEach((c)->io.print(c.getName()));
     /*   for (Coin currentCoin : CoinList) {
            io.print(currentCoin.getName());
        }*/
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
