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
    <form method="POST" name="login" action="<c:url value="/login"/>" layout-align="center center" layout="column"
          ng-if="!register">
        <md-input-container>
            <label>User name</label>
            <input path="userName" id="username" name="username" required>
        </md-input-container>
        <md-input-container>
            <label>Password</label>
            <input path=" password " type="password" name="password" id="password" required>
        </md-input-container>
        <div>
            <input type="hidden" name="${_csrf_parameterName}" value="${_csrf_token}">
            <md-button type="submit" class="md-primary md-raised" ng-click="register=false" id="login">Login</md-button>
            <md-button class="md-primary" id="signUp" ng-click="toggleRegister()">
                Sign Up
            </md-button>
        </div>
    </form>
    <div layout="column" ng-if="register">
        <p class="md-title">New User Sign Up Form</p>
        <form name="formHolder.addForm" layout="column" layout-padding class="md-inline-form">
            <input type="hidden" name="userName" ng-minlength="6" ng-model="user.email" required readonly>
            <div layout-padding layout-gt-sm="row" flex>
                <md-input-container class="md-block" flex>
                    <label>First Name</label>
                    <input type="text" ng-minlength="1" ng-model="user.firstName" required>
                </md-input-container>
                <md-input-container class="md-block" flex>
                    <label>Surname</label>
                    <input type="text" ng-minlength="1" ng-model="user.surname" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Cell Number</label>
                    <input type="tel" ng-minlength="10" name="cellNumber" ng-model="user.cellNumber" required>
                </md-input-container>
            </div>
            <div layout-padding layout-gt-sm="row">
                <md-input-container flex>
                    <label>Email</label>
                    <input type="email" name="email" ng-minlength="6" ng-model="user.email" required>
                </md-input-container>
                <label class="md-warn" ng-show="user.password !== formHolder.confirmPassword">
                    Password And Confirm Password Must Match.</label>
                <md-input-container flex>
                    <label>Password</label>
                    <input type="password" ng-minlength="6" name="password" ng-model="user.password" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Confirm Password</label>
                    <input type="password" ng-minlength="6" name="confirmPassword"
                           ng-model="formHolder.confirmPassword" required>
                </md-input-container>
            </div>
            <div layout-padding layout-gt-sm="row" flex>
                <security:authorize access="hasAnyRole('ROLE_admin')">
                    <md-input-container flex>
                        <label>Role</label>
                        <md-select name="authorities" ng-model="user.authority" required>
                            <md-option ng-value="role.role" ng-repeat="role in sharedState.roles">{{role.role}}
                            </md-option>
                        </md-select>
                    </md-input-container>
                    <md-input-container flex>
                        <label>Company</label>
                        <md-select name="company" ng-model="user.company">
                            <md-option ng-value="company.tradingAs"
                                       ng-repeat="company in sharedState.companyPage.content">
                                {{company.tradingAs}}
                            </md-option>
                        </md-select>
                    </md-input-container>
                </security:authorize>
                <md-datepicker type="date" md-placeholder="Date Of Birth" flex
                               name="dateOfBirth" ng-model="user.dateOfBirth" required></md-datepicker>
            </div>
            <div layout-padding layout="row" flex>
                <md-input-container flex>
                    <label>Street Address</label>
                    <input type="text" ng-minlength="2"
                           name="streetAddress" ng-model="user.streetAddress" required>
                </md-input-container>
            </div>
            <div layout-padding layout-gt-sm="row" flex>
                <md-input-container flex>
                    <label>Suburb</label>
                    <input type="text" ng-minlength="1" name="suburb" ng-model="user.suburb" required>
                </md-input-container>
                <md-input-container flex>
                    <label>City/Town</label>
                    <input type="text" ng-minlength="1" name="town" ng-model="user.town" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Province/State</label>
                    <input type="text" ng-minlength="1" name="province" ng-model="user.province" required>
                </md-input-container>
            </div>
            <div flex layout-padding layout-gt-sm="row">
                <md-input-container flex>
                    <label>Postal Code</label>
                    <input type="number" ng-minlength="2" name="postalCode" ng-model="user.postalCode" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Country</label>
                    <md-select ng-model="user.country">
                        <md-option ng-value="country.code" ng-repeat="country in countries">{{country.name}}
                        </md-option>
                    </md-select>
                </md-input-container>
            </div>
        </form>
        <div>
            <md-button type="reset" ng-click="reset()">Reset</md-button>
            <md-button type="button" data-dismiss="modal">Close</md-button>
            <md-button type="button" ng-disabled="false" ng-click="saveClick()">Save
            </md-button>
        </div>
    </div>

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
