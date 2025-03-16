<%-- 
    Document   : index
    Created on : Feb 19, 2025, 3:11:23 PM
    Author     : am528423
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>User Login/Registration</title>
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
                    <div class="col-6">
                        <h1>Expense Trackers</h1>
                        <h3>${message}</h3>
                        <form action="Public" method="post">
                            <input type="hidden" name="action" value="login">
                            <div class="columnFlex">
                                <label for="email">Email </label>
                                <input placeholder="example@domain.com" class="minWidth200" type="text" name="email" value="<c:out value='${user.email}'/>">
                            </div>
                            <div class="columnFlex">
                                <label for="password">Password </label>
                                <input id="password" class="minWidth200" class="form-control" type="password" name="password" value="<c:out value='${user.password}'/>">
                                <i class="bi bi-eye-slash" id="togglePassword"></i>
                            </div>
                            <input type="submit" value="login">
                        </form>

                        <p>Registered users can log in from here. <br><br>
                           To register for the first time, please click Go to Registration Form button.</p>

                        <form action="Public" method="get">
                            <input type="hidden" name="action" value="goToRegistrationPage">
                            <button class="btn btn-primary" type="submit">Register<svg xmlns="http://www.w3.org/2000/svg" width="25" height="20" fill="currentColor" class="bi bi-arrow-right ms-1" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
                                </svg>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="styles/js-bootstrap/bootstrap.js"></script>
        <script>
            const togglePassword = document
                    .querySelector('#togglePassword');
            const password = document.querySelector('#password');
            togglePassword.addEventListener('click', () => {
                // Toggle the type attribute using
                // getAttribure() method
                const type = password
                        .getAttribute('type') === 'password' ?
                        'text' : 'password';
                password.setAttribute('type', type);
                // Toggle the eye and bi-eye icon
                this.classList.toggle('bi-eye');
            });
        </script>
    </body>
</html>
