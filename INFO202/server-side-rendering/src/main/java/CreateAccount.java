/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.CustomerDAO;
import dao.DaoExceptionMapper;
import dao.DaoFactory;
import domain.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 *
 * @author putsuthisrisinlpa
 */
@WebServlet(urlPatterns = {"/create-account"})
public class CreateAccount extends HttpServlet {

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
        try {
            CustomerDAO dao = DaoFactory.getCustomerDAO();

            // extract form data
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String surname = request.getParameter("surname");
            String emailAddress = request.getParameter("emailAddress");
            String shippingAddress = request.getParameter("shippingAddress");

            Customer customer = new Customer(username, password, firstName, surname, shippingAddress, emailAddress);

            // validate customer fields
            new Validator().assertValid(customer);

            // Save customer
            dao.save(customer);

            // Redirect to sign in page
            response.sendRedirect("sign-in.jsp");

        } catch (ConstraintsViolatedException ex) {
            // get the violated constraints from the exception
            ConstraintViolation[] violations = ex.getConstraintViolations();
            // create a nice error message for the user
            String msg = "";
            msg += "<ul class='form-messages'>";
            for (ConstraintViolation cv : violations) {
                msg += "<li>" + cv.getMessage() + "</li>";
            }
            msg += "</ul>";
            request.getSession().setAttribute("error-messages", msg);
            response.sendRedirect("create-account.jsp");
        } catch (Exception ex) {
            String msg = new DaoExceptionMapper().messageFromException(ex);
            request.getSession().setAttribute("error-messages", "<p>" + msg + "</p>");
            response.sendRedirect("create-account.jsp");
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
