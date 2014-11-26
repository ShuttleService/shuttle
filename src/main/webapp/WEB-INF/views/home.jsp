<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html ng-app="controllers">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="resources/vendor/jquery-2.1.1.min.js"></script>
    <script src="resources/vendor/bootstrap-3.2.0/dist/js/bootstrap.js"></script>
    <script src="resources/vendor/angular-1.3.0-rc.4/angular.js"></script>
    <script src="resources/vendor/angular-1.3.0-rc.4/angular-resource.js"></script>
    <script src="resources/js/Service.js"></script>
    <script src="resources/js/Controller.js"></script>
    <link href="resources/vendor/bootstrap-3.2.0/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
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
                        <li><a class="active" href="<spring:url value='trip'/>">Trips</a></li>
                        <li><a href="<spring:url value='driver'/> ">Drivers</a></li>
                        <li><a href="<spring:url value='vehicle' />">Vehicles</a></li>
                        <li><a href="<spring:url value='user' />">Users</a> </li>
                        <li><a href="<spring:url value="review"/>">Reviews</a></li>
                        <li class="dropdown">
                            <a data-toggle="dropdown" class="dropdown-toggle" data-target="#" href="#">Admin<b class="caret"></b> </a>
                            <ul class="dropdown-menu">
                                <li><a href="<spring:url value='/company'/>">Companies</a></li>
                            </ul>
                        </li>
                        <li><a href="<spring:url value="logout"/>">Logout</a></li>
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
