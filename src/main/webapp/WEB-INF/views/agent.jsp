<div class="container" ng-controller="AgentController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding An Agent</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form name="addForm">
                        <md-input-container>
                            <label>Agent Full Name</label>
                            <input type="text" ng-minlength="2" name="fullName" ng-model="agent.fullName" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Trading As</label>
                            <input type="text" ng-minlength="2" name="tradingAs" ng-model="agent.tradingAs" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Slug</label>
                            <input type="text" ng-minlength="1" name="slug" ng-model="agent.slug" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Registration Number</label>
                            <input type="text" ng-minlength="1" name="registrationNumber"
                                   ng-model="agent.registrationNumber" required>
                        </md-input-container>
                        <md-input-container>
                            <label>VAT Number</label>
                            <input type="text" class="form-control" ng-minlength="1" name="vatNumber"
                                   ng-model="agent.vatNumber" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Logo</label>
                            <input type="text" name="logo" ng-model="agent.logo">
                        </md-input-container>
                        <md-input-container>
                            <label>Description</label>
                            <textarea ng-minlength="2" ng-model="agent.description" required></textarea>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" ng-click="reset()">Reset</md-button>
                    <md-button type="button" data-dismiss="modal">Close</md-button>
                    <md-button type="button" ng-disabled="!canSave()" ng-click="saveClick()">
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
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="agent in page.content">
                <td>{{agent.reference}}</td>
                <td>{{agent.slug}}</td>
                <td>{{agent.tradingAs}}</td>
                <td>{{agent.fullName}}</td>
                <td>{{agent.description}}</td>
                <td>{{agent.vatNumber}}</td>
                <td>{{agent.registrationNumber}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
