<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.com.benlinus92.server.LoginServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JSP Chat</title>
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="shortcut icon" href="favicon.png" type="image/png">
	<style type="text/css">
	body {
		background: url(background.jpg) no-repeat center center fixed; 
		-moz-background-size: 100%; 
		-webkit-background-size: 100%;
		-o-background-size: 100%; 
		background-size: cover;
		padding-top: 12%;
		font-family: 'Roboto';
		font-weight: 300;
	}
	.container {
		min-width: 300px; 
		overflow: visible;
		max-width: 28%; 
		border: 2px solid #414143;
	}
	.alert-danger {
		margin-top: 20px;
		width: 400px;
	}
	.btn-primary {
		background-color: #414143;
		border-color: #c6c9c6;
		margin-bottom: 5px;
		
	}
	.goin {
		margin-right: 10px;
		padding-right: 20px;
		padding-left: 20px;
	}
	</style>
</head>
<body>
<div class="container">
<div class="row text-center" style="background-color: #414143; color: white">
	<h3><strong>Авторизация</strong></h3>
</div>
<div class="row" style="background-color: #fff;  font-size: 14px; font-weight: normal; padding: 10px">
	<form action="login" method="post">
		<div class="form-group" style="padding-top: 10px">
	      <input type="text" placeholder="Логин" name="login" class="form-control">
	    </div>
	    <div class="form-group">
			<input type="password" placeholder="Пароль" name="password" class="form-control">
		</div>
		<br>
		<div class="text-center">
			<button name="send" value="log" type="submit" class="btn btn-primary goin">Войти</button>
			<button name="send" value="register" type="submit" class="btn btn-primary reg">Регистрация за один клик</button>
		</div>
	</form>
			<div class="text-center">
			<div class="alert alert-warning alert-dismissable">
	  			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	  			<strong>Внимание!</strong> Данные для тестовой авторизации без регистрации
	  			Логин: user1; Пароль: testing
			</div>
		</div>
</div>
</div>
<c:if test="${not empty requestScope.error}">
	<div class="alert alert-danger alert-dismissable center-block">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		<strong>Ошибка!</strong>
		<c:out value="${requestScope.error}"></c:out>
	</div>
</c:if>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>