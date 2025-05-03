<%-- 
    Document   : reportPage
    Created on : Apr 21, 2025, 9:31:13 PM
    Author     : lando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Report Page</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
    </head>
    <body class="bg-primary">
        <div class="col">
            <%@include file="nav.jsp"  %>
        </div>
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <h1>Report Page</h1>
                        <h2 class="success">${message}</h2>

                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <h2><li class="errors"><c:out value="${error.value}"/></li></h2>
                                    </c:forEach>
                        </ul><br>

                        <form action="Report" method="post">
                            <input type="hidden" name="action" value="addReport">
                            <label>Reported User: </label>
                            <input type="text" name="reportedUsername" value="<c:out value='${reportedUsername}'/>" readonly><br><br>
                            <label>Report Type: </label>
                            <select id="reportType" name="reportType">
                                <option value="Sexual">Sexual Content</option>
                                <option value="Violent">Violent or repulsive content</option>
                                <option value="Hateful">Hateful or abusive content</option>
                                <option value="Harassment">Harassment or bullying</option>
                                <option value="Harmful">Harmful or dangerous acts</option>
                                <option value="Misinformation">Misinformation</option>
                                <option value="Child">Child abuse</option>
                                <option value="Terrorism">Promotes terrorism</option>
                                <option value="Spam">Spam or misleading</option>
                            </select><br><br>
                            <label>Notes: </label>
                            <textarea class="form-control" id="reportUserNotes" name="reportUserNotes" rows="4" cols="50"></textarea><br><br>
                            <input type="hidden" name="reportedUserID" value="${reportedUserID}">
                            <input type="hidden" name="reportedPackID" value="${reportedPackID}">
                            <input type="submit" value="Report">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
