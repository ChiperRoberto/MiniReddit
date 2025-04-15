<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Creare Forum</title>
    <!-- Quill (dacă e necesar) -->
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
        /*body {*/
        /*    background-color: #eaf6ff;*/
        /*}*/
        /*.bubble-card {*/
        /*    background-color: #ffffff;*/
        /*    border-radius: 2rem;*/
        /*    padding: 2rem;*/
        /*    box-shadow: 0 0 10px rgba(0,0,0,0.1);*/
        /*}*/
        /*.bubble-title {*/
        /*    font-family: "Fredoka", sans-serif;*/
        /*}*/
    </style>
</head>
<body>

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Creează un nou Forum</h1>

        <form action="${pageContext.request.contextPath}/forums/create" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Nume Forum:</label>
                <input type="text" id="name" name="name" class="form-control" value="${forum.name}" />
                <c:if test="${bindingResult.hasFieldErrors('name')}">
                    <div class="text-danger">${bindingResult.getFieldError('name').defaultMessage}</div>
                </c:if>
            </div>

            <button type="submit" class="btn btn-primary">Salvează Forum</button>
        </form>

        <hr/>
        <a href="${pageContext.request.contextPath}/forums" class="btn btn-outline-secondary mt-2">
            <i class="bi bi-arrow-left"></i> Înapoi la lista forumuri
        </a>
    </div>
</div>

<!-- Bootstrap JS -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Ho1s9FlcwIyHfJO23mO4IECEq29gcNeLVU9wtBfKcvQf0yIfzBi6vM2kl1imU81u"
        crossorigin="anonymous"
></script>

</body>
</html>