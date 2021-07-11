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
import sump.registration.RegistrationDTO;
import sump.registration.RegistrationInsertError;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {
    
    private final String LOGIN_PAGE = "loginPage";
    private final String ERROR_CREATE_PAGE = "createAccountPage";

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
        String corfirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        
        RegistrationInsertError errors = new RegistrationInsertError();
        boolean foundErr = false;
        String url = ERROR_CREATE_PAGE;
        
        try {
            ServletContext context = request.getServletContext();
            Map<String,String> roadmap = (Map<String,String>)context.getAttribute("ROADMAP");
            
            //1. check all user Err
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                foundErr = true;
                errors.setUsernameLengthErr("Username is required from 6 to 20 chars");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Password is required from 6 to 30 chars");
            } else if (!corfirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setComfirmNotMatch("Confirm must match password");
            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullnameLengthErr("Fullname is required from 6 to 50 chars");
            }
            if (foundErr) {
                request.setAttribute("INSERT_ERRORS", errors);
            } else {
                //. Insert to DB-cal DAO
                RegistrationDTO dto
                        = new RegistrationDTO(username, password, fullname, false);
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createAccount(dto);
                
                if (result) {
                    //.transfer to login
                    url = LOGIN_PAGE;
                }//end account is created
            }
            if(roadmap!=null){
                url = roadmap.get(url);
            }//end if roadmap existed
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateNewAccountServlet _ SQL " + ex.getMessage());
            if(msg.contains("duplicate")){
                errors.setUsernameIsExisted(username+" existed!!!");
                request.setAttribute("INSERT_ERRORS", errors);
            }
        } catch (NamingException ex) {
            log("CreateNewAccountServlet _ Naming " + ex.getMessage());
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
