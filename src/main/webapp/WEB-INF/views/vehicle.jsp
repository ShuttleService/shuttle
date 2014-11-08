<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div ng-controller="VehicleController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Vehicle</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal well col-xs-12">
                        <div class="form-group col-xs-6">
                            <label class="control-label">Make</label>
                            <input type="text" class="form-control" placeholder="Make" ng-model="vehicle.make">
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Model</label>
                            <input type="text" class="form-control" placeholder="Model" ng-model="vehicle.model">
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Year Model</label>
                            <input type="number" class="form-control" placeholder="Year Model" ng-model="vehicle.year">
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Type</label>
                            <input type="text" class="form-control" placeholder="Type" ng-model="vehicle.type">
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Capacity</label>
                            <input type="number" class="form-control" placeholder="Capacity" ng-model="vehicle.seats">
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Number Plate</label>
                            <input type="text" class="form-control" placeholder="Number Plate" ng-model="vehicle.licenseNumber">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default" ng-click="reset()">Reset</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-click="saveClick();">Save</button>
                </div>
            </div>
        </div>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <th>License Number</th>
        <th>Make</th>
        <th>Model</th>
        <th>Type</th>
        <th>Year</th>
        <th>Seats</th>
        </thead>
        <tbody>
        <tr ng-repeat="vehicle in page.content">
            <td>{{vehicle.licenseNumber}}</td>
            <td>{{vehicle.make}}</td>
            <td>{{vehicle.model}}</td>
            <td>{{vehicle.type}}</td>
            <td>{{vehicle.year}}</td>
            <td>{{vehicle.seats}}</td>
        </tr>
        </tbody>
    </table>
</div>