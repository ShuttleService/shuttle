<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Driver</h4>
                <button type="button" data-dismiss="modal" class="close">&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well col-xs-12">
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="First Name" class="form-control">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="Surname" class="form-control">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="email" class="form-control" placeholder="Email Address">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="tel" class="form-control" placeholder="Cellphone Number">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Drivers License Class">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Drivers License Number">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default">Reset</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>

    </div>
</div>
