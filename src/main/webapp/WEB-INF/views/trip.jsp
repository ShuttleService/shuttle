<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Trip</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well">
                    <div class="form-group">
                        <input class="form-control" type="text" placeholder="Client Name">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Source">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Destination">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="Distance">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="Price Per Km">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="Price">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Vehicle">
                    </div>
                    <div class="form-group">
                        <input type="text" placeholder="Driver" class="form-control">
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