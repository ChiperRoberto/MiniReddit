<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Font + Bootstrap CSS -->

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"/>

    <style>
        body {
            background-color: #eaf6ff;
            font-family: Arial, sans-serif;
            animation: backgroundFloat 10s ease-in-out infinite alternate;
        }
        .navbar {
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            position: sticky; top: 0; z-index: 1000;
            background-color: lightblue ;
            background-size: cover;
            background-position: center;
            border-radius: 0 0 2rem 2rem;
        }

        .bubble-card {
            background-color: #ffffff;
            border-radius: 2rem;
            padding: 2rem;
            box-shadow: 0 0 20px rgba(0, 123, 255, 0.1);
            animation: floatUp 1s ease-in-out;
        }
        .bubble-title {
            font-weight: bold;
            animation: popIn 0.7s ease;
        }
        .list-group-item a:hover {
            transform: scale(1.05);
            transition: transform 0.3s ease;
            color: #0d6efd;
        }

        @keyframes floatUp {
            0%   { transform: translateY(20px); opacity: 0; }
            100% { transform: translateY(0); opacity: 1; }
        }

        @keyframes popIn {
            0%   { transform: scale(0.8); opacity: 0; }
            100% { transform: scale(1); opacity: 1; }
        }

        @keyframes backgroundFloat {
            from { background-color: #eaf6ff; }
            to   { background-color: #d4f1ff; }
        }

        .btn {
            transition: transform 0.2s ease;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        .floating-bubble {
            animation: float 8s infinite ease-in-out;
            opacity: 0.6;
        }
        @keyframes float {
            0% { transform: translateY(0) }
            50% { transform: translateY(-20px) }
            100% { transform: translateY(0) }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
    <div class="container">
        <!-- Schimbă textul Bubbles cu Forumuri, dacă vrei -->
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/forums">
            <span style="font-weight: bold; font-size: 1.5rem;">Forumuri</span>
        </a>
    </div>
</nav>
</body>
</html>