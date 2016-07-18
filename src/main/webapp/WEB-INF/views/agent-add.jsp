<div flex layout="column" ng-controller="AgentController">
    <div flex>
        <p class="md-title">Adding An Agent</p>
        <form name="addForm">
            <div flex layout-gt-sm="row">
                <md-input-container flex>
                    <label>Agent Full Name</label>
                    <input type="text" ng-minlength="2" name="fullName" ng-model="agent.fullName" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Trading As</label>
                    <input type="text" ng-minlength="2" name="tradingAs" ng-model="agent.tradingAs" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Slug</label>
                    <input type="text" ng-minlength="1" name="slug" ng-model="agent.slug" required>
                </md-input-container>
            </div>
            <div flex layout-gt-sm="row">
                <md-input-container flex>
                    <label>Registration Number</label>
                    <input type="text" ng-minlength="1" name="registrationNumber"
                           ng-model="agent.registrationNumber" required>
                </md-input-container>
                <md-input-container flex>
                    <label>VAT Number</label>
                    <input type="text" class="form-control" ng-minlength="1" name="vatNumber"
                           ng-model="agent.vatNumber" required>
                </md-input-container>
                <md-input-container flex>
                    <label>Logo</label>
                    <input type="text" name="logo" ng-model="agent.logo">
                </md-input-container>
            </div>
            <md-input-container flex class="md-block">
                <label>Description</label>
                <textarea ng-minlength="2" ng-model="agent.description" required></textarea>
            </md-input-container>
        </form>
        <div flex layout="row">
            <md-button type="reset" ng-click="reset()">Reset</md-button>
            <md-button type="button" ng-disabled="!canSave()" ng-click="saveClick()">
                Save
            </md-button>
        </div>
    </div>
</div>