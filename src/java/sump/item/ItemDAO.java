/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.item;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sump.utils.DBHelpers;

/**
 *
 * @author Administrator
 */
public class ItemDAO implements Serializable {

    private List<ItemDTO> items;

    public List<ItemDTO> getItems() {
        return items;
    }

    public void loadProduct() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. get connection DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. create sql statement
                String sql = "Select sku, name, price, quantity "
                        + "From Product "
                        + "Where sold < quantity";
                //3. create statement to set sql
                stm = con.prepareCall(sql);
                //4. execute statement
                rs = stm.executeQuery();
                //5. process
                while (rs.next()) {
                    //get field coloumn
                    String sku = rs.getString("sku");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantityInStock = rs.getInt("quantity");

                    //create dto instance
                    ItemDTO item = new ItemDTO(sku, name, price, quantityInStock, 0);
                    //add to list items
                    if (this.items == null) {
                        this.items = new ArrayList<>();
                    }
                    //list items is avaiable
                    this.items.add(item);
                }//end rs has more one record
            }//end if connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public ItemDTO checkExistedItem(String id)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. get connection DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. create sql statement
                String sql = "Select sku, name, price, quantity "
                        + "From Product "
                        + "Where sku= ?";
                //3. create statement to set sql
                stm = con.prepareCall(sql);
                stm.setString(1, id);
                //4. execute statement
                rs = stm.executeQuery();
                //5. process
                if (rs.next()) {
                    String sku = rs.getString("sku");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantityInStock = rs.getInt("quantity");

                    //create dto instance
                    ItemDTO item = new ItemDTO(sku, name, price, quantityInStock, 0);
                    return item;
                }//end rs has more one record
            }//end if connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean updateQuantityToStock(String sku, int quantity, int quantityInStock)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Update Product "
                        + "Set sold= ? , quantity=? "
                        + "Where sku= ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setInt(2, quantityInStock-quantity);
                stm.setString(3, sku);
                //4.excute
                int row = stm.executeUpdate();
                //5.process
                if (row > 0) {
                    return true;
                }
            }//end if connection is existed
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;

    }

    public boolean checkOutItems(String name,String address,String sku, 
            int quantity, double totalPrice, int quantityInStock)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                boolean result = updateQuantityToStock(sku, quantity, quantityInStock);
                if (result) {
                    //2.create sql statement string
                    String sql = "Insert into OrderDetail( name, address, sku, quantity, totalPrice) "
                            + "Values( ?, ?, ?, ?, ?)";
                    //3.create statement to set SQL
                    stm = con.prepareStatement(sql);
                    stm.setString(1, name);
                    stm.setString(2, address);
                    stm.setString(3, sku);
                    stm.setInt(4, quantity);
                    stm.setDouble(5, totalPrice);
                    //4.excute
                    int row = stm.executeUpdate();
                    //5.process
                    if (row > 0) {
                        return true;
                    }
                }//end if update to stock successfully
            }//end if connection is existed
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;

    }

}
