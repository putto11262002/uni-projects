/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.CustomerDAO;
import dao.DaoFactory;
import domain.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author putsuthisrisinlpa
 */
@WebServlet(urlPatterns = {"/sign-in"})
public class SignIn extends HttpServlet {

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
        CustomerDAO dao = DaoFactory.getCustomerDAO();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean result = dao.authenticate(username, password);

        if (result) {
            Customer customer = dao.getByUsername(username);
            customer.setPassword(null);
            request.getSession().setAttribute("customer", customer);
            response.sendRedirect("index.jsp");
        } else {

            request.getSession().setAttribute("error-messages", "<p>Incorrect username/password</p>");
            response.sendRedirect("sign-in.jsp");
        }
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
