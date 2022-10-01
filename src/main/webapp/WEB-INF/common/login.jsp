<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@include file="./include.jsp"%>
<style type="text/css">
body, html {
	height: 100%;
}
</style>
</head>
<body>
	<div class="d-flex align-items-center justify-content-center text-white">
		<fieldset style="border: 1px solid silver; border-radius: 5px; padding: 25px; margin-top:15%;">
			<form:form action="login" method="post">
				<div class="input-group mb-3">
					<span class="input-group-text" id="basic-addon1"> <i class="fas fa-user"></i></span> 
					<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" name="username" value="user">
				</div>

				<div class="input-group mb-3">
					<span class="input-group-text" id="basic-addon1"> <i class="fas fa-key"></i></span> 
					<input type="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="basic-addon1" name="password" value="user">
				</div>
				<div class="input-group mb-3">
					<button class="btn btn-info" style="width: 100%;">Sign In</button>
				</div>
			</form:form>
		</fieldset>
	</div>
	<%@include file="./footer.jsp"%>
</body>
</html>