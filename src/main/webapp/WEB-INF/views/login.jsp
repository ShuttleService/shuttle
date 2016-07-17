<!DOCTYPE html>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="controllers" ng-controller="UserController" layout="column" ng-cloak>
<body layout="column">

<md-toolbar class="md-toolbar-tools" layout-align="center center">
    <div>
        <c:if test="${param.error != null}">
            <p class="md-warn">
                <strong id="message">Access Denied</strong>
            </p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p class="md-warn">
                Please register or log in
            </p>
        </c:if>
    </div>
</md-toolbar>
<md-content layout-padding layout-margin layout="column" layout-align="center center" flex>
    <form method="POST" name="login" action="<c:url value="/login"/>" layout-align="center center" layout="column">
        <md-input-container>
            <label>User name</label>
            <input path="userName" id="username" name="username" required>
        </md-input-container>
        <md-input-container>
            <label>Password</label>
            <input path=" password" type="password" name="password" id="password" required>
        </md-input-container>
        <div>
            <input type="hidden" name="${_csrf_parameterName}" value="${_csrf_token}">
            <md-button type="submit" class="md-primary md-raised" ng-click="register=false" id="login">Login</md-button>
            <md-button type="button" class="md-primary md-button" href="<spring:url value="/signup"/>">
                Sign Up
            </md-button>
        </div>
    </form>
</md-content>
<md-toolbar class="md-toolbar-tools">
</md-toolbar>
</body>
<head>
    <link href="resources/style.css" type="text/css" rel="stylesheet">
    <script src="resources/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.js"></script>
    <script src="resources/bower_components/angular/angular.min.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <script src="resources/bower_components/angular-route/angular-route.min.js"></script>
    <script src="resources/bower_components/angular-aria/angular-aria.min.js"></script>
    <script src="resources/bower_components/angular-animate/angular-animate.min.js"></script>
    <script src="resources/bower_components/angular-messages/angular-messages.min.js"></script>
    <script src="resources/bower_components/angular-material/angular-material.min.js"></script>
    <link href="resources/bower_components/bootstrap/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,500,600,700,300Italic,500Italic,600Italic,700Italic"
          type="text/css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons" type="text/css" rel="stylesheet">
    <link href="resources/bower_components/angular-material/angular-material.min.css" type="text/css" rel="stylesheet">
    <script src="resources/js/Service.js"></script>
    <script src="resources/js/Controller.js"></script>
    <title>Shuttle Service</title>
</head>
</html>
