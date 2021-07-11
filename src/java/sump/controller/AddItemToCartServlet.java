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
import sump.item.ItemDTO;
import sump.item.OutOfStockItem;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {

    private final String SHOPPING_PAGE = "shoppingPage";

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
        String url = SHOPPING_PAGE;
        try {
            //1. Customer goes to cart place
            HttpSession session = request.getSession();
            //2. Customer get shopping cart
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            //3. customer take item
            String sku = request.getParameter("txtSku");
            //4. customer drop item to cart
            cart.addItemToCart(sku);
            session.setAttribute("CART", cart);
            //4. update quantity in stock
            Map<String, ItemDTO> listItems = cart.getItems();
            if (listItems != null) {
                ItemDTO dto = listItems.get(sku);
                if (dto != null) {
                    int quantityUpdate = dto.getQuantity();
                    int quantityInstock = dto.getQuantityInStock();
                    if (quantityUpdate == quantityInstock) {
                        OutOfStockItem outOfStockItem
                                = (OutOfStockItem) session.getAttribute("OUT_OF_STOCK_ITEMS");
                        if (outOfStockItem == null) {
                            outOfStockItem = new OutOfStockItem();
                        }
                        outOfStockItem.addItem(sku);
                        session.setAttribute("OUT_OF_STOCK_ITEMS", outOfStockItem);
                    }
                }//end if dto existed
            }//end if list items existed
            //5. redirect to online shopping page
        } catch (SQLException ex) {
            log("AddItemToCartServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddItemToCartServlet _ Naming " + ex.getMessage());
        } catch (NullPointerException ex) {
            log("AddItemToCartServlet _ NullPointer " + ex.getMessage());
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
