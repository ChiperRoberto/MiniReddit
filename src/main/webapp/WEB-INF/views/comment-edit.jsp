<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<html>
<head>
  <title>Edit Comment</title>
</head>
<body>
<div class="container mt-5">
  <div class="card shadow-sm">
    <div class="card-header bg-primary text-white">
      <h4 class="mb-0">Edit Comment</h4>
    </div>
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/posts/${postId}/comments/${comment.id}/edit" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="mb-3">
          <label for="content" class="form-label">Content</label>
          <textarea id="content" name="content" class="form-control" rows="4" required>${comment.content}</textarea>
        </div>
        <div class="d-flex justify-content-end gap-2">
          <button type="submit" class="btn btn-success">Save</button>
          <a href="${pageContext.request.contextPath}/forums/${comment.post.forum.id}" class="btn btn-outline-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>