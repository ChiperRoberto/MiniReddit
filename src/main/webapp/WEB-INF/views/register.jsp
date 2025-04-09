<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8" />
    <title>Înregistrare Forum</title>
</head>
<body>
<div class="container">
    <div class="bubble-card" style="max-width: 500px; margin: 2rem auto;">
        <h1 class="bubble-title mb-4 text-center">Creare cont nou</h1>

        <!-- Mesaj de eroare (dacă vine de la controller) -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <!-- Formular de înregistrare -->
        <!-- user este un obiect 'new User()' injectat în model la GET /register -->
        <form action="<c:url value='/register' />" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="mb-3">
                <label class="form-label" for="username">Utilizator</label>
                <input type="text" class="form-control" id="username" name="username"
                       value="${user.username}" required />
            </div>
            <div class="mb-3">
                <label class="form-label" for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${user.email}" required />
            </div>
            <div class="mb-3">
                <label class="form-label" for="password">Parolă</label>
                <input type="password" class="form-control" id="password" name="password" required />
            </div>
            <!-- Dacă vrei confirmare parolă, adaugă încă un câmp aici -->

            <div class="mb-3">
                <label class="form-label" for="details">Detalii</label>
                <input type="text" class="form-control" id="details" name="details"
                       value="${user.details}" />
            </div>

            <button type="submit" class="btn btn-success w-100">Înregistrează-te</button>
        </form>

        <hr/>
        <div class="text-center">
            <small>Ai deja cont?
                <a href="<c:url value='/login' />">Loghează-te aici</a>
            </small>
        </div>
    </div>
</div>
</body>
</html>