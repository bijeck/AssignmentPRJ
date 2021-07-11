/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import sump.item.ItemDAO;
import sump.item.ItemDTO;

/**
 *
 * @author Administrator
 */
public class CartObject implements Serializable {

    private Map<String, ItemDTO> items;

    public Map<String, ItemDTO> getItems() {
        return items;
    }

    public void addItemToCart(String sku) throws SQLException, NamingException {
        //call dao
        ItemDAO dao = new ItemDAO();
        //1. check Item's id is existed--Check tu DB
        if (sku == null) {
            return;
        }
        if (sku.trim().isEmpty()) {
            return;
        }
        ItemDTO item = dao.checkExistedItem(sku);
        if (item !=null) {
            //2. when item's id is existed, checking existed items
            if (this.items == null) {
                this.items = new HashMap<>();
            }
            //3. when item has existed, checking existed id
            int quantity = 1;
            if (this.items.containsKey(sku)) {
                quantity = this.items.get(sku).getQuantity() + 1;
            }
            //4. update items
            item.setQuantity(quantity);
            this.items.put(sku,item);
        }//end if id is existed
    }
    public void removeItemFromCart(String sku){
        //1. check items is existed
        if(this.items== null){
            return;
        }
        //2. when items has existed, check existed id
        if(this.items.containsKey(sku)){
            this.items.remove(sku);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
}
