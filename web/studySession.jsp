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
        <meta charset="UTF-8">
        <title>Study Session</title>
        <link rel="stylesheet" href="styles/css-bootstrap/bootstrap.css">
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body class="bg-primary">
        <div class="col">
            <nav>    
                <%@include file="nav.jsp" %>
            </nav>
        </div>
        <div class="container bg-light border rounded-3 shadow-lg py-5 my-5 text-center">
            <h1 class="mb-4">Study Session</h1>
            <h4 id="timer">Time: 0s</h4>

            <div class="card-container mt-4 mb-4">
                <!-- Wrapper to hold the flip functionality -->
                <div class="flip-card" id="flipCard" onclick="flipCard()">
                    <div class="flip-card-inner">
                        <!-- Front of the card -->
                        <div class="flip-card-front d-flex align-items-center justify-content-center text-center" id="cardFront">
                            <div id="cardFrontContent"></div>
                        </div>

                        <!-- Back of the card -->
                        <div class="flip-card-back d-flex align-items-center justify-content-center text-center" id="cardBack">
                            <div id="cardBackContent"></div>
                        </div>
                    </div>
                </div>

            </div>


            <div id="cardControls" class="d-none">

                <div id="answerButtons" class="d-none">
                    <form method="post" action="StudySessionController" class="d-inline">
                        <input type="hidden" name="action" value="markCard">
                        <input type="hidden" id="markResult" name="result">
                        <button type="button" class="btn btn-success" onclick="markCard('right')">Right</button>
                        <button type="button" class="btn btn-danger" onclick="markCard('wrong')">Wrong</button>
                    </form>
                </div>
            </div>

            <div id="sessionComplete" class="d-none mt-4">
                <h3>Study Session Complete!</h3>
                <a href="dashboard.jsp" class="btn btn-primary">Return to Dashboard</a>
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
            let startTime = Date.now();

            const timerEl = document.getElementById("timer");
            const cardFront = document.getElementById("cardFront");
            const cardBack = document.getElementById("cardBack");
            const answerButtons = document.getElementById("answerButtons");
            const cardControls = document.getElementById("cardControls");
            const sessionComplete = document.getElementById("sessionComplete");

            function updateTimer() {
                const seconds = Math.floor((Date.now() - startTime) / 1000);
                timerEl.textContent = `Time: ${seconds}s`;
            }
            setInterval(updateTimer, 1000);

            function showCard(index) {
                const card = packCards[index];

                // Immediately update the content before flip
                if (!flipped) {
                    // Set the question on the front
                    document.getElementById("cardFrontContent").textContent = card.question;
                    // Clear the back content
                    document.getElementById("cardBackContent").textContent = "";
                } else {
                    // Clear the front content
                    document.getElementById("cardFrontContent").textContent = "";
                    // Set the answer on the back
                    document.getElementById("cardBackContent").textContent = card.answer;
                }

                // Show card controls (Right/Wrong buttons)
                cardControls.classList.remove("d-none");

                // Show or hide answer buttons based on whether the card is flipped
                if (flipped) {
                    answerButtons.classList.remove("d-none");
                } else {
                    answerButtons.classList.add("d-none");
                }

                // Ensure that the card doesn't disappear after the animation ends
                const flipCard = document.getElementById("flipCard");
                flipCard.classList.remove("flipped"); // Remove the flip effect initially
            }

            function flipCard() {
                flipped = !flipped;

                // Set the content before starting the animation
                showCard(currentIndex);

                // Apply flip animation after content is updated
                setTimeout(function () {
                    document.getElementById("flipCard").classList.toggle("flipped");
                }, 50); // Ensure content updates before animation starts
            }

            function markCard(result) {
                // Mark the card as right or wrong and fetch the next card
                fetch("StudySessionController", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: `action=markCard&result=${result}&cardID=${packCards[currentIndex].id}`
                }).then(() => {
                    currentIndex++;
                    flipped = false; // Reset the flipped state when moving to the next card

                    // If there are more cards, update the content and show the next card
                    if (currentIndex < packCards.length) {
                        showCard(currentIndex);
                    } else {
                        // If no more cards, show session complete message
                        cardControls.classList.add("d-none");
                        sessionComplete.classList.remove("d-none");
                    }
                });
            }

            // Initial setup: Show the first card if available
            if (packCards.length > 0) {
                showCard(0);
            } else {
                document.querySelector(".container").innerHTML = "<h2>No cards in this pack.</h2>";
            }
        </script>

        <script src="styles/js-bootstrap/bootstrap.js"></script>
    </body>
</html>
