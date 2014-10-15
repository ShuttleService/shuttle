<!DOCTYPE html>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head class="page-header">
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
                    <label form="password">Password<form:input path="password" type="password" cssClass="form-control"
                                                               id="password"/></label>
                </div>
                <form:button type="submit" class="btn btn-primary">Login</form:button>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>
