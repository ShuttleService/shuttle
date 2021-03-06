<div flex layout-padding layout="column" ng-controller="DriverController">
    <form name="addForm" layout="column" layout-padding>
        <div layout-padding layout-align-gt-sm="row" flex>
            <md-input-container flex>
                <label>First Name</label>
                <input type="text" minlength="2" ng-model="driver.firstName" name="firstName" required>
            </md-input-container>
            <md-input-container flex>
                <label>Surname</label>
                <input type="text" ng-model="driver.surname" name="surname" minlength="2" required>
            </md-input-container>
        </div>
        <div layout-align-gt-sm="row" layout-padding flex>
            <md-input-container flex>
                <label>Email Address</label>
                <input type="email" minlength="6" ng-model="driver.email" name="email" required>
            </md-input-container>
            <md-input-container flex>
                <label>Cellphone Number</label>
                <input type="number" ng-minlength="9" name="cellNumber" ng-model="driver.cellPhone" required>
            </md-input-container>
        </div>
        <md-input-container flex>
            <label>Company</label>
            <md-select name="company" ng-model="company">
                <md-option ng-value="company" ng-repeat="company in sharedState.companyPage.content">
                    {{company.tradingAs}}
                </md-option>
            </md-select>
        </md-input-container>
        <div layout-padding layout-align-gt-sm="row" flex>
            <p class="md-text">Driver's License Details</p>
            <md-input-container flex>
                <label>Class</label>
                <input type="text" minlength="1" maxlength="3" name="driversLicenseClass"
                       ng-model="driver.driversLicenseClass" required>
            </md-input-container>
            <md-input-container flex>
                <label>Number</label>
                <input type="text" minlength="4" name="driversLicenseNumber" ng-model="driver.driversLicenseNumber"
                       required>
            </md-input-container>
        </div>
    </form>
    <div layout="right center">
        <md-button type="reset" class="btn btn-default" ng-click="reset()">Reset</md-button>
        <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">
            Save
        </md-button>
    </div>
</div>