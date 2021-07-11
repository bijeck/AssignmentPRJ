/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sump.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sump.registration.RegistrationDAO;

/**
 *
 * @author Administrator
 */
public class LoginServlet extends HttpServlet {

    private final String SEARCH_PAGE = "searchPage";
    private final String INVALID_PAGE = "invalid";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID_PAGE;
        //get 03 parameter

        try {

            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            //call DAO -> new DAO object & call method DAO
            RegistrationDAO dao = new RegistrationDAO();
            String lastname = dao.checkLogin(username, password);

            if (!lastname.isEmpty()) {
                url = SEARCH_PAGE;
                //tao session de luu thong tin nguoi dung
                HttpSession session = request.getSession();
                //get full name from username via Dao
                session.setAttribute("LASTNAME", lastname);
                session.setAttribute("LOGIN_USERNAME", username);
                //Luu thong tin xuong file cookie client
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60 * 3);
                response.addCookie(cookie);
            }//end if lastname is existed
        } catch (SQLException ex) {
            log("LoginServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet _ Naming " + ex.getMessage());
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
