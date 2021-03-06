<div layout="column" layout-padding flex ng-controller="VehicleController">
    <form name="addForm" layout="column" layout-padding>
        <div flex layout-gt-sm="row">
            <md-input-container flex>
                <label>Make</label>
                <input type="text" name="make" ng-model="vehicle.make" ng-minlength="1" required>
            </md-input-container>
            <md-input-container flex>
                <label>Model</label>
                <input type="text" ng-model="vehicle.model" name="model" ng-minlength="1" required>
            </md-input-container>
        </div>
        <div flex layout-gt-sm="row">
            <md-input-container flex>
                <label>Year Model</label>
                <input type="number" ng-model="vehicle.year" name="year" ng-minlength="4" required>
            </md-input-container>
            <md-input-container flex>
                <label>Type</label>
                <md-select ng-model="vehicle.type" name="type" required>
                    <md-option ng-value="type" ng-repeat="type in vehicleTypes">{{type}}</md-option>
                </md-select>
            </md-input-container>
        </div>
        <div layout-gt-sm="row" flex>
            <md-input-container flex>
                <label>Capacity</label>
                <input type="number" ng-model="vehicle.seats" name="seats" ng-minlength="1" required>
            </md-input-container>
            <md-input-container flex>
                <label>License Number</label>
                <input type="text" ng-model="vehicle.licenseNumber" ng-minlength="1" name="licenseNumber"
                       required>
            </md-input-container>
            <md-input-container flex>
                <label>Company</label>
                <md-select ng-model="company" name="company">
                    <md-option ng-value="company" ng-repeat="company in sharedState.companyPage.content">
                        {{company.tradingAs}}
                    </md-option>
                </md-select>
            </md-input-container>
        </div>
    </form>
    <div layout="row" layout-align="center right" layout-padding>
        <md-button type="reset" ng-click="reset()">Reset</md-button>
        <md-button type="button" ng-disabled="!canSave()" ng-click="saveClick();">
            Save
        </md-button>
    </div>
</div>