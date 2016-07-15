<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div ng-controller="TripController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Trip</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form name="addForm">
                        <md-input-container>
                            <label>Client Name</label>
                            <input type="text" ng-minlength="4" name="clientName" ng-model="trip.clientName" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Client Cellphone Number</label>
                            <input type="number" name="clientCellNumber" ng-model="trip.clientCellNumber"
                                   ng-minlength="9" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Source</label>
                            <input type="text" ng-minlength="4" name="source" ng-model="trip.source" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Destination</label>
                            <input type="text" ng-minlength="4" name="destination" ng-model="trip.destination" required>
                        </md-input-container>
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
                            <md-input-container>
                                <label>Distance</label>
                                <input type="number" minlength="1" name="distance" ng-model="trip.distance"
                                       ng-change="price()" required>
                            </md-input-container>
                            <div class="panel col-xs-12 panel-default">
                                <div class="panel-heading">
                                    Price Per Kilo Meter
                                </div>
                                <div class="panel-body colxs-12">
                                    <md-input-container>
                                        <label>Currency</label>
                                        <select ng-options="currency for currency in currencyCodes"
                                                ng-model="trip.price.currency.currencyCode"
                                                required></select>
                                    </md-input-container>
                                    <md-input-container>
                                        <label>Amount Per Kilo Meter</label>
                                        <input type="number" minlength="1" name="pricePerKm" ng-model="trip.pricePerKm"
                                               ng-change="price()" required>
                                    </md-input-container>
                                </div>
                            </div>
                            <md-input-container>
                                <label>Total Price {{trip.price.currency.currencyCode}}</label>
                                <input type="number" minlength="1" name="price" ng-model="trip.price.amount " required
                                       readonly>
                            </md-input-container>
                        </security:authorize>
                        <md-input-container>
                            <label>Company</label>
                            <select name="company" ng-model="company"
                                    ng-change="findBookableDriversAndVehicles()"
                                    ng-options="company.tradingAs for company in sharedState.companyPage.content"
                                    required></select>
                        </md-input-container>
                        <md-input-container>
                            <label>Pick Up Time</label>
                            <input type="datetime-local" minlength="16" name="from"
                                   ng-change="findBookableDriversAndVehicles()" ng-model="from" id="from" required>
                        </md-input-container>
                        <md-input-container>
                            <label>To</label>
                            <input type="datetime-local" minlength="16" name="to" ng-model="to"
                                   ng-change="findBookableDriversAndVehicles()" id="to" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Vehicle</label>
                            <select name="vehicle" ng-model="vehicle"
                                    ng-options="vehicle.make+' '+vehicle.model+' '+vehicle.licenseNumber for vehicle in bookableVehicles "
                                    required></select>
                        </md-input-container>
                        <md-input-container>
                            <label>Driver</label>
                            <select name="driver" ng-model="driver"
                                    ng-options="driver.firstName+' '+driver.surname for driver in bookableDrivers"
                                    required>
                            </select>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" class="btn btn-default" ng-click="reset();">Reset</md-button>
                    <md-button type="button" class="btn btn-default" data-dismiss="modal">Close</md-button>
                    <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick();">
                        Save
                    </md-button>
                </div>
            </div>
        </div>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Reference</th>
            <th>Client Name</th>
            <th>Cell Number</th>
            <th>Source</th>
            <th>Destination</th>
            <th>Distance</th>
            <th>Price Per Km</th>
            <th>Total Price</th>
            <th>Vehicle</th>
            <th>Driver</th>
            <th>Company</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="trip in page.content">
            <td>{{trip.reference}}</td>
            <td>{{trip.clientName}}</td>
            <td>{{trip.clientCellNumber}}</td>
            <td>{{trip.source}}</td>
            <td>{{trip.destination}}</td>
            <td>{{trip.distance}}</td>
            <td>{{trip.price.currency.currencyCode +' ' }} {{trip.pricePerKm | number:2}}</td>
            <td>{{trip.price.currency.currencyCode + ' '}} {{trip.price.amount | number:2}}</td>
            <td>{{trip.vehicleName}}</td>
            <td>{{trip.driverName}}</td>
            <td>{{trip.companyName}}</td>
        </tr>
        </tbody>
    </table>
</div>