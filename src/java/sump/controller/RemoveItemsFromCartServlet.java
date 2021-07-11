/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sump.cart.CartObject;
import sump.item.ItemDTO;
import sump.item.OutOfStockItem;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "RemoveItemsFromCartServlet", urlPatterns = {"/RemoveItemsFromCartServlet"})
public class RemoveItemsFromCartServlet extends HttpServlet {

    private final String VIEW_CART = "viewCart";

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
        String url = VIEW_CART;
        try {
            //1. customer goes to cart place
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. customer take cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                OutOfStockItem outOfStockItems = (OutOfStockItem) session.getAttribute("OUT_OF_STOCK_ITEMS");
                if (cart != null) {
                    //3. get item
                    Map<String, ItemDTO> items = cart.getItems();
                    if (items != null) {
                        //4. get all selected items
                        String[] removeItems = request.getParameterValues("chkItem");
                        if (removeItems != null) {
                            for (String item : removeItems) {
                                cart.removeItemFromCart(item);
                                if (outOfStockItems != null) {
                                    if (outOfStockItems.checkContainItems(item)) {
                                        outOfStockItems.removeItem(item);
                                    }
                                    session.setAttribute("OUT_OF_STOCK_ITEMS", outOfStockItems);
                                }
                            }//end traverser each item
                            session.setAttribute("CART", cart);
                        }//end removed item has choiced
                    }//items are existed
                }//cart is existed
            }//session is existed
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
