<div flex layout="column" layout-padding ng-controller="CompanyController">
    <form name="addForm" flex>
        <div flex layout-gt-sm="row">
            <md-input-container flex>
                <label>Company Full Name</label>
                <input type="text" ng-minlength="2" name="fullName" ng-model="company.fullName" required>
            </md-input-container>
            <md-input-container flex>
                <label>Trading As</label>
                <input type="text" ng-minlength="2" name="tradingAs" ng-model="company.tradingAs" required>
            </md-input-container>
            <md-input-container flex>
                <label>Slug</label>
                <input type="text" ng-minlength="1" name="slug" ng-model="company.slug" required>
            </md-input-container>
        </div>
        <div flex layout-gt-sm="row">
            <md-input-container flex>
                <label>Registration Number</label>
                <input type="text" ng-minlength="1" name="registrationNumber"
                       ng-model="company.registrationNumber" required>
            </md-input-container>
            <md-input-container flex>
                <label>VAT Number</label>
                <input type="text" ng-minlength="1" name="vatNumber" ng-model="company.vatNumber" required>
            </md-input-container>
            <md-input-container flex>
                <label>Logo</label>
                <input type="text" name="logo" ng-model="company.logo">
            </md-input-container>
        </div>
        <div>
            <md-input-container flex>
                <label>Agent</label>
                <md-select name="agent" id="agent" ng-model="agent">
                    <md-option ng-value="agent" ng-repeat="agent in sharedState.agentPage.content">
                        {{agent.fullName}}
                    </md-option>
                </md-select>
            </md-input-container>
            <md-input-container flex class="md-block">
                <label>Description</label>
                <textarea ng-minlength="2" name="description"
                          ng-model="company.description" required></textarea>
            </md-input-container>
        </div>
    </form>
    <div layout="row">
        <md-button type="reset" class="btn btn-default" ng-click="reset()">Reset</md-button>

        <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">
            Save
        </md-button>
    </div>
</div>