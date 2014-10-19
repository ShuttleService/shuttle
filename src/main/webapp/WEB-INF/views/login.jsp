<!DOCTYPE html>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head class="page-header">
    <script src="resources/vendor/jquery-2.1.1.min.js"></script>
    <script src="resources/vendor/bootstrap-3.2.0/dist/js/bootstrap.js"></script>
    <link href="resources/vendor/bootstrap-3.2.0/dist/css/bootstrap.css" type="text/css" rel="stylesheet">
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
                <form class="form-horizontal well col-xs-12">
                    <div class="form-group col-xs-6">
                        <input type="email" class="form-control" placeholder="Email">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="User Name" class="form-control">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" class="form-control" placeholder="Password">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" placeholder="Retype Password" class="form-control">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="tel" class="form-control" placeholder="Cell Number">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="date" class="form-control" placeholder="Date Of Birth (MM-DD-YYYY)">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Street Address">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Suburb">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="City/Town">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control " placeholder="Province">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control" placeholder="Postal Code">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default">Reset</button>
                <button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
