<%-- 
    Document   : studySession
    Created on : Apr 6, 2025, 2:47:32 PM
    Author     : lando
--%>

<%@page import="business.Pack"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="business.Card"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

    LinkedHashMap<Integer, Card> packCards = (LinkedHashMap<Integer, Card>) request.getAttribute("packCards");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Study Session</title>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body class="bg-primary">
        <div class="col">
            <%@include file="nav.jsp" %>
        </div>
        <div class="container bg-primary-subtle border rounded-3 shadow-lg py-5 my-5">
            <div class="row justify-content-center">
                <div class="row justify-content-center">
                    <div class="col-6 text-center">
                        <h1 class="mb-4">Study Session</h1>

                        <div class="card-container mt-4 mb-4">
                            <div class="flip-card" id="flipCard">
                                <div class="flip-card-inner">
                                    <div class="flip-card-front d-flex align-items-center justify-content-center text-center" id="cardFront">
                                        <div id="cardFrontContent"></div>
                                    </div>
                                    <div class="flip-card-back d-flex align-items-center justify-content-center text-center" id="cardBack">
                                        <div id="cardBackContent"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="cardControls">
                            <div id="answerButtons" class="d-none">
                                <button type="button" class="btn btn-success" onclick="markCard('right')">Right</button>
                                <button type="button" class="btn btn-danger" onclick="markCard('wrong')">Wrong</button>
                            </div>
                        </div>

                        <div id="sessionComplete" class="d-none mt-4">
                            <h3>Study Session Complete!</h3>
                            <a href="results.jsp" class="btn btn-primary">View Results</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const packCards = [
            <c:forEach var="entry" items="${packCards}" varStatus="status">
            {
                id: ${entry.key},
                question: `${fn:escapeXml(entry.value.cardQuestion)}`,
                answer: `${fn:escapeXml(entry.value.cardAnswer)}`
            }<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ];

            let currentIndex = 0;
            let flipped = false;

            function showCard(index) {
                const card = packCards[index];

                if (!flipped) {
                    document.getElementById("cardFrontContent").textContent = card.question;
                    document.getElementById("cardBackContent").textContent = "";
                } else {
                    document.getElementById("cardFrontContent").textContent = "";
                    document.getElementById("cardBackContent").textContent = card.answer;
                }

                document.getElementById("flipCard").classList.toggle("flipped", flipped);
                document.getElementById("flipCard").onclick = flipCard;

                document.getElementById("answerButtons").classList.toggle("d-none", !flipped);
            }

            function flipCard() {
                flipped = !flipped;
                showCard(currentIndex);
            }

            function markCard(result) {
                fetch("StudySessionController", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: `action=submitAnswer&result=${result}`
                }).then(() => {
                    currentIndex++;
                    flipped = false;

                    if (currentIndex < packCards.length) {
                        showCard(currentIndex);
                    } else {
                        document.getElementById("cardControls").classList.add("d-none");
                        document.getElementById("sessionComplete").classList.remove("d-none");
                    }
                });
            }

            if (packCards.length > 0) {
                showCard(0);
            } else {
                document.querySelector(".container").innerHTML = "<h2>No cards in this pack.</h2>";
            }
        </script>

        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
