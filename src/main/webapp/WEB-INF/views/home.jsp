<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html ng-app="controllers" ng-controller="SharedController">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="resources/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="resources/bower_components/angular/angular.min.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <link href="resources/bower_components/bootstrap/dist/css/bootstrap.css" type="text/css" rel="stylesheet">

    <script src="resources/js/Service.js"></script>
    <script src="resources/js/Controller.js"></script>

    <link href="resources/style.css" type="text/css" rel="stylesheet">

    <title><tiles:getAsString name="title"/></title>
</head>
<body>

<div class="container">
    <div class="col-xs-12">
        <nav class="navbar navbar-default navbar-static-top">
            <div class="navbar-inner">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-target="#menu" data-toggle="collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><tiles:getAsString name="title"/></a>
                </div>
                <div class="collapse navbar-collapse" id="menu">
                    <ul class="nav navbar-nav">
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser','ROLE_world')">
                            <li><a class="active" href="<spring:url value='trip'/>">Trips</a></li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
                            <li><a href="<spring:url value='driver'/> ">Drivers</a></li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
                            <li><a href="<spring:url value='vehicle' />">Vehicles</a></li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
                            <li><a href="<spring:url value='user' />">Users</a></li>
                        </security:authorize>

                        <li><a href="<spring:url value="review"/>">Reviews</a></li>

                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_agent')">
                            <li class="dropdown">
                                <a data-toggle="dropdown" class="dropdown-toggle" data-target="#" href="#">Agent<b
                                        class="caret"></b> </a>
                                <ul class="dropdown-menu">
                                    <li><a href="<spring:url value='/company'/>">Companies</a></li>
                                </ul>
                            </li>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ROLE_admin')">
                            <li class="dropdown">
                                <a data-toggle="dropdown" class="dropdown-toggle" data-target="#" href="#">Admin<b
                                        class="caret"></b> </a>
                                <ul class="dropdown-menu">
                                    <li><a href="<spring:url value='/agent'/>">Agents</a></li>
                                    <li><a href="<spring:url value='/company'/>">Companies</a></li>
                                </ul>
                            </li>
                        </security:authorize>
                        <li class="pull-right"><a href="<spring:url value="logout"/>">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <div class="pull-right">
        <button data-toggle="modal" data-target="#add"><span class="glyphicon-plus"></span></button>
    </div>
    <div class="row">
        <div class="col-xs-12 text-center">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
</div>
</body>
</html>

