/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import dao.CustomerDAO;
import dao.DaoExceptionMapper;
import domain.Customer;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 *
 * @author sutpu703
 */
public class CustomerModule extends Jooby {

	private CustomerDAO dao;

	public CustomerModule(CustomerDAO dao) {
		this.dao = dao;

		post("/api/register", ctx -> {
			try{
				Customer customer = ctx.body().to(Customer.class);
				// validate customer fields
				new Validator().assertValid(customer);
				dao.save(customer);
				return ctx.send(StatusCode.CREATED);
			}catch (ConstraintsViolatedException ex) {
				// get the violated constraints from the exception
				ConstraintViolation[] violations = ex.getConstraintViolations();
				// create a nice error message for the user
				ErrorMessage message = new ErrorMessage();
				String msg = "";
				msg += "<ul class='form-messages'>";
				for (ConstraintViolation cv : violations) {
					message.addMessage(cv.getMessage());
				}
				msg += "</ul>";
				return ctx.setResponseCode(StatusCode.BAD_REQUEST).render(message);
			} catch (Exception ex) {
				String msg = new DaoExceptionMapper().messageFromException(ex);
				ErrorMessage message = new ErrorMessage();
				message.addMessage(msg);
				return ctx.setResponseCode(StatusCode.BAD_REQUEST).render(message);
			}
		});

		get("/api/customers/{username}", ctx -> {
			String username = ctx.path("username").value();
			Customer customer = dao.getByUsername(username);
			// remove sensitive information
			customer.setEmailAddress(null);
			customer.setPassword(null);
			customer.setShippingAddress(null);
			if (customer == null) {
				return ctx.send(StatusCode.NOT_FOUND);
			}
			return customer;

		});

	}

}
