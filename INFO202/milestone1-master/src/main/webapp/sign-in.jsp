<%-- 
    Document   : sign-in
    Created on : 23 Aug 2022, 6:06:00 pm
    Author     : putsuthisrisinlpa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf"%>
        <header><h1>Sign In</h1></header>
        <main>
              <%
					String messages = (String) session.getAttribute("error-messages");
					messages = messages != null ? messages : "";
                                        
                                        // clear validation message
                                        session.setAttribute("error-messages", "");
            %>
            
            <div class="error-messages"><%= messages%></div>
            <form method="POST" action="sign-in">
                <label for="username">Username</label>
                <input type="text" name="username" required/>
                <label for="password">Password</label>
                <input type="password" name="password" required/>
                <button type="submit">Sign In</button>
            </form>
            <p>If you do not have an account then you can <a href="create-account.jsp">create one</a></p>
        </main>
    </body>
</html>
