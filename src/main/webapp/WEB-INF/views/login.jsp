<!DOCTYPE html>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html ng-app="controllers" ng-controller="UserController">
<head class="page-header">
    <script src="resources/vendor/jquery-2.1.1.min.js"></script>
    <script src="resources/vendor/bootstrap-3.2.0/dist/js/bootstrap.js"></script>
    <link href="resources/vendor/bootstrap-3.2.0/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
    <script src="resources/vendor/angular-1.3.0-rc.4/angular.js"></script>
    <script src="resources/vendor/angular-1.3.0-rc.4/angular-resource.js"></script>
    <script src="resources/js/Service.js"></script>
    <script src="resources/js/Controller.js"></script>
    <title>Shuttle Service</title>
</head>
<body>
<ng-include src="'resources/template/userAdd.jsp'"></ng-include>
<div class="container text-center">
    <div class="row">

        <div class="col-xs-12">
            <span class="col-xs-5"></span>
            <form:form cssClass="form-signin col-xs-2" commandName="login">
                <div class="form-group">
                   <form:input path="userName" id="userName" cssClass="form-control" placeholder="User Name"/>
                </div>
                <div class="form-group">
                    <form:input path="password" type="password" cssClass="form-control" id="password" placeholder="Password"/>
                </div>
                <form:button type="submit" class="btn btn-primary">Login</form:button>
                <form:button type="button" class="btn btn-default" data-target="#add"
                             data-toggle="modal">Sign Up</form:button>
            </form:form>
            <span class="col-xs-5"></span>
        </div>
    </div>
</div>
</body>
</html>
