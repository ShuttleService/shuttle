<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="add" ng-controller="TripController">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Adding A Trip</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form class="form-horizontal well col-xs-12" name="addForm">
                    <div class="form-group col-xs-6">
                        <input class="form-control" type="text" placeholder="Client Name" ng-minlength="1" name="clientName" ng-model="trip.clientName" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Source" ng-minlength="1"  name="source" ng-model="trip.source" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Destination" ng-minlength="1" name="destination" ng-model="trip.destination" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control col-xs-4" placeholder="Distance" minlength="1" name="distance" ng-model="trip.distance" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control col-xs-4" placeholder="Price Per Km" minlength="1" name="pricePerKm" ng-model="trip.pricePerKm" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control" placeholder="Price" minlength="1" name="price" ng-model="trip.price" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Vehicle" minlength="1" name="vehicle" ng-model="trip.vehicle" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="Driver" class="form-control" minlength="1" name="driver" ng-model="trip.driver" ng-required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default">Reset</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick();">Save</button>
            </div>
        </div>
    </div>
</div>