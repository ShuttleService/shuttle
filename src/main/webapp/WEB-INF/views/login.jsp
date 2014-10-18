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
        <div class="col-xs-12 span12">
            <form:form cssClass="form-horizontal" commandName="login">
                <div class="form-group">
                    <label for="userName">User Name<form:input path="userName" id="userName"
                                                               cssClass="form-control"/> </label>
                </div>
                <div class="form-group">
                    <label form="password">Password<form:input path="password" type="password" cssClass="form-control" id="password"/></label>
                </div>
                <form:button type="submit" class="btn btn-primary">Login</form:button>
                <form:button type="button" class="btn btn-default" data-target="#signUp" data-toggle="modal">Sign Up</form:button>
            </form:form>
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
                I Am The Modal Body
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
