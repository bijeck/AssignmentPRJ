/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.registration;

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
public class RegistrationDAO implements Serializable {

    public String checkLogin(String username, String password)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String lastname="";
        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Select username , lastname "
                        + "From Registration "
                        + "Where username = ? And password = ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4.excute
                rs = stm.executeQuery();
                //5.process
                if (rs.next()) {
                    lastname = rs.getString("lastname");
                }
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
        return lastname;
    }
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastname(String searchValue)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4.excute
                rs = stm.executeQuery();
                //5.process
                while (rs.next()) {
                    //get field coloumn
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    //create DTO instance
                    RegistrationDTO dto = new RegistrationDTO(username,
                            password, lastname, role);
                    //add to accounts list
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }
                    //account is avaiable
                    this.accounts.add(dto);
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

    public boolean deleteAccount(String username)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Delete From Registration "
                        + "Where username= ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
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

    public boolean updateAccount(String username,
            String password, boolean role)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Update Registration "
                        + "Set password= ?, isAdmin= ? "
                        + "Where username= ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, role);
                stm.setString(3, username);
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
    public boolean editAccount(String username,
            String password,String lastname, boolean role)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Update Registration "
                        + "Set password= ?,lastname= ?, isAdmin= ? "
                        + "Where username= ?";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, lastname);
                stm.setBoolean(3, role);
                stm.setString(4, username);
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
    public boolean createAccount(RegistrationDTO dto)
            throws SQLException, NamingException {
        if (dto == null) {
            return false;
        }
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1.connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2.create sql statement string
                String sql = "Insert Into Registration(username, password, lastname, isAdmin) "
                        + "Values(?, ?, ?, ?)";
                //3.create statement to set SQL
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getLastname());
                stm.setBoolean(4, dto.isRole());
                //4.excute Query
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

}
