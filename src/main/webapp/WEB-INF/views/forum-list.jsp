<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Lista Forumuri</title>
    <!-- Quill (dacă e nevoie) -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

    <!-- Bootstrap CSS -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
            integrity="sha384-CTd3/yCOZhVVkTQ+nCyk3z9Hra6Hc6wWY6Xfq6wA91gz7zUmD2cBfyqegH6ckEdT"
            crossorigin="anonymous"
    />
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
    />
    <style>
        body {
            background-color: #eaf6ff;
        }
        .bubble-card {
            background-color: #ffffff;
            border-radius: 2rem;
            padding: 2rem;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .bubble-title {
            font-family: "Fredoka", sans-serif;
        }
    </style>
</head>
<body>

<div class="bubbles-background"></div>
<style>
    .bubbles-background {
        position: fixed;
        top: 0; left: 0;
        width: 100%; height: 100%;
        z-index: -1;
        background: radial-gradient(circle at 20% 30%, rgba(173,216,230,0.3) 10px, transparent 11px),
        radial-gradient(circle at 80% 70%, rgba(173,216,230,0.2) 8px,  transparent 9px),
        radial-gradient(circle at 50% 50%, rgba(173,216,230,0.25) 12px, transparent 13px);
        background-size: 100% 100%;
        animation: bubbleMove 10s infinite linear;
    }

    @keyframes bubbleMove {
        0%   { background-position: 0% 0%; }
        100% { background-position: 0% 100%; }
    }
</style>

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Lista Forumuri</h1>

        <!-- Formular de căutare -->
        <form class="row g-3 mb-3" action="${pageContext.request.contextPath}/forums" method="get">
            <div class="col-auto">
                <input type="text" class="form-control" name="keyword" placeholder="Caută forum..." />
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Găsește forum
                </button>
            </div>
        </form>

        <!-- Link către crearea unui forum -->
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/forums/create" class="btn btn-success" data-bs-toggle="tooltip" title="Creează un nou forum!">
                <i class="bi bi-plus-circle"></i> Creează Forum
            </a>
        </div>

        <ul class="list-group">
            <c:forEach var="forum" items="${forums}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <span>
                        <a href="${pageContext.request.contextPath}/forums/${forum.id}" class="text-decoration-none">
                                ${forum.name}
                        </a>
                    </span>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<img src="${pageContext.request.contextPath}/images/Bubbles_floating.svg" class="floating-bubble" />

<!-- Bootstrap JS -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Ho1s9FlcwIyHfJO23mO4IECEq29gcNeLVU9wtBfKcvQf0yIfzBi6vM2kl1imU81u"
        crossorigin="anonymous"
></script>
<script>
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle=\"tooltip\"]'));
    tooltipTriggerList.forEach(function (tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });
</script>
</body>
</html>