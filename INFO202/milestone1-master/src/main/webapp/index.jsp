<%@page import="java.util.Collection"%>
<%@page import="dao.DaoFactory"%>
<%@page import="dao.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
                <link rel="stylesheet" href="css/style.css"/>
	</head>
	<body>
		<%@include file="WEB-INF/jspf/navigation.jspf"%>
                <header><h1>Welcome to Tech Center</h1></header>
                
                <main>
                  
                    <div class="category-container">
                    <%
                        ProductDAO dao = DaoFactory.getProductDAO();
                        
                        Collection<String> categories = dao.getCategories();
                       
                        for (String category: categories){
                            
                        
                        
                     %>
                     
                     <div class="category">
                         <a href="view-products.jsp?category=<%=category %>"><%=category%></a>
<!--                         <div></div>-->
                             
                     </div>
                     
                     <%
                         }
                     %>
                     
                    </div>
                     
                     
                </main>
	</body>
</html>
