<form method="post" action="${pageContext.request.contextPath}/register">
    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>

    <label>Username: <input type="text" name="username" /></label><br/>
    <label>Email: <input type="text" name="email" /></label><br/>
    <label>Password: <input type="password" name="password" /></label><br/>
    <label>Details: <input type="text" name="details" /></label><br/>
    <button type="submit">Register</button>
</form>