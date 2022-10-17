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

  <div class="container">
    <div class="row">
      <div class="col-lg-5 mx-auto">
        <div class="card border-0 shadow rounded-3 my-5">
          <div class="card-body p-4 p-sm-5">
          <fieldset class="card-title mb-5 fw-lightfs-5 ">
            <form:form action="login" method="post">
            <legend class="text-center">Sign In</legend>
              <div class="form-floating mb-3">
                <input type="text" class="form-control shadow-none border-0 border-bottom" id="floatingInput" placeholder="Username" name="username" autocomplete="off">
                <label for="floatingInput">Username</label>
              </div>
              <div class="form-floating mb-3">
                <input type="password" class="form-control shadow-none border-0 border-bottom" id="floatingPassword" placeholder="Password" name="password">
                <label for="floatingPassword">Password</label>
              </div>
              <div class="form-check mb-3">
                <input class="form-check-input" type="checkbox" value="" id="rememberPasswordCheck">
                <label class="form-check-label" for="rememberPasswordCheck">
                  Remember password
                </label>
              </div>
              <div class="d-grid">
                <button class="btn btn-primary btn-login fw-bold" type="submit">Sign In</button>
              </div>
            </form:form>
            </fieldset>
          </div>
        </div>
      </div>
    </div>
  </div> 

<%-- 	 <div class="d-flex align-items-center justify-content-center text-white">
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
	</div> --%>
	<%@include file="./footer.jsp"%>
</body>
</html>