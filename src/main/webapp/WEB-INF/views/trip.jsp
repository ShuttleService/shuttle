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
                    <form class="form-horizontal well col-xs-12" name="addForm">
                        <div class="form-group col-xs-6">
                            <label class="control-label">Client Name</label>
                            <input class="form-control" type="text" placeholder="Client Name" ng-minlength="4"
                                   name="clientName" ng-model="trip.clientName" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Client Cellphone Number</label>
                            <input type="number" name="clientCellNumber" class="form-control"
                                   ng-model="trip.clientCellNumber" placeholder="Client Cell Number" ng-minlength="9"
                                   required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Source</label>
                            <input type="text" class="form-control" placeholder="Source" ng-minlength="4" name="source"
                                   ng-model="trip.source" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Destination</label>
                            <input type="text" class="form-control" placeholder="Destination" ng-minlength="4"
                                   name="destination" ng-model="trip.destination" required>
                        </div>
                        <security:authorize access="hasAnyRole('ROLE_admin','ROLE_companyUser')">
                            <div class="form-group col-xs-6">
                                <label class="control-label">Distance</label>
                                <input type="number" class="form-control col-xs-4" placeholder="Distance" minlength="1"
                                       name="distance" ng-model="trip.distance" ng-change="price()" required>
                            </div>
                            <div class="panel col-xs-12 panel-default">
                                <div class="panel-heading">
                                    Price Per Kilo Meter
                                </div>
                                <div class="panel-body colxs-12">
                                    <div class="form-group col-xs-6">
                                        <label class="control-label">Currency</label>
                                        <select class="form-control" ng-options="currency for currency in currencyCodes"
                                                ng-model="trip.price.currency.currencyCode"
                                                required></select>
                                    </div>
                                    <div class="form-group col-xs-6">
                                        <label class="control-label">Amount Per Kilo Meter</label>
                                        <input type="number" class="form-control" placeholder="Price Per Km"
                                               minlength="1"
                                               name="pricePerKm" ng-model="trip.pricePerKm" ng-change="price()"
                                               required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xs-6">
                                <label class="control-label">Total Price {{trip.price.currency.currencyCode}}</label>
                                <input type="number" class="form-control" placeholder="Price" minlength="1" name="price"
                                       ng-model="trip.price.amount" required readonly>
                            </div>
                        </security:authorize>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Company</label>
                            <select class="form-control" name="company" ng-model="company"
                                    ng-change="findBookableDriversAndVehicles()"
                                    ng-options="company.tradingAs for company in sharedState.companyPage.content"
                                    required></select>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Pick Up Time</label>
                            <input type="datetime-local" class="form-control" placeholder="From" minlength="16"
                                   name="from"
                                   ng-change="findBookableDriversAndVehicles()" ng-model="from" id="from" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">To</label>
                            <input type="datetime-local" class="form-control" placeholder="To" minlength="16" name="to"
                                   ng-model="to"
                                   ng-change="findBookableDriversAndVehicles()" id="to" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Vehicle</label>
                            <select class="form-control" placeholder="Vehicle" name="vehicle"
                                    ng-model="vehicle"
                                    ng-options="vehicle.make+' '+vehicle.model+' '+vehicle.licenseNumber for vehicle in bookableVehicles "
                                    required></select>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Driver</label>
                            <select class="form-control" name="driver" ng-model="driver"
                                    ng-options="driver.firstName+' '+driver.surname for driver in bookableDrivers"
                                    required>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default" ng-click="reset();">Reset</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick();">
                        Save
                    </button>
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
            <td>{{trip.price.currency.currencyCode +' '+ trip.pricePerKm}}</td>
            <td>{{trip.price.currency.currencyCode + ' '+ trip.price.amount}}</td>
            <td>{{trip.vehicleName}}</td>
            <td>{{trip.driverName}}</td>
            <td>{{trip.companyName}}</td>
        </tr>
        </tbody>
    </table>
</div>