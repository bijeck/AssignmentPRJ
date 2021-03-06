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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "ProcessRequestServlet", urlPatterns = {"/ProcessRequestServlet"})
public class ProcessRequestServlet extends HttpServlet {

    private final String LOGIN_PAGE = "loginPage";
    private final String SEARCH_PAGE = "searchPage";

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
        String url = LOGIN_PAGE;
        try {
            //1. Get cookies from request
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. Traverse all cookie to check authetication
                for (Cookie cookie : cookies) {
                    //3. Get username and password from name value
                    String username = cookie.getName();
                    String password = cookie.getValue();
                    //4. call DAO to check authetication
                    RegistrationDAO dao = new RegistrationDAO();
                    String lastname = dao.checkLogin(username, password);

                    if (!lastname.isEmpty()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("LASTNAME", lastname);
                        session.setAttribute("LOGIN_USERNAME", username);
                        url = SEARCH_PAGE;
                        break;
                    }//end authetication is success checked
                }//end of traverse cookies
            }//end cookies is existed
        } catch (SQLException ex) {
            log("ProcessRequestServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("ProcessRequestServlet _ Naming " + ex.getMessage());
        } finally {
            ServletContext context = request.getServletContext();
            Map<String, String> roadmap = (Map<String, String>) context.getAttribute("ROADMAP");
            if (roadmap != null) {
                if (url.contains("?")) {
                    String resourse = url.substring(0, url.indexOf("?"));
                    String parameters = url.substring(url.indexOf("?"));
                    url = roadmap.get(resourse) + parameters;
                } else {
                    url = roadmap.get(url);
                }
            }
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
