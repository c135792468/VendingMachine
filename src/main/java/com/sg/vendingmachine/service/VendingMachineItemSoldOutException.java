/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author 69591
 */
public class VendingMachineItemSoldOutException extends Exception {
    public VendingMachineItemSoldOutException(String message) {
        super(message);
    }
    
    public VendingMachineItemSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
