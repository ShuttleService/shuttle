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
                    <form name="addForm">
                        <md-input-container>
                            <label>Company Full Name</label>
                            <input type="text" ng-minlength="2" name="fullName" ng-model="company.fullName" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Trading As</label>
                            <input type="text" ng-minlength="2" name="tradingAs" ng-model="company.tradingAs" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Slug</label>
                            <input type="text" ng-minlength="1" name="slug" ng-model="company.slug" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Registration Number</label>
                            <input type="text" ng-minlength="1" name="registrationNumber" ng-model="company.registrationNumber" required>
                        </md-input-container>
                        <md-input-container>
                            <label>VAT Number</label>
                            <input type="text" ng-minlength="1" name="vatNumber" ng-model="company.vatNumber" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Logo</label>
                            <input type="text" name="logo" ng-model="company.logo">
                        </md-input-container>
                        <md-input-container>
                            <label for="agent">Agent</label>
                            <select name="agent" id="agent" ng-model="agent"
                                    ng-options="agent.fullName for agent in sharedState.agentPage.content"></select>
                        </md-input-container>
                        <md-input-container>
                            <label>Description</label>
                            <textarea ng-minlength="2" name="description"
                                      ng-model="company.description" required></textarea>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" class="btn btn-default" ng-click="reset()">Reset</md-button>
                    <md-button type="button" class="btn btn-default" data-dismiss="modal">Close</md-button>
                    <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">
                        Save
                    </md-button>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12 text-center">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Reference</th>
                <th>Slug</th>
                <th>Trading As</th>
                <th>Full Name</th>
                <th>Description</th>
                <th>VAT Number</th>
                <th>Registration Number</th>
                <th>Agent</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="company in page.content">
                <td>{{company.reference}}</td>
                <td>{{company.slug}}</td>
                <td>{{company.tradingAs}}</td>
                <td>{{company.fullName}}</td>
                <td>{{company.description}}</td>
                <td>{{company.vatNumber}}</td>
                <td>{{company.registrationNumber}}</td>
                <td>{{company.agentName}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
