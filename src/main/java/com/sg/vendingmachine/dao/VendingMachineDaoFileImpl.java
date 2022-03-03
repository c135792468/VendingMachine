/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author 69591
 */
@Component
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    private Map<String, Item> items = new HashMap<>();
    private final String ITEM_FILE;
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl(){
        ITEM_FILE = "item.txt";
    }

    public VendingMachineDaoFileImpl(String itemTextFile){
        ITEM_FILE = itemTextFile;
    }
   
    @Override
    public List<Item> getAllItems()throws VendingMachinePersistenceException{
        loadRoster();
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item getItem(String item)throws VendingMachinePersistenceException{
        loadRoster();
        return items.get(item);
    }

    @Override
    //get changes
    public List<Coin> getChanges(Double inputAmount, Item currentItem)throws VendingMachinePersistenceException{
        List<Coin> changes = new ArrayList<Coin>();
        //convert inputAmount to bigDecimal
        BigDecimal balance = new BigDecimal(Double.toString(inputAmount)).setScale(2, RoundingMode.DOWN);
        //get the change balance by subtract the item price by inputAmount 
        balance = balance.subtract(new BigDecimal(currentItem.getPrice()));
        //set zero to bigDecimal
        BigDecimal zero = new BigDecimal("0.00");
        //while the balance is not zero
        while(balance.compareTo(zero) > 0){
            //if balance if greater than quarter
            //add quarter to change
            //subtract the quarter by balance 
            //repeat for dime, nickle, and penny
            if(balance.compareTo(Coin.QUARTER.getValue()) >= 0){
                changes.add(Coin.QUARTER);
                balance = balance.subtract(Coin.QUARTER.getValue());
            }
            else if(balance.compareTo(Coin.DIME.getValue()) >= 0){
                changes.add(Coin.DIME);
                balance = balance.subtract(Coin.DIME.getValue());
            }
            else if(balance.compareTo(Coin.NICKLE.getValue()) >= 0){
                changes.add(Coin.NICKLE);
                balance = balance.subtract(Coin.NICKLE.getValue());
            }
            else if(balance.compareTo(Coin.PENNY.getValue()) >= 0){
                changes.add(Coin.PENNY);
                balance = balance.subtract(Coin.PENNY.getValue());
            }
        }
        //update the item that being sold
        updateItemInventory(currentItem);
        //write to file
        writeRoster();
        return changes;
    }
    
    //updating the item that being sold
    private void updateItemInventory(Item item){
        //getting the item number in string
        String stringItemNum = item.getNum();
        //convert stringNumber to intNumber
        int itemNum = Integer.parseInt(stringItemNum);
        //itemNumber minus one
        itemNum--;
        //convert itemIntNumber back to StringItemNumber and set the value
        item.setNum(Integer.toString(itemNum));
    }
    
    //convert stringItem to item
    private Item unmarshallItem(String itemAsText){
        //split the delimiter in file
        String[] itemTokens = itemAsText.split(DELIMITER);
        //set the value of item
        String name = itemTokens[0];
        String price = itemTokens[1];
        String num = itemTokens[2];
        Item itemFromFile = new Item(name, price, num);
        return itemFromFile;
    }
    
    //read item from file
    private void loadRoster() throws VendingMachinePersistenceException{
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentItem holds the most recent student unmarshalled
        Item currentItem;
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Item
            currentItem = unmarshallItem(currentLine);
            //adding item to the map
            items.put(currentItem.getName(), currentItem);
        }
        // close scanner
        scanner.close();
    }
    
    //convert item to string then return the itemString
    private String marshallItem(Item item){
        String itemAsText = item.getName() + DELIMITER;
        itemAsText += item.getPrice() + DELIMITER;
        itemAsText += item.getNum();
        return itemAsText;
    }
    
    //write item to file
    private void writeRoster() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save student data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn a item into a String
            itemAsText = marshallItem(currentItem);
            // write the item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }


}
