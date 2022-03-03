/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.util.Objects;

/**
 *
 * @author 69591
 */
public class Item {
    private String price;
    private String num;
    private String name;

    public String getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public Item(String name, String price, String num) {
        this.price = price;
        this.num = num;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.price);
        hash = 37 * hash + Objects.hashCode(this.num);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.num, other.num)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Item() {
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    
}
