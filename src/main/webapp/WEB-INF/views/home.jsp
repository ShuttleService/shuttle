<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tile" uri="http://tiles.apache.org/tags-tiles" %>
<html ng-app="controllers" ng-controller="SharedController">
<body layout="column">
<md-toolbar class="md-toolbar-tools" layout layout-align="center center">
    <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser','ROLE_world')">
        <md-button href='#/trip'>Trips</md-button>
    </security:authorize>
    <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
        <md-button href='#/driver'>Drivers</md-button>
    </security:authorize>
    <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
        <md-button href='#/vehicle'>Vehicles</md-button>
    </security:authorize>
    <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
        <md-button href='#/user'>Users</md-button>
    </security:authorize>

    <md-button href='#/review'>Reviews</md-button>

    <security:authorize access="hasAnyRole('ROLE_admin','ROLE_agent')">
        <md-menu>
            <md-button ng-click="$mdOpenMenu($event)">Agent</md-button>
            <md-menu-content>
                <md-button href='#/company'>Companies</md-button>
            </md-menu-content>
        </md-menu>
    </security:authorize>
    <security:authorize access="hasAnyRole('ROLE_admin')">
        <md-menu>
            <md-button ng-click="$mdOpenMenu($event)">Admin</md-button>
            <md-menu-content>
                <md-button href='#/agent'>Agents</md-button>
                <md-button href='#/company'>Companies</md-button>
            </md-menu-content>
        </md-menu>
    </security:authorize>
    <md-button href="<spring:url value="logout"/>">Logout</md-button>

    <md-button href="{{'#/'+addUrl}}">
        <md-icon>add</md-icon>
    </md-button>

</md-toolbar>
<md-content layout="column" layout-align="top center" flex>
    <tile:insertAttribute name="body"/>
</md-content>
<md-toolbar></md-toolbar>
</body>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <script src="resources/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="resources/bower_components/angular/angular.min.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <script src="resources/bower_components/angular-route/angular-route.min.js"></script>
    <link href="resources/bower_components/bootstrap/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
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

    <link href="resources/style.css" type="text/css" rel="stylesheet">
</head>
</html>

