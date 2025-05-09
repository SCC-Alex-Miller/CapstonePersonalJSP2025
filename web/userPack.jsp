<%-- 
    Document   : personalPack
    Created on : Mar 25, 2025, 9:32:10 PM
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
        LinkedHashMap<Integer, Pack> allUserPacks = PackDA.seeAllUserPacks(loggedInUser.getUserID());
        request.setAttribute("allUserPacks", allUserPacks);

        LinkedHashMap<Integer, PackCategory> packCategoryList = PackCategoryDA.selectAllPackCategoriesByUserID(loggedInUser.getUserID());
        request.setAttribute("packCategoryList", packCategoryList);

    } catch (NamingException | SQLException e) {
        error = "Issue populating Accounts for User.";
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${loggedInUser.username}'s Packs</title>
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
                        <h1>${user.username}</h1>
                        <form action="Pack" method="post">
                            <h1>Pack Creation</h1>
                            <input type="hidden" name="action" value="addPack">
                            <div class="columnFlex">
                                <label for="newPack">Pack Name</label>
                                <input type="text" name="newPack" required><br>
                                <select name="packCategory">
                                    <option value="" disabled selected>Select your option</option>
                                    <c:forEach items="${packCategoryList}" var="packCategory" >
                                        <option value="${packCategory.value.packCategoryID}" >${packCategory.value.packCategoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <input type="submit" value="Submit" id="submit">
                        </form>
                        <br>
                        <h3><c:out value= "${message}"/></h3>
                        <h3><c:out value= "${error}"/></h3>

                        <c:if test="${!allUserPacks.isEmpty()}">
                            <h1>Pack List</h1>

                            <table>               
                                <tr>
                                    <th>#</th>
                                    <th>Study</th>
                                    <th>View</th>
                                    <th style="width: 100%;">Pack Name</th>
                                    <th>Public?</th>
                                    <th>Pack Category</th>
                                    <th></th>
                                    <th>Delete Pack</th>
                                </tr>
                                <c:forEach var="pack" items="${allUserPacks}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}" /></td>
                                        <td>
                                            <form action="Pack" method="post">
                                                <input type="hidden" name="action" value="studyPack">
                                                <input type="hidden" name="packID" value="<c:out value='${pack.key}' />">
                                                <input type="hidden" name="packName" value="<c:out value='${pack.value.packName}' />">
                                                <input type="submit" value="Study">
                                            </form>
                                        </td>
                                        <td>
                                            <form action="Pack" method="post">
                                                <input type="hidden" name="action" value="viewPack">
                                                <input type="hidden" name="packID" value="<c:out value='${pack.key}' />">
                                                <input type="hidden" name="packName" value="<c:out value='${pack.value.packName}' />">
                                                <input type="submit" value="View">
                                            </form>
                                        </td>
                                    <form id="editForm" action="Pack" method="post">
                                        <td>
                                            <input type="hidden" name="action" value="editPack">
                                            <input type="hidden" name="packID" value="<c:out value='${pack.key}' />">
                                            <input type="hidden" name="userID" value="<c:out value='${pack.value.user.userID}' />">
                                            <input type="text" id="packText" name="updatedPackName" value="<c:out value="${pack.value.packName}" />">
                                        </td>
                                        <td>
                                            <input type="checkbox" id="isPublic" name="isPublic" <c:if test="${pack.value.isPublic}">checked</c:if> >
                                            </td>
                                            <td>
                                                <select name="newPackCategoryID" required>
                                                <c:forEach var="category" items="${packCategoryList}">
                                                    <option value="${category.value.packCategoryID}"
                                                            <c:if test="${category.value.packCategoryID == pack.value.packCategoryID}">selected</c:if>>
                                                        ${category.value.packCategoryName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="submit" value="Edit">
                                        </td>
                                    </form>
                                    <td>
                                        <form action="Pack" method="post">
                                            <input type="hidden" name="action" value="deletePack">
                                            <input type="hidden" name="packID" value="${pack.value.packID}">
                                            <input type="submit" value="Delete" class="btn btn-sm btn-danger">
                                        </form>
                                    </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div class="col-6">
                        <form action="PackCategory" method="post">
                            <h1>Add Pack Category</h1>
                            <input type="hidden" name="action" value="addPackCategory">
                            <div class="columnFlex">
                                <label for="newPackCategoryName">Pack Category Name</label>
                                <input type="text" name="newPackCategoryName" required><br>
                            </div>
                            <input type="submit" value="Submit" id="submit">
                        </form>
                        <br>
                        <table>
                            <tr>
                                <th>#</th>
                                <th style="width: 100%;">Category</th>
                                <th>Delete Category</th>
                            </tr>
                            <c:forEach var="packCategory" items="${packCategoryList}" varStatus="status">
                                <tr>
                                    <td><c:out value="${status.count}" /></td>
                                    <td>
                                        <form id="editForm" action="PackCategory" method="post">
                                            <input type="hidden" name="action" value="editPackCategory">
                                            <input type="hidden" name="packCategoryID" value="<c:out value='${packCategory.key}' />">
                                            <div class="rowFlex">
                                                <input type="text" id="packCategoryName" name="editedPackCategoryName" value="<c:out value="${packCategory.value.packCategoryName}" />">
                                                <input type="submit" value="Edit">
                                            </div>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="PackCategory" method="post">
                                            <input type="hidden" name="action" value="deletePackCategory">
                                            <input type="hidden" name="key" value="<c:out value='${packCategory.key}' />">
                                            <input type="submit" value="Delete" class="btn btn-sm btn-danger">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
