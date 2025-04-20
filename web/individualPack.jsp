<%-- 
    Document   : individualPack
    Created on : Apr 5, 2025, 10:06:18 AM
    Author     : lando
--%>

<%@page import="business.Card"%>
<%@page import="data.CardDA"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="data.PackDA"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="business.Pack"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //code to direct users out off of the page if they're not logged in
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
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

    //Setup all Users before page render
    Pack pack = new Pack();
    pack.setUser(loggedInUser);
    String error = "";

    try {
        LinkedHashMap<Integer, Card> packCards = CardDA.selectPackCards(activePack.getPackID());
        request.setAttribute("packCards", packCards);

    } catch (NamingException | SQLException e) {
        error = "Issue populating cards for Pack.";
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accounts</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
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
                        <h3><c:out value= "${message}"/></h3>
                        <h3><c:out value= "${error}"/></h3>

                        <!-- USE A TABLE AND LIST FOR YOU TO SELECT EACH CREATED ACCOUNT -->
                        <c:if test="${!packCards.isEmpty()}">
                            <h1>Card List</h1>

                            <table>               
                                <tr>
                                    <th>#</th>
                                    <th style="width: 100%;">Card</th>
                                    <th>Delete</th>
                                </tr>
                                <c:forEach var="card" items="${packCards}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}" /></td>
                                        <td>
                                            <form action="Card" method="post">
                                                <input type="hidden" name="cardID" value="<c:out value='${card.key}' />">
                                                <input type="text" name="cardQuestion" class="form-control flex-grow-1" value="<c:out value='${card.value.cardQuestion}' />">
                                                <input type="text" name="cardAnswer" class="form-control flex-grow-1" value="<c:out value='${card.value.cardAnswer}' />">
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <form action="Card" method="post">
                                                <input type="hidden" name="action" value="deleteCard">
                                                <input type="hidden" name="cardID" value="${card.value.cardID}">
                                                <input type="submit" value="Delete" class="btn btn-sm btn-danger">
                                            </form>
                                        </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div class="col-6">
                        <form action="Card" method="post">
                            <h1>Add Card</h1>
                            <input type="hidden" name="action" value="addCard">
                            <div class="columnFlex">
                                <label for="cardQuestion">Card Question</label>
                                <input type="text" name="cardQuestion" class="form-control flex-grow-1" required><br>
                            </div>
                            <div class="columnFlex">
                                <label for="cardAnswer">Card Answer</label>
                                <input type="text" name="cardAnswer" class="form-control flex-grow-1" required><br>
                            </div>
                            <input type="submit" value="Submit" id="Submit">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>

