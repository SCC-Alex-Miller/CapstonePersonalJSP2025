<%-- 
    Document   : publicPack
    Created on : Apr 21, 2025, 1:12:08 PM
    Author     : lando
--%>

<%@page import="data.PackCategoryDA"%>
<%@page import="business.PackCategory"%>
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

    //Setup all Users before page render
    Pack pack = new Pack();
    pack.setUser(loggedInUser);
    String error = "";

    try {
        LinkedHashMap<Integer, Pack> allPublicPacks = PackDA.seeAllPublicPacks();
        request.setAttribute("allPublicPacks", allPublicPacks);

    } catch (NamingException | SQLException e) {
        error = "Issue populating Accounts for User.";
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Public Packs</title>
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
                        <h1>Public Packs</h1>
                        <h3><c:out value= "${message}"/></h3>
                        <h3><c:out value= "${error}"/></h3>

                        <c:if test="${!allPublicPacks.isEmpty()}">
                            <table>               
                                <tr>
                                    <th>#</th>
                                    <th>Username</th>
                                    <th>View</th>
                                    <th style="width: 100%;">Pack Name</th>
                                    <th>Pack Category</th>
                                    <th>Download Pack</th>
                                </tr>
                                <c:forEach var="pack" items="${allPublicPacks}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${pack.value.user.userID ne loggedInUser.userID}">
                                                    <div class="dropdown show">
                                                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            ${pack.value.user.username}
                                                        </a>

                                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                            <form action="Report" method="post" class="m-0 p-0">
                                                                <input type="hidden" name="action" value="goToReportPage">
                                                                <input type="hidden" name="reportedUsername" value="${pack.value.user.username}">
                                                                <input type="submit" id="reportUser" class="dropdown-item" value="Report User">
                                                            </form>
                                                            <form action="Admin" method="post" class="m-0 p-0">
                                                                <input type="hidden" name="action" value="banUser">
                                                                <input type="submit" id="banUser" class="dropdown-item" value="Ban User">
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    ${pack.value.user.username}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form action="Pack" method="post">
                                                <input type="hidden" name="action" value="viewPack">
                                                <input type="hidden" name="packID" value="<c:out value='${pack.key}' />">
                                                <input type="hidden" name="packName" value="<c:out value='${pack.value.packName}' />">
                                                <input type="submit" value="View">
                                            </form>
                                        </td>
                                        <td>
                                            <form id="editForm" action="Pack" method="post">
                                                <input type="hidden" name="action" value="editPack">
                                                <input type="hidden" name="packID" value="<c:out value='${pack.key}' />">
                                                <input type="hidden" name="userID" value="<c:out value='${pack.value.user.userID}' />">
                                                <div class="rowFlex">
                                                    <input type="text" id="packText" name="updatedPackName" value="<c:out value="${pack.value.packName}" />">
                                                    <input type="submit" value="Edit">
                                                </div>
                                            </form>
                                        </td>
                                        <td>${pack.value.packCategoryName}</td>
                                        <td>
                                            <form action="Pack" method="post">
                                                <input type="hidden" name="action" value="downloadPack">
                                                <input type="hidden" name="packID" value="${pack.value.packID}">
                                                <input type="submit" value="Download">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div class="col-6">
                        <h1>Filter Options</h1>
                    </div>
                </div>
            </div>
        </div>
        <!-- jQuery, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
