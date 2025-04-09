<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Detalii Forum</title>
    <p>session user = ${sessionScope.currentUser.id}, role=${sessionScope.currentUser.role}</p>

    <!-- Quill CSS + JS -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet" />
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

    <!-- Bootstrap CSS + Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-CTd3/yCOZhVVkTQ+nCyk3z9Hra6Hc6wWY6Xfq6wA91gz7zUmD2cBfyqegH6ckEdT"
          crossorigin="anonymous"/>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"/>

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

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title">Detalii Forum</h1>
        <h5 class="text-secondary">ID: ${forum.id}</h5>
        <p class="fs-4"><strong>Nume forum:</strong> ${forum.name}</p>

        <hr/>

        <h3>Postări în acest forum:</h3>

        <!-- Form de filtrare postări -->
        <form class="d-flex mb-3"
              action="${pageContext.request.contextPath}/forums/${forum.id}"
              method="get">
            <input type="text" name="keyword"
                   class="form-control me-2" placeholder="Caută postări" />
            <button type="submit" class="btn btn-primary">Filtrează</button>
        </form>

        <ul class="list-group">
            <c:forEach var="post" items="${posts}">
                <li class="list-group-item">
                    <strong>${post.title}</strong><br/>
                    <c:out value="${post.content}" escapeXml="false" />

                    <!-- Reacții -->
                    <div class="mt-2">
                        <strong>Reacții:</strong>

                        <c:set var="likeCount" value="0" />
                        <c:set var="loveCount" value="0" />
                        <c:set var="sadCount" value="0" />

                        <c:forEach var="reaction" items="${post.reactions}">
                            <c:choose>
                                <c:when test="${reaction.type == 'LIKE'}">
                                    <c:set var="likeCount" value="${likeCount + 1}" />
                                </c:when>
                                <c:when test="${reaction.type == 'LOVE'}">
                                    <c:set var="loveCount" value="${loveCount + 1}" />
                                </c:when>
                                <c:when test="${reaction.type == 'SAD'}">
                                    <c:set var="sadCount" value="${sadCount + 1}" />
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <span class="me-2">👍 ${likeCount}</span>
                        <span class="me-2">❤️ ${loveCount}</span>
                        <span>😢 ${sadCount}</span>
                    </div>

                    <!-- Butoane de reacție -->
                    <div class="mt-2">
                        <form action="${pageContext.request.contextPath}/posts/${post.id}/react" method="post" class="d-inline">
                            <input type="hidden" name="type" value="LIKE" />
                            <button type="submit" class="btn btn-sm btn-outline-primary me-1">👍</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/posts/${post.id}/react" method="post" class="d-inline">
                            <input type="hidden" name="type" value="LOVE" />
                            <button type="submit" class="btn btn-sm btn-outline-danger me-1">❤️</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/posts/${post.id}/react" method="post" class="d-inline">
                            <input type="hidden" name="type" value="SAD" />
                            <button type="submit" class="btn btn-sm btn-outline-secondary">😢</button>
                        </form>
                    </div>

                    <!-- Afișăm butoane Edit/Delete doar dacă autorul postării == user logat SAU user logat = admin -->
                    <div class="mt-2">
                        <c:if test="${post.author.id == sessionScope.currentUser.id
                                    or sessionScope.currentUser.role eq 'ROLE_ADMIN'}">
                            <a href="${pageContext.request.contextPath}/forums/${forum.id}/posts/${post.id}/edit"
                               class="btn btn-sm btn-outline-warning me-2">
                                <i class="bi bi-pencil-square"></i> Edit Post
                            </a>
                            <a href="${pageContext.request.contextPath}/forums/${forum.id}/posts/${post.id}/delete"
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Ești sigur că vrei să ștergi postarea?');">
                                <i class="bi bi-trash"></i> Șterge
                            </a>
                        </c:if>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/forums/${forum.id}/posts/create"
               class="btn btn-success">
                <i class="bi bi-plus-circle"></i> Adaugă o nouă postare
            </a>
        </div>

        <hr/>
        <!-- Afișăm butoanele Edit / Delete forum doar dacă forum.author == user logat sau e Admin -->
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/forums"
               class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Înapoi la lista forumuri
            </a>
            <c:if test="${forum.author.id == sessionScope.currentUser.id
                        or sessionScope.currentUser.role eq 'ROLE_ADMIN'}">
                <a href="${pageContext.request.contextPath}/forums/${forum.id}/edit"
                   class="btn btn-outline-info">
                    <i class="bi bi-pencil"></i> Edit Forum
                </a>
                <a href="${pageContext.request.contextPath}/forums/${forum.id}/delete"
                   class="btn btn-outline-danger"
                   onclick="return confirm('Ești sigur că vrei să ștergi acest forum?')">
                    <i class="bi bi-x-circle"></i> Șterge forum
                </a>
            </c:if>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Ho1s9FlcwIyHfJO23mO4IECEq29gcNeLVU9wtBfKcvQf0yIfzBi6vM2kl1imU81u"
        crossorigin="anonymous"></script>
</body>
</html>