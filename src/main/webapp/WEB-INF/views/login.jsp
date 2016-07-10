<!DOCTYPE html>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="controllers" ng-controller="UserController">
<head class="page-header">
    <link href="resources/style.css" type="text/css" rel="stylesheet">
    <script src="resources/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.js"></script>
    <script src="resources/bower_components/angular/angular.min.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <link href="resources/bower_components/bootstrap/dist/css/bootstrap.css" type="text/plain" rel="stylesheet">
    <script src="resources/js/Service.js"></script>
    <script src="resources/js/Controller.js"></script>
    <title>Shuttle Service</title>
</head>
<body>
<ng-include src="'resources/template/userAdd.jsp'"></ng-include>
<div class="container text-center">
    <div class="row">

        <div class="col-xs-12">

            <c:if test="${param.error != null}">
                <p class="well has-error danger">
                    <strong id="message" class="str">Access Denied</strong>
                </p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p class="well">
                    You Are Now Logged Out.
                </p>
            </c:if>
            <span class="col-xs-5"></span>

            <form class="form-signin col-xs-2" method="POST" name="login" action="<c:url value="/login"/>">
                <div class="form-group">
                    <input path="userName" id="username" name="username" class="form-control" placeholder="User Name"
                           required/>
                </div>
                <div class="form-group">
                    <input path="password" type="password" name="password" class="form-control" id="password"
                           placeholder="Password" required/>
                </div>
                <input type="hidden" name="${_csrf_parameterName}" value="${_csrf_token}">
                <input type="submit" class="btn btn-primary" id="login" value="Login">
                <button class="btn btn-default" id="signUp" data-target="#add" data-toggle="modal"
                        onclick="event.preventDefault()">Sign Up
                </button>
            </form>

            <span class="col-xs-5" ng-controller="SharedController"></span>
        </div>
    </div>
</div>
</body>
</html>
