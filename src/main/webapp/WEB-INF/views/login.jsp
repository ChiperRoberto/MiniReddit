<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8" />
    <title>Autentificare</title>
</head>
<body>
<div class="container">
    <!-- card cu stilul bubble-card -->
    <div class="bubble-card" style="max-width: 450px; margin: 2rem auto;">
        <h1 class="bubble-title mb-4 text-center">Login Forum</h1>

        <!-- Mesaj de eroare / info, dacă e setat în controller -->
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">Te-ai delogat cu succes!</div>
        </c:if>
        <c:if test="${loginError}">
            <div class="alert alert-danger">Username sau parolă incorecte!</div>
        </c:if>
        <c:if test="${param.registerSuccess != null}">
            <div class="alert alert-info">Cont creat cu succes. Te poți loga!</div>
        </c:if>

        <!-- Formularul de login -->
        <!-- method=post + action=\"/login\" => fluxul Spring Security -->
        <form method="post" action="<c:url value='/login' />">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="mb-3">
                <label class="form-label" for="username">Utilizator</label>
                <input type="text" id="username" name="username" class="form-control" required />
            </div>
            <div class="mb-3">
                <label class="form-label" for="password">Parolă</label>
                <input type="password" id="password" name="password" class="form-control" required />
            </div>

            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>

        <hr/>
        <div class="text-center">
            <small>Nu ai cont? <a href="<c:url value='/register' />">Înregistrează-te aici</a></small>
        </div>
    </div>
</div>

<!-- Imagine sau decor la final, la fel ca în celelalte pagini -->
</body>
</html>