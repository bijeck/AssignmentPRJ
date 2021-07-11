/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class OutOfStockItem implements Serializable {

    private ArrayList<String> items;

    public OutOfStockItem() {
    }

    /**
     * @return the items
     */
    public ArrayList<String> getItems() {
        return items;
    }

    public void addItem(String sku) {
        if (sku == null) {
            throw new NullPointerException("OutOfStockItem.addItem _ NullPointer can't not find sku");
        } else {
            if (this.items == null) {
                this.items = new ArrayList<>();
            }
            this.items.add(sku);
        }
    }

    public void removeItem(String sku) {
        if (sku == null) {
            throw new NullPointerException("OutOfStockItem.removeItem _ NullPointer can't not find sku");
        } else {
            if (this.items == null) {
                this.items = new ArrayList<>();
            }
            this.items.remove(sku);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public boolean checkContainItems(String sku) {
        if (this.items != null) {
            if (this.items.contains(sku)) {
                return true;
            }
        }
        return false;
    }
}
