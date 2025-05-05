<%-- 
    Document   : results
    Created on : Apr 19, 2025, 11:31:37 PM
    Author     : lando
--%>

<%@page import="business.Pack"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="business.StudySession"%>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect("Public");
        return;
    }

    Pack activePack = (Pack) request.getSession().getAttribute("activePack");
    if (activePack == null) {
        String message = "Pack Unavailable";
        response.sendRedirect("Pack");
        request.setAttribute("message", message);
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Study Session Results</title>
        <link rel="stylesheet" href="styles/main.css">
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
    </head>
    <body class="bg-primary">
        <div class="col">
            <nav>    
                <%@include file="nav.jsp" %>
            </nav>
        </div>
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <h1 class="mb-4">Study Session Results</h1>
                        <p class="fs-4">Total Cards: <strong>${totalCards}</strong></p>
                        <p class="fs-4 text-success">Right Answers: <strong>${rightCount}</strong></p>
                        <p class="fs-4 text-danger">Wrong Answers: <strong>${wrongCount}</strong></p>
                    </div>
                </div>
            </div>
        </div>
        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
