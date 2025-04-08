<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Creare cont nou</h2>
<c:if test="${not empty errorMessage}">
    <div style="color:red;">${errorMessage}</div>
</c:if>

<form action="${pageContext.request.contextPath}/register" method="post">
    <p>
        <label>Username:</label>
        <input type="text" name="username" required />
    </p>
    <p>
        <label>Email:</label>
        <input type="email" name="email" required />
    </p>
    <p>
        <label>Parolă:</label>
        <input type="password" name="password" required />
    </p>
    <p>
        <label>Detalii:</label>
        <input type="text" name="details" />
    </p>
    <button type="submit">Register</button>
</form>
</body>
</html>