<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="resources/vendor/bootstrap-3.2.0/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
    <title><tiles:getAsString name="title"/></title>
</head>
<body>
<div class="container">

    <div class="col-xs-12">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-target="#menu" data-toggle="collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Shuttle Service</a>
                </div>
                <div class="collapse navbar-collapse" id="menu">
                    <ul class="nav navbar-nav">
                        <li><a class="active" href="<spring:url value='trip'/>">Trips</a></li>
                        <li><a href="<spring:url value='driver'/> ">Drivers</a></li>
                        <li><a href="<spring:url value='vehicle' />">Vehicles</a> </li>
                        <li><a href="<spring:url value='admin'/>">Admin</a></li>
                        <li><a href="<spring:url value="review"/>">Reviews</a> </li>
                    </ul>
                </div>
            </div>

        </nav>
    </div>
    <div class="row">
        <div class="col-xs-12 text-center">
            <div class="page-header">
                <h3> <tiles:getAsString name="title"/></h3>
            </div>
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
</div>
</body>
</html>
