<%-- 
    Document   : results
    Created on : Apr 19, 2025, 11:31:37 PM
    Author     : lando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="business.StudySession"%>
<%
    StudySession studySession = (StudySession) request.getAttribute("studySession");
    if (studySession == null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    int correctCount = studySession.getSessionRight();
    int wrongCount = studySession.getSessionWrong();
    int totalCount = correctCount + wrongCount;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Study Session Results</title>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body class="bg-primary">
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="col-8 text-center">
                    <h1 class="mb-4">Study Session Results</h1>
                    <p class="fs-4">Total Cards: <strong><%= totalCount %></strong></p>
                    <p class="fs-4 text-success">Correct Answers: <strong><%= correctCount %></strong></p>
                    <p class="fs-4 text-danger">Wrong Answers: <strong><%= wrongCount %></strong></p>
                    <a href="dashboard.jsp" class="btn btn-primary mt-4">Return to Dashboard</a>
                </div>
            </div>
        </div>
        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
