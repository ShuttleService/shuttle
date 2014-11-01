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
                <form:button type="button" class="btn btn-default" data-target="#signUp"
                             data-toggle="modal">Sign Up</form:button>
            </form:form>
            <span class="col-xs-5"></span>
        </div>
    </div>
</div>
<div class="modal fade" id="signUp">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New User Sign Up Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well col-xs-12" name="addForm">
                    <div class="form-group col-xs-6">
                        <input type="email" class="form-control" placeholder="Email" name="email" ng-minlength="4" ng-model="user.email"  ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="User Name" class="form-control" name="userName" ng-minlength="6" ng-model="user.userName" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" class="form-control" placeholder="Password" ng-minlength="6" name="password" ng-model="user.password" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" placeholder="Retype Password" class="form-control" ng-minlength="6" name="retypePassword" ng-model="user.retypePassword" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="tel" class="form-control" placeholder="Cell Number" ng-minlength="10" name="cellNumber" ng-model="user.cellNumber" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="date" class="form-control" placeholder="Date Of Birth" ng-minlength="10" name="dateOfBirth" ng-model="user.dateOfBirth" ng-required>
                    </div>
                    <div class="form-group col-xs-11">
                        <textarea type="text" class="form-control" placeholder="Street Address" ng-minlength="2" name="streetAddress" ng-model="user.streetAddress" ng-required></textarea>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Surburb" ng-minlength="2" name="surburb" ng-model="user.surburb" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="City/Town" ng-minlength="2" name="town" ng-model="user.town" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control " placeholder="Province" ng-minlength="2" name="province" ng-model="user.province" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control" placeholder="Postal Code" ng-minlength="4" name="postalCode" ng-model="user.postalCode" ng-required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default">Reset</button>
                <button type="button" class="btn btn-default " data-dismiss="modal" >Close</button>
                <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
