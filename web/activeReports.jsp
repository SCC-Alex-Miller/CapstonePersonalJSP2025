<%-- 
    Document   : reports
    Created on : May 4, 2025, 9:18:52 PM
    Author     : lando
--%>

<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="data.ReportDA"%>
<%@page import="business.Report"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="business.Pack"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //code to direct users out off of the page if they're not logged in
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect("Public");
        return;
    }

    LinkedHashMap<Integer, Report> activeReports = new LinkedHashMap();
    String error = "";

        try {
            activeReports = ReportDA.seeAllActiveReports(true);
            request.setAttribute("activeReports", activeReports);
        } catch (NamingException | SQLException e) {
            error = "Unable to get reports";
        }


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Active Reports</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
    </head>
    <body class="bg-primary">
        <div class="col">
            <%@include file="nav.jsp" %>
        </div>
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="row justify-content-center">
                    <div class="col-12">
                        <h1>Active Reports</h1>
                        <c:if test="${!activeReports.isEmpty()}">
                            <table>               
                                <tr>
                                    <th>#</th>
                                    <th>Reported User</th>
                                    <th>Reported Pack</th>
                                    <th>Report Type</th>
                                    <th>Created By</th>
                                    <th style="width: 50%;">Report Notes</th>
                                    <th style="width: 50%;">Admin Notes</th>
                                    <th>Approve Report</th>
                                    <th>Reject Report</th>
                                </tr>
                                <c:forEach var="report" items="${activeReports}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}" /></td>
                                        <td>${report.value.reportedUsername}</td>
                                        <td>
                                            <form action="Report" method="post">
                                                <input type="hidden" name="action" value="viewPack">
                                                <input type="hidden" name="reportedUserID" value="<c:out value='${report.value.reportedUserID}' />">
                                                <input type="hidden" name="packID" value="<c:out value='${report.value.reportedPackID}' />">
                                                <input type="submit" value="View">
                                            </form>
                                        </td>
                                        <td>${report.value.reportType}</td>
                                        <td>${report.value.reportCreatedUsername}</td>
                                        <td>${report.value.reportUserNotes}</td>
                                    <form action="Report" method="post">
                                        <input type="hidden" name="action" value="editReport">
                                        <input type="hidden" name="reportID" value="<c:out value='${report.value.reportID}' />">
                                        <td>
                                            <textarea class="form-control" name="adminNotes" rows="4" cols="50"></textarea>
                                        </td>
                                        <td>
                                            <button type="submit" name="answer" value="approve" class="btn btn-success">Approve</button>
                                        </td>
                                        <td>
                                            <button type="submit" name="answer" value="reject" class="btn btn-danger">Reject</button>
                                        </td>
                                    </form>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
