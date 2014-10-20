<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add" ng-controller="CompanyController">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Company</h4>
                <button type="button" class="close" data-dismiss="modal" >&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well col-xs-12" name="addForm">
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" name="name" placeholder="Company Full Name" ng-model="company.fullName" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" name="tradingAs" placeholder="Trading As" ng-model="company.tradingAs" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" name="slug" placeholder="Slug" ng-required ng-model="company.slug">
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" name="registrationNumber" placeholder="Registration Number" ng-model="company.registrationNumber" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control" name="vatNumber" placeholder="VAT Number" ng-model="company.vatNumber" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" name="logo" placeholder="Logo" ng-model="company.logo">
                    </div>
                    <div class="form-group col-xs-12">
                        <textarea class="col-xs-12" data-ng-model="company.description" placeholder="Description" ng-required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default">Reset</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save</button>
            </div>
        </div>
    </div>
</div>