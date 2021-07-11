/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Administrator
 */
public class DBHelpers implements Serializable{
    public static Connection makeConnection()
    throws /*ClassNotFoundException,*/ SQLException, NamingException{
        //1.get current system file
        Context context = new InitialContext();
        //2.get container context
        Context tomcatContext = (Context)context.lookup("java:comp/env");
        //3,get DB source
        DataSource ds = (DataSource) tomcatContext.lookup("DSBlink");
        //4.get connection
        Connection con = ds.getConnection();
        
        return con;
//        //1. load driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2. Create connection string
//        String url = "jdbc:sqlserver://localhost:1433"
//                + ";databaseName=LOGINACCOUNTS"
//                + ";instanceName=PHUOCSUIT";
//        //3. open connection
//        Connection con = DriverManager.getConnection(url, "sa","Su0205zz");
//        
//        return con;
        
    }
}
