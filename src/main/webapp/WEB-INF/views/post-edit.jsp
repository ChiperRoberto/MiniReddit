<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Editare Postare</title>

    <!-- Quill CSS + JS -->
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


<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Editare Postare</h1>

        <!-- Notă: aici încă folosim un <textarea> simplu.
             Dacă vrei Quill și la editare, e ceva mai complex (ar trebui să initializezi Quill cu content-ul existent).
        -->
        <form action="${pageContext.request.contextPath}/forums/${forum.id}/posts/${post.id}/edit"
              method="post">

            <div class="mb-3">
                <label for="title" class="form-label">Titlu:</label>
                <input type="text" id="title" name="title" value="${post.title}" class="form-control" />
            </div>

            <!-- Înlocuiește textarea-ul clasic -->
            <div class="mb-3">
                <label for="editor" class="form-label">Conținut:</label>
                <div id="editor">${post.content}</div>
                <textarea name="content" id="content" hidden></textarea>
            </div>

            <script>
                var quill = new Quill('#editor', {
                    theme: 'snow',
                    modules: {
                        toolbar: [
                            [{ 'header': [1, 2, false] }],
                            ['bold', 'italic', 'underline'],
                            ['image', 'code-block']
                        ]
                    }
                });

                document.querySelector('form').addEventListener('submit', function () {
                    var html = quill.root.innerHTML;
                    document.getElementById('content').value = html;
                });
            </script>

            <button type="submit" class="btn btn-primary">Salvează modificările</button>
        </form>

        <hr/>
        <a href="${pageContext.request.contextPath}/forums/${forum.id}" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Înapoi la Forum
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