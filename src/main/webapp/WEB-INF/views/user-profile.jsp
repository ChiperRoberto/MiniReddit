<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="header.jsp" %>
<html>
<head>
  <title>Profil Utilizator</title>

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
<sec:authorize access="isAuthenticated()">
  <div class="container mt-5">
    <div class="bubble-card">
      <h2 class="bubble-title mb-4">Profilul utilizatorului</h2>

      <p><strong>Username:</strong> ${user.username}</p>
      <p><strong>Email:</strong> ${user.email}</p>
      <p><strong>Rol:</strong> ${user.role}</p>

      <form method="post" action="/user/update-description" class="mb-4">
        <div class="mb-3">
          <label for="description" class="form-label">Descriere:</label>
          <textarea class="form-control" id="description" name="description" rows="4">${user.details}</textarea>
        </div>
        <button type="submit" class="btn btn-primary"><i class="bi bi-save"></i> Salvează descrierea</button>
      </form>

      <h4 class="mt-4">Forumurile tale</h4>
      <ul class="list-group">
        <c:forEach items="${forums}" var="forum">
          <li class="list-group-item">
            <a href="/forums/${forum.id}" class="text-decoration-none">${forum.name}</a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
</sec:authorize>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Ho1s9FlcwIyHfJO23mO4IECEq29gcNeLVU9wtBfKcvQf0yIfzBi6vM2kl1imU81u"
        crossorigin="anonymous"
></script>
</body>
</html>