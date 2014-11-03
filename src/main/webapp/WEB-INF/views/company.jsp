<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container" ng-controller="CompanyController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Company</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal well col-xs-12" name="addForm">
                        <div class="form-group col-xs-6">
                            <input type="text" class="form-control" ng-minlength="2" name="name"
                                   placeholder="Company Full Name" ng-model="company.fullName" ng-required>
                        </div>
                        <div class="form-group col-xs-6">
                            <input type="text" class="form-control" ng-minlength="2" name="tradingAs"
                                   placeholder="Trading As" ng-model="company.tradingAs" ng-required>
                        </div>
                        <div class="form-group col-xs-6">
                            <input type="text" class="form-control" ng-minlength="2" name="slug" placeholder="Slug"
                                   ng-required ng-model="company.slug">
                        </div>
                        <div class="form-group col-xs-6">
                            <input type="number" class="form-control" ng-minlength="10" name="registrationNumber"
                                   placeholder="Registration Number" ng-model="company.registrationNumber" ng-required>
                        </div>
                        <div class="form-group col-xs-6">
                            <input type="number" class="form-control" ng-minlength="10" name="vatNumber"
                                   placeholder="VAT Number" ng-model="company.vatNumber" ng-required>
                        </div>
                        <div class="form-group col-xs-6">
                            <input type="text" class="form-control" name="logo" placeholder="Logo"
                                   ng-model="company.logo">
                        </div>
                        <div class="form-group col-xs-12">
                            <textarea class="col-xs-12" ng-minlength="2" data-ng-model="company.description"
                                      placeholder="Description" ng-required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default">Reset</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12 text-center">
        <table class="table-bordered">
            <thead>
            <th>Id</th>
            <th>Slug</th>
            <th>Trading As</th>
            <th>Full Name</th>
            <th>Description</th>
            </thead>
            <tbody>
            <tr ng-repeat="company in companies">
                <td>{{company.id}}</td>
                <td>{{company.slug}}</td>
                <td>{{company.tradingAs}}</td>
                <td>{{company.fullName}}</td>
                <td>{{company.description}}</td>
            </tr>
            </tbody>
        </table>
        {{page | json}}
    </div>
</div>