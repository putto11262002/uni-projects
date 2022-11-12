<%-- 
    Document   : view-products
    Created on : 23 Aug 2022, 6:12:14 pm
    Author     : putsuthisrisinlpa
--%>

<%@page import="java.util.Collection"%>
<%@page import="dao.DaoFactory"%>
<%@page import="dao.ProductDAO"%>
<%@page import="domain.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Products</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf"%>
        <header><h1>Sign in</h1></header>
        <main>
            
            <%
                
                 // check if the user is log in
                if (customer == null) response.sendRedirect("sign-in.jsp");
            %>
                    
            <%
                
                
                ProductDAO dao = DaoFactory.getProductDAO();
            %>

            <a href="view-products.jsp?category=All"><button>All</button></a>

            <%
                Collection<String> categories = dao.getCategories();
                for (String category : categories) {
            %>
            <a href="view-products.jsp?category=<%= category%>"><button><%= category%></button></a>
                    <%
                        }
                    %>
            
            
            <table>
                 <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Available</th>
                    </tr>
                </thead>
                
                <tbody>
                     <%
                        // get the category from the query parameter
                        String category = request.getParameter("category");
                        // declare the products collection
                        Collection<Product> products;
                        // if there is no category parameter, or "All" is requested, return all products
                        if (category == null || category.equals("All")) {
                            products = dao.getProducts();
                        } else {
                            // otherwise, get the products for the requested major
                            products = dao.filterByCategory(category);
                        }
                        for (Product  product: products) {
                    %>
                    
                        <tr>
                        <td><%= product.getName() %></td>
                        <td><%= product.getDescription() %></td>
                        <td><%= product.getListPrice() %></td>
                        <td><%= product.getQuantityInStock() %></td>
                      
                        <td>
                            <form action="buy-product" method="POST"><input type="hidden" name="id" value="<%= product.getProductId() %>"><button>Buy</button></form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    
                </tbody>
                    

            </table>
        </main>
    </body>
</html>
