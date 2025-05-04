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
                            <form action="StudySession" method="post">
                                <input type="hidden" name="action" value="Answer">
                                <input type="hidden" name="answer" value="right">
                                <input type="hidden" name="rightCount" value="${rightCount}">
                                <input type="hidden" name="wrongCount" value="${wrongCount}">
                                <input type="hidden" name="currentIndex" value="${currentIndex}">
                                <input type="hidden" name="packCards" value="${packCards}">
                                <input type="submit" value="Right" class="btn btn-success" id="rightButton">
                            </form>
                            <form action="StudySession" method="post">
                                <input type="hidden" name="action" value="Answer">
                                <input type="hidden" name="answer" value="wrong">
                                <input type="hidden" name="rightCount" value="${rightCount}">
                                <input type="hidden" name="wrongCount" value="${wrongCount}">
                                <input type="hidden" name="currentIndex" value="${currentIndex}">
                                <input type="hidden" name="packCards" value="${packCards}">
                                <input type="submit" value="Wrong" class="btn btn-danger" id="wrongButton">
                            </form>
                        </div>
                    </div>

                    <div id="sessionComplete" class="d-none mt-4">
                        <h3>Study Session Complete!</h3>
                        <form action="StudySession" method="post">
                            <input type="hidden" name="action" value="goToResultsPage">
                            <input type="submit" value="View Results" id="submit" class="btn btn-primary">
                        </form>
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

            let currentIndex = parseInt(${currentIndex});
            let flipped = false;

            function showCard(index) {
                const card = packCards[index];
                const cardFrontContent = document.getElementById("cardFrontContent");
                const cardBackContent = document.getElementById("cardBackContent");
                const answerButtons = document.getElementById("answerButtons");

                if (!flipped) {
                    cardFrontContent.textContent = card.question;
                    cardBackContent.textContent = "";
                    answerButtons.classList.add("d-none");
                } else {
                    cardFrontContent.textContent = "";
                    cardBackContent.textContent = card.answer;
                    answerButtons.classList.remove("d-none");
                }

                document.getElementById("flipCard").classList.toggle("flipped", flipped);
                document.getElementById("flipCard").onclick = flipCard;
            }

            function flipCard() {
                flipped = !flipped;
                showCard(currentIndex);
            }

            if (packCards.length > 0) {
                currentIndex = 0;
                showCard(currentIndex);
            } else {
                document.querySelector(".container").innerHTML = "<h2>No cards in this pack.</h2>";
            }
        </script>

        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
