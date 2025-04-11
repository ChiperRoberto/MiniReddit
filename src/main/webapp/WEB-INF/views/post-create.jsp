<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Creare Postare</title>

    <!-- Quill CSS/JS -->
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
        #editor {
            height: 200px;
            background: #fff;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="bubble-card">
        <h1 class="bubble-title mb-4">Creează Postare</h1>

        <form id="postForm"
              action="${pageContext.request.contextPath}/forums/${post.forum.id}/posts/create"
              method="post">

            <div class="mb-3">
                <label for="title" class="form-label">Titlu Postare:</label>
                <input type="text" id="title" name="title" class="form-control" value="${post.title}" />
                <c:if test="${bindingResult.hasFieldErrors('title')}">
                    <div class="text-danger">${bindingResult.getFieldError('title').defaultMessage}</div>
                </c:if>
            </div>

            <div class="mb-3">
                <label for="editor" class="form-label">Conținut:</label>
                <div id="editor">${post.content}</div>
                <textarea name="content" id="content" hidden></textarea>
                <c:if test="${bindingResult.hasFieldErrors('content')}">
                    <div class="text-danger">${bindingResult.getFieldError('content').defaultMessage}</div>
                </c:if>
            </div>

            <button type="submit" class="btn btn-primary">Salvează Postarea</button>
        </form>

        <hr/>
        <a href="${pageContext.request.contextPath}/forums/${post.forum.id}" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Înapoi la Forum
        </a>
    </div>
</div>

<!-- Script Quill - custom image handler -->
<script>
    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: {
                container: [
                    [{ 'header': [1, 2, false] }],
                    ['bold', 'italic', 'underline'],
                    ['image', 'code-block']
                ],
                handlers: {
                    'image': imageHandler
                }
            }
        }
    });

    async function imageHandler() {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', 'image/*');
        input.click();

        input.onchange = async () => {
            const file = input.files[0];
            if (!file) return;

            const formData = new FormData();
            formData.append('file', file);

            try {
                const response = await fetch('${pageContext.request.contextPath}/forums/' + ${post.forum.id} + '/upload', {
                    method: 'POST',
                    body: formData
                });
                if (!response.ok) {
                    alert('Eroare la upload imagine! Status ' + response.status);
                    return;
                }
                const data = await response.json();
                const range = quill.getSelection();
                quill.insertEmbed(range.index, 'image', data.url);
            } catch (error) {
                console.error('Eroare la upload imagine', error);
            }
        };
    }

    document.getElementById('postForm').addEventListener('submit', function() {
        var htmlContent = quill.root.innerHTML;
        document.getElementById('content').value = htmlContent;
    });
</script>

<!-- Bootstrap JS -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Ho1s9FlcwIyHfJO23mO4IECEq29gcNeLVU9wtBfKcvQf0yIfzBi6vM2kl1imU81u"
        crossorigin="anonymous"
></script>


</body>
</html>