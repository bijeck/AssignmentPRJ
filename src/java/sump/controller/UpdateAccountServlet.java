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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sump.registration.RegistrationDAO;
import sump.registration.RegistrationUpdateError;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    private final String ERROR_UPDATE_OCCUR_PAGE = "searchLastName";
    private final String ERROR_PAGE = "errors";
    private final String SEARCH = "searchLastName";

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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String checkAdmin = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        boolean role = false;
        String url = ERROR_PAGE;
        RegistrationUpdateError errors = new RegistrationUpdateError();
        boolean foundErr = false;
        try {
            ServletContext context = request.getServletContext();
            Map<String, String> roadmap = (Map<String, String>) context.getAttribute("ROADMAP");

            if (checkAdmin != null) {
                role = true;
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Password is required from 6 to 30 chars");
                errors.setUsername(username);
            }

            if (foundErr) {
                if (roadmap != null) {
                    url = roadmap.get(ERROR_UPDATE_OCCUR_PAGE)
                            + "?txtSearchValue=" + searchValue;
                    request.setAttribute("UPDATE_ERRORS", errors);
                }//end if roadmap existed

            } else {
                //call DAO
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.updateAccount(username, password, role);
                if (result) {
                    if (roadmap != null) {
                        //call search again
                        url = roadmap.get(SEARCH)
                                + "?txtSearchValue=" + searchValue;
                    }//end if roadmap existed
                }//end if update is successfully
            }
        } catch (SQLException ex) {
            log("UpdateAccountServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateAccountServlet _ Naming " + ex.getMessage());
        } finally {
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
