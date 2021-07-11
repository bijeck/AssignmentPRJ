/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sump.cart.CartObject;
import sump.item.ItemDAO;
import sump.item.ItemDTO;
import sump.item.OutOfStockItem;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String SHOPPING = "shopping";
    private final String ERROR_SHOPPING_PAGE = "errorShopping";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_SHOPPING_PAGE;
        String customerName = request.getParameter("txtCustomerName");
        String customerAddress = request.getParameter("txtCustomerAddress");

        try {
            if (customerName.equals("")) {
                customerName = "Customer";
            }
            if (customerAddress.equals("")) {
                customerAddress = "Viet Nam";
            }
            //1. go to cart place
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. staff take cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                OutOfStockItem outItems = (OutOfStockItem) session.getAttribute("OUT_OF_STOCK_ITEMS");
                if (cart != null) {
                    //3. take item
                    Map<String, ItemDTO> items = cart.getItems();
                    if (items != null) {
                        //4. check out item
                        //5. call dao
                        boolean result = false;
                        ItemDAO dao = new ItemDAO();
                        for (ItemDTO item : items.values()) {
                            result = dao.checkOutItems(customerName,
                                    customerAddress,
                                    item.getSku(),
                                    item.getQuantity(),
                                    item.getPrice() * item.getQuantity(),
                                    item.getQuantityInStock());
                            if (!result) {
                                break;
                            }//end traveser item if have error in DB
                        }//end traveser item
                        if (result) {
                            url = SHOPPING;
                            session.removeAttribute("CART");
                            session.removeAttribute("ITEMS");
                            if (outItems != null) {
                                session.removeAttribute("OUT_OF_STOCK_ITEMS");
                            }
                        }
                    }//items are existed
                }//end if cart is existed
            }//end if session is existed
        } catch (SQLException ex) {
            log("CheckOutServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet _ Naming " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
