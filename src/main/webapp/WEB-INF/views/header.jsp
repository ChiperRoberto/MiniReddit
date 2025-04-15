<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <!-- Font + Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"/>

    <style>
        .logo-img {
            height: 120px; /* Mărește logo-ul */
            max-height: 150px;
            object-fit: contain;
        }
        .logo-img:hover {
            transform: scale(1.05);
        }
        body {
            background-color: #fff5f0;
            <%--background-image: url('${pageContext.request.contextPath}/images/background.png');--%>
            font-family: Arial, sans-serif;
            animation: backgroundFloat 10s ease-in-out infinite alternate;
        }

        .navbar {
            background-color: #ffffff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            border-radius: 0 0 2rem 2rem;
            min-height: 60px;
        }

        .bubble-card {
            background-color: #ffffff;
            border-radius: 2rem;
            padding: 2rem;
            box-shadow: 0 0 20px rgba(255, 69, 0, 0.08); /* roșu-portocaliu subtil */
        }

        .bubble-title {
            font-weight: bold;
            animation: popIn 0.7s ease;
            color: #ff4500;
        }

        .btn-primary {
            background-color: #ff4500;
            border-color: #ff4500;
            transition: transform 0.2s ease;
        }

        .btn-primary:hover {
            background-color: #ff5722;
            border-color: #ff5722;
            transform: scale(1.05);
        }

        .nav-link {
            color: #212529 !important;
        }
        /*body {*/
        /*    background-color: #333333;*/
        /*    font-family: Arial, sans-serif;*/
        /*    animation: backgroundFloat 10s ease-in-out infinite alternate;*/
        /*}*/
        /*.navbar {*/
        /*    box-shadow: 0 2px 8px rgba(0,0,0,0.1);*/
        /*    position: sticky; top: 0; z-index: 1000;*/
        /*    background-color: #333333;*/
        /*    background-size: cover;*/
        /*    background-position: center;*/
        /*    border-radius: 0 0 2rem 2rem;*/
        /*    min-height: 56px; !* controlează cât de mică poate fi navbar-ul *!*/
        /*}*/

        /*.bubble-card {*/
        /*    background-color: #ffffff;*/
        /*    border-radius: 2rem;*/
        /*    padding: 2rem;*/
        /*    box-shadow: 0 0 20px rgba(0, 123, 255, 0.1);*/
        /*    animation: floatUp 1s ease-in-out;*/
        /*}*/
        /*.bubble-title {*/
        /*    font-weight: bold;*/
        /*    animation: popIn 0.7s ease;*/
        /*}*/
        /*.list-group-item a:hover {*/
        /*    transform: scale(1.05);*/
        /*    transition: transform 0.3s ease;*/
        /*    color: #0d6efd;*/
        /*}*/

        /*@keyframes floatUp {*/
        /*    0%   { transform: translateY(20px); opacity: 0; }*/
        /*    100% { transform: translateY(0); opacity: 1; }*/
        /*}*/

        /*@keyframes popIn {*/
        /*    0%   { transform: scale(0.8); opacity: 0; }*/
        /*    100% { transform: scale(1); opacity: 1; }*/
        /*}*/

        /*@keyframes backgroundFloat {*/
        /*    from { background-color: #eaf6ff; }*/
        /*    to   { background-color: #d4f1ff; }*/
        /*}*/

        /*.btn {*/
        /*    transition: transform 0.2s ease;*/
        /*}*/

        /*.btn:hover {*/
        /*    transform: scale(1.05);*/
        /*}*/

        /*.floating-bubble {*/
        /*    animation: float 8s infinite ease-in-out;*/
        /*    opacity: 0.6;*/
        /*}*/
        /*@keyframes float {*/
        /*    0% { transform: translateY(0) }*/
        /*    50% { transform: translateY(-20px) }*/
        /*    100% { transform: translateY(0) }*/
        /*}*/
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold fs-4" href="${pageContext.request.contextPath}/forums">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="MiniReddit" class="logo-img" height="145" >
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto align-items-center">
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">
                            <i class="bi bi-person-circle"></i> Profil
                        </a>
                    </li>
                    <li class="nav-item">
                        <form action="${pageContext.request.contextPath}/logout" method="post" class="d-inline">
                            <button type="submit" class="btn btn-link nav-link" style="padding: 0; text-decoration: none;">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </button>
                        </form>
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">
                            <i class="bi bi-box-arrow-in-right"></i> Autentificare
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
