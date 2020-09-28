<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<link href="<c:url value='/resource/css/webmail.css' />"
	rel="stylesheet"></link>
<style>
body {
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
	font-family: Montserrat;
	height: 80%;
}

.logo {
	width: 213px;
	height: 36px;
	background: url('http://i.imgur.com/fd8Lcso.png') no-repeat;
	margin: 30px auto;
}

/* .login-block {
	width: 320px;
	padding: 20px;
	background: #fff;
	border-radius: 5px;
	border-top: 5px solid #ff656c;
	margin: 25% auto;
	margin-left: 34% !important;
} */

/* .login-block {
    width: 320px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #ff656c;
    margin: 4% auto;
} */

.login-block {
    width: 320px;
    padding: 20px;
    background: #2196F3;
    border-radius: 5px;
    border-top: 5px solid #ff656c;
    margin: 4% auto;
}

.login-block h1 {
	text-align: center;
	color: #000;
	font-size: 18px;
	text-transform: uppercase;
	margin-top: 0;
	margin-bottom: 20px;
}

.login-block input {
	width: 100%;
	height: 42px;
	box-sizing: border-box;
	border-radius: 5px;
	border: 1px solid #ccc;
	margin-bottom: 20px;
	font-size: 14px;
	font-family: Montserrat;
	padding: 0 20px 0 50px;
	outline: none;
}

.login-block input#username {
	background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px top
		no-repeat;
	background-size: 16px 80px;
}

.login-block input#username:focus {
	background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px bottom
		no-repeat;
	background-size: 16px 80px;
}

.login-block input#password {
	background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px top
		no-repeat;
	background-size: 16px 80px;
}

.login-block input#password:focus {
	background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px bottom
		no-repeat;
	background-size: 16px 80px;
}

.login-block input:active,.login-block input:focus {
	border: 1px solid #ff656c;
}

.login-block button {
	width: 100%;
	height: 40px;
	background: #ff656c;
	box-sizing: border-box;
	border-radius: 5px;
	border: 1px solid #e15960;
	color: #fff;
	font-weight: bold;
	text-transform: uppercase;
	font-size: 14px;
	font-family: Montserrat;
	outline: none;
	cursor: pointer;
}

.login-block button:hover {
	background: #ff7b81;
}

.heading {
	
}
</style>
</head>

<body>
		<div class="page-icon animated bounceInDown">
			<center>
				<h1>Yearly Budget Management System</h1>
			</center>
		</div>

		<div class="page-icon animated bounceInDown" style="">
		<center>
			 <img
					style="background-color:gray;-moz-box-shadow: 0px 1px 3px 3px #ccc; -webkit-box-shadow: 0px 1px 3px 3px #ccc; box-shadow: 0px 1px 3px 3px #ccc; -moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius: 10px;"
					src="resource/img/logo.png"/>
					</center>
		</div>
	<%-- <div class="webmail" style="position: absolute; left: 5%; top: -25px;">
		
		<div class="heading">
			<center>
				<span>
				<img
					style="-moz-box-shadow: 0px 1px 3px 3px #ccc; -webkit-box-shadow: 0px 1px 3px 3px #ccc; box-shadow: 0px 1px 3px 3px #ccc; -moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius: 10px;"
					src="resource/img/logo.png"></span>
			</center>
		</div>

	</div> --%>

	<div class="login-block">
		<h1>Login</h1>

		<c:url var="loginUrl" value="/login" />
		<form action="<c:url value="/j_spring_security_check"></c:url>"
			method="post" class="form-horizontal">
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<center><h3 style="color:red;">The username or password you entered is incorrect</h3> </center>
					<br />
				</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">
					<p>You have been logged out successfully.</p>
				</div>
			</c:if>


			<input type="text" placeholder="Username" id="username"
				name="j_username" required /> <input type="password" value=""
				placeholder="Password" id="password" name="j_password" required /> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			<button>Submit</button>
		</form>
	</div>


</body>


</html>