/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author 69591
 */
public enum Coin{
    QUARTER(new BigDecimal(".25"), "Quarter"),
    DIME(new BigDecimal(".10"), "Dime"),
    NICKLE(new BigDecimal(".05"), "Nickle"),
    PENNY(new BigDecimal(".01"), "Penny");
    
    private BigDecimal value;
    private String name;
    
    private Coin(BigDecimal value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Coin getQUARTER() {
        return QUARTER;
    }


    public static Coin getDIME() {
        return DIME;
    }

    public static Coin getNICKLE() {
        return NICKLE;
    }

    public static Coin getPENNY() {
        return PENNY;
    }
    
    public BigDecimal getValue(){
        return value;
    }
    
}
