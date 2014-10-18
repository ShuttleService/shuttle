<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Vehicle</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Make">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Model">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="Year Model">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Type">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="Capacity">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Number Plate">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>