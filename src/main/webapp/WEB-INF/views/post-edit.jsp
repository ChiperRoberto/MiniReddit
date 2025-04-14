<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Editare Postare</title>

    <!-- Quill CSS -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

    <!-- Bootstrap CSS + Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"/>

    <style>
        body {
            background-color: #eaf6ff;
        }
        .bubble-card {
            background-color: #fff;
            border-radius: 2rem;
            padding: 2rem;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .bubble-title {
            font-family: "Fredoka", sans-serif;
        }
        #editor {
            background-color: white;
            min-height: 200px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Editare Postare</h1>

        <form id="postForm"
              action="${pageContext.request.contextPath}/forums/${forum.id}/posts/${post.id}/edit"
              method="post">

            <!-- Titlu -->
            <div class="mb-3">
                <label for="title" class="form-label">Titlu:</label>
                <input type="text" id="title" name="title"
                       value="${post.title}" required minlength="5" maxlength="150"
                       class="form-control" />
            </div>

            <!-- Conținut -->
            <div class="mb-3">
                <label for="editor" class="form-label">Conținut:</label>
                <div id="editor"><c:out value="${post.content}" escapeXml="false"/></div>
                <textarea name="content" id="content" hidden></textarea>
                <noscript>
                    <div class="alert alert-warning mt-2">
                        JavaScript este dezactivat. Editorul nu va funcționa corect.
                    </div>
                </noscript>
            </div>

            <!-- Buton submit -->
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-save"></i> Salvează modificările
            </button>
        </form>

        <hr/>
        <a href="${pageContext.request.contextPath}/forums/${forum.id}" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Înapoi la Forum
        </a>
    </div>
</div>

<!-- Quill JS -->
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

<!-- Script care transferă conținutul Quill în textarea -->
<script>
    const quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'header': [1, 2, false] }],
                ['bold', 'italic', 'underline'],
                ['link', 'image', 'code-block']
            ]
        }
    });

    document.getElementById('postForm').addEventListener('submit', function () {
        const html = quill.root.innerHTML.trim();
        document.getElementById('content').value = html;

        // Validare rapidă (evită trimiteri cu conținut gol)
        if (html === '' || html === '<p><br></p>') {
            alert('Conținutul postării nu poate fi gol!');
            event.preventDefault();
        }
    });
</script>

</body>
</html>
