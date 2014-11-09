<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                        <div class="form-group col-xs-6">
                            <label class="control-label">Distance</label>
                            <input type="number" class="form-control col-xs-4" placeholder="Distance" minlength="1"
                                   name="distance" ng-model="trip.distance" ng-change="price()" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Price Per Km</label>
                            <input type="number" class="form-control col-xs-4" placeholder="Price Per Km" minlength="1"
                                   name="pricePerKm" ng-model="trip.pricePerKm" ng-change="price()" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Price</label>
                            <input type="number" class="form-control" placeholder="Price" minlength="1" name="price"
                                   ng-model="trip.price" required readonly>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Vehicle</label>
                            <input type="text" class="form-control" placeholder="Vehicle" minlength="4" name="vehicle"
                                   ng-model="trip.vehicle" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Driver</label>
                            <input type="text" placeholder="Driver" class="form-control" minlength="1" name="driver"
                                   ng-model="trip.driver" required>
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
            <th>Client Name</th>
            <th>Cell Number</th>
            <th>Source</th>
            <th>Destination</th>
            <th>Distance</th>
            <th>Price Per Km</th>
            <th>Total Price</th>
            <th>Vehicle</th>
            <th>Driver</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="trip in page.content">
            <td>{{trip.clientName}}</td>
            <td>{{trip.clientCellNumber}}</td>
            <td>{{trip.source}}</td>
            <td>{{trip.destination}}</td>
            <td>{{trip.distance}}</td>
            <td>{{trip.pricePerKm | currency}}</td>
            <td>{{trip.price | currency}}</td>
            <td>{{trip.vehicle}}</td>
            <td>{{trip.driver}}</td>
        </tr>
        </tbody>
    </table>
</div>