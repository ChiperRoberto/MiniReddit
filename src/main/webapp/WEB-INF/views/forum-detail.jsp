<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Detalii Forum</title>

    <!-- Quill CSS + JS -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet" />
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

    <!-- Bootstrap CSS + Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
          crossorigin="anonymous"/>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"/>

    <!-- Highlight.js pentru syntax highlighting -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/monokai-sublime.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>

    <style>
        body {
            background-color: #fff5f0;
            font-family: Arial, sans-serif;
            animation: backgroundFloat 10s ease-in-out infinite alternate;
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
    </style>
    <style>
        .ql-snow .ql-editor pre {
            background-color: #f3f3f3;
            color: #333;
            padding: 10px;
            border-radius: 6px;
            font-family: "Courier New", monospace;
            overflow-x: auto;
        }
    </style>

</head>
<body>

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Detalii Forum</h1>
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
                    <!-- Conținutul postării este afișat într-un container ql-editor -->
                    <div class="ql-editor">
                        <c:out value="${post.content}" escapeXml="false" />
                    </div>

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

                    <!-- Comentarii -->
                    <div class="mt-4 ms-3">
                        <h6>Comentarii:</h6>

                        <ul class="list-group mb-2">
                            <c:forEach var="comment" items="${post.comments}">
                                <li class="list-group-item">
                                    <strong>${comment.author.username}</strong> a spus:
                                    <p>${comment.content}</p>
                                    <c:if test="${comment.author.id == sessionScope.currentUser.id or sessionScope.currentUser.role eq 'ROLE_ADMIN'}">
                                        <form action="${pageContext.request.contextPath}/posts/${post.id}/comments/${comment.id}/edit" method="get" class="d-inline">
                                            <button type="submit" class="btn btn-sm btn-outline-warning me-1">Editează</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/posts/${post.id}/comments/${comment.id}/delete" method="post" class="d-inline"
                                              onsubmit="return confirm('Ești sigur că vrei să ștergi acest comentariu?');">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <button type="submit" class="btn btn-sm btn-outline-danger">Șterge</button>
                                        </form>
                                    </c:if>
                                </li>
                            </c:forEach>
                            <c:if test="${empty post.comments}">
                                <li class="list-group-item text-muted">Niciun comentariu.</li>
                            </c:if>
                        </ul>

                        <!-- Formular comentariu nou -->
                        <c:if test="${not empty sessionScope.currentUser}">
                            <form class="d-flex align-items-start gap-2 mt-2"
                                  action="${pageContext.request.contextPath}/posts/${post.id}/comments"
                                  method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <textarea name="content" class="form-control" rows="2" placeholder="Scrie un comentariu..." required></textarea>
                                <button type="submit" class="btn btn-primary">Comentează</button>
                            </form>
                        </c:if>
                    </div>

                    <!-- Butoane Edit/Delete Post -->
                    <div class="mt-3">
                        <c:if test="${post.author.id == sessionScope.currentUser.id
                                    or sessionScope.currentUser.role eq 'ROLE_ADMIN'}">
                            <a href="${pageContext.request.contextPath}/forums/${forum.id}/posts/${post.id}/edit" class="btn btn-sm btn-outline-warning me-2">
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
            <form action="${pageContext.request.contextPath}/forums/${forum.id}/posts/create" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit" class="btn btn-success">
                    <i class="bi bi-plus-circle"></i> Adaugă o nouă postare
                </button>
            </form>
        </div>

        <hr/>
        <!-- Butoane Edit/Delete Forum -->
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

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

<!-- Inițializează Highlight.js pentru blocurile de cod -->
<script>
    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('pre.ql-syntax').forEach((block) => {
            hljs.highlightElement(block);
        });
    });
</script>

</body>
</html>
