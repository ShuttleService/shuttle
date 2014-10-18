<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Company</h4>
                <button type="button" class="close" data-dismiss="modal" >&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Company Name">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Trading As">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Slug">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Registration Number">
                    </div>
                    <div class="form-group">
                        <input type="number" class="form-control" placeholder="VAT Number">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Logo">
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