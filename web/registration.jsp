<%-- 
    Document   : registration
    Created on : Mar 2, 2025, 9:43:32 AM
    Author     : lando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
        <title>Registration for Expense Tracker</title>
        
    </head>
    <body class="bg-primary">
        <div class="col">
            <%@include file="nav.jsp"  %>
        </div>
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <h1>Registration Form</h1>
                        <h2 class="success">${message}</h2>

                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <h2><li class="errors"><c:out value="${error.value}"/></li></h2>
                                    </c:forEach>
                        </ul><br>

                        <form action="Public" method="post">
                            <input type="hidden" name="action" value="registration">
                            <label>Username: </label>
                            <input type="text" name="username" value="<c:out value='${user.username}'/>" ><br><br>
                            <label>Email: </label>
                            <input type="text" name="email" value="<c:out value='${user.email}'/>" ><br><br>
                            <label>Password: </label>
                            <input type="text" name="password" value="<c:out value='${user.password}'/>" ><br><br>
                            <input type="submit" value="Register">
                        </form><br><br>

                        <p>Please create your account with this Registration Form for the first time.<br>
                            If you have already registered before, please click the Go to Login Form button.</p>

                        <form action="Public" method="get">
                            <input type="hidden" name="action" value="goToIndexPage">
                            <button class="btn btn-primary" type="submit">Go to Login Form<svg xmlns="http://www.w3.org/2000/svg" width="25" height="20" fill="currentColor" class="bi bi-arrow-right ms-1" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
                                </svg>
                            </button>           
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
