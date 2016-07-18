<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div layout="column" layout-padding layout-margin flex ng-controller="TripController">
    <p class="md-title">Adding A Trip</p>
    <form name="addForm" layout="column" layout-padding layout-margin flex>
        <div layout-gt-sm="row" flex>
            <md-input-container flex>
                <label>Client Name</label>
                <input type="text" ng-minlength="4" name="clientName" ng-model="trip.clientName" required>
            </md-input-container>
            <md-input-container flex>
                <label>Client Cellphone Number</label>
                <input type="number" name="clientCellNumber" ng-model="trip.clientCellNumber"
                       ng-minlength="9" required>
            </md-input-container>
        </div>
        <div layout-gt-sm="row">
            <md-input-container flex>
                <label>Source</label>
                <input type="text" ng-minlength="4" name="source" ng-model="trip.source" required>
            </md-input-container>
            <md-input-container flex>
                <label>Destination</label>
                <input type="text" ng-minlength="4" name="destination" ng-model="trip.destination" required>
            </md-input-container>
            <md-input-container flex>
                <label>Distance</label>
                <input type="number" minlength="1" name="distance" ng-model="trip.distance"
                       ng-change="price()" required>
            </md-input-container>
        </div>
        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
            <p class="md-text" flex>Price Per Kilo Meter</p>
            <div layout-gt-sm="row" flex>

                <div layout-gt-sm="row">
                    <md-input-container flex>
                        <label>Currency</label>
                        <md-select ng-model="trip.price.currency.currencyCode" required>
                            <md-option ng-value="currency" ng-repeat="currency in currencyCodes">{{currency}}
                            </md-option>
                        </md-select>
                    </md-input-container>
                    <md-input-container flex>
                        <label>Amount Per Kilo Meter</label>
                        <input type="number" minlength="1" name="pricePerKm" ng-model="trip.pricePerKm"
                               ng-change="price()" required>
                    </md-input-container>

                </div>
                <md-input-container flex>
                    <label>Total Price {{trip.price.currency.currencyCode}}</label>
                    <input type="number" minlength="1" name="price" ng-model="trip.price.amount " required
                           readonly>
                </md-input-container>
            </div>
        </security:authorize>

        <md-input-container flex>
            <label>Company</label>
            <md-select name="company" ng-model="company" ng-change="findBookableDriversAndVehicles()" required>
                <md-option ng-value="company.tradingAs" ng-repeat="company in sharedState.companyPage.content">
                    {{company.tradingAs}}
                </md-option>
            </md-select>
        </md-input-container>
        <div layout-gt-sm="row" flex>
            <md-input-container flex>
                <label>Pick Up Time</label>
                <input type="datetime-local" minlength="16" name="from"
                       ng-change="findBookableDriversAndVehicles()" ng-model="from" id="from" required>
            </md-input-container>
            <md-input-container flex>
                <label>To</label>
                <input type="datetime-local" minlength="16" name="to" ng-model="to"
                       ng-change="findBookableDriversAndVehicles()" id="to" required>
            </md-input-container>
        </div>
        <div layout-gt-sm="row" flex>
            <md-input-container flex>
                <label>Vehicle</label>
                <md-select name="vehicle" ng-model="vehicle" required>
                    <md-option ng-value="vehicle" ng-repeat="vehicle in bookableVehicles">
                        {{vehicle.make+' '+vehicle.model+' '+vehicle.licenseNumber}}
                    </md-option>
                </md-select>
            </md-input-container>
            <md-input-container flex>
                <label>Driver</label>
                <md-select name="driver" ng-model="driver" required>
                    <md-option ng-value="driver" ng-repeat="driver in bookableDrivers">
                        {{driver.firstName+' '+driver.surname}}
                    </md-option>
                </md-select>
            </md-input-container>
        </div>
    </form>
    <div layout-align="center center">
        <md-button type="reset" class="btn btn-default" ng-click="reset();">Reset</md-button>
        <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick();">
            Save
        </md-button>
    </div>
</div>