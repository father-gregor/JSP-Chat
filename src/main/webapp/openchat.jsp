<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<c:set value="${sessionScope.user}" var="user"/>
	<title>JSP Chat</title>
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato">
	<link rel="shortcut icon" href="favicon.png" type="image/png">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/custom.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="chat"><img class="logo navbar-left" alt="logo" src="logo.png">
      <p class="navbar-left" style="margin-top: 10px">JSPChat</p></a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li class="active"><a href="chat">Общий чат</a></li>
        <!--<li><a class="menus" href="#">Чат-комнаты</a></li>-->
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout" class="exit"><span class="glyphicon glyphicon-log-in"></span> Выход</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="container">
	<div class="row">
		<div class="col-lg-8 col-md-8 col-sm-7 col-xs-7" style="height:95%">	
			<div class="panel panel-default">
			  <div class="panel-body" id="panel-chat">
			  	<div style="padding-bottom: 1%" id="panel-msg">
			    </div>
			  </div>
			</div>
			<div class="row row-send">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 col-send">
					<div class="input-group pull-left" style="margin-top: 10px">
						<div class="text-area" contenteditable="true" id="msgsend"></div><br>
						<button class="btn btn-primary" type="button" onclick="addToChat()">Отправить</button>
						<span class="badge" id="receiver-name"></span>
						<span class="glyphicon glyphicon-remove" id="remove-glyph" onclick="clickRemoveGlyph()" onmouseout="outRemoveGlyph()" onmouseover="overRemoveGlyph()" aria-hidden="true"></span>
					</div>
					<div class="panel panel-default emoji-panel pull-right">
						<div class="panel-body">
							<c:forEach items="${applicationScope.emojiList}" var="emoji">
								<img class="emoji" alt="em_crazy" src="images/${emoji}" onclick="addEmoji(this)" onmouseover="overEmoji(this)" onmouseout="outEmoji(this)">
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-5 col-xs-5 col-users-list">
			<div class="panel panel-info" id="panel-users">
				<div class="panel-heading">
    				<h3 class="panel-title">Список пользователей <span class="label label-primary pull-right" id="count"></span></h3>
  				</div>
				<ul class="list-group" id="users-list">
				</ul>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var user = "${user}";
</script>
<script src="openchat.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>