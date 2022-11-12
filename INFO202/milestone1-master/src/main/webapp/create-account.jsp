<%-- 
    Document   : create-account
    Created on : 23 Aug 2022, 6:07:17 pm
    Author     : putsuthisrisinlpa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf"%>
        <header><h1>Create Account</h1></header>
        <main>
            <%
					String messages = (String) session.getAttribute("error-messages");
					messages = messages != null ? messages : "";
                                        
                                        // clear validation message
                                        session.setAttribute("error-messages", "");
            %>
            
            <div class="error-messages"><%= messages%></div>
            
          
            <form method="POST" action="create-account">
                <label for="username">Username</label>
                <input type="text" name="username" required/>
                <label for="firstName">First Name</label>
                <input type="text" name="firstName" required/>
                <label for="surname">Last Name</label>
                <input type="text" name="surname" required/>
                <label for="emailAddress">Email</label>
                <input type="email" name="emailAddress" required/>
                <label for="shippingAddress">Address</label>
                <textarea name="shippingAddress"></textarea>
                <label for="password">Password</label>
                <input type="password" name="password" required/>
                <button type="submit">Create Account</button>
            </form>
        </main>
    </body>
</html>
