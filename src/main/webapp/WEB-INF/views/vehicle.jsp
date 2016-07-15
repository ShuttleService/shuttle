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
                    <form name="addForm">
                        <md-input-container>
                            <label>Make</label>
                            <input type="text" name="make" ng-model="vehicle.make" ng-minlength="1" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Model</label>
                            <input type="text" ng-model="vehicle.model" name="model" ng-minlength="1" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Year Model</label>
                            <input type="number" ng-model="vehicle.year" name="year" ng-minlength="4" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Type</label>
                            <select ng-model="vehicle.type" ng-options="type for type in vehicleTypes"
                                    name="type" required>Vehicle Type</select>
                        </md-input-container>
                        <md-input-container>
                            <label>Capacity</label>
                            <input type="number" ng-model="vehicle.seats" name="seats" ng-minlength="1" required>
                        </md-input-container>
                        <md-input-container>
                            <label>License Number</label>
                            <input type="text" ng-model="vehicle.licenseNumber" ng-minlength="1" name="licenseNumber" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Company</label>
                            <select  ng-model="company" name="company"
                                    ng-options="company.tradingAs for company in sharedState.companyPage.content"
                                    required></select>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" class="btn btn-default" ng-click="reset()">Reset</md-button>
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
            <th>Make</th>
            <th>Model</th>
            <th>Type</th>
            <th>Year</th>
            <th>Seats</th>
            <th>License Number</th>
            <th>Company</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="vehicle in page.content">
            <td>{{vehicle.reference}}</td>
            <td>{{vehicle.make}}</td>
            <td>{{vehicle.model}}</td>
            <td>{{vehicle.type}}</td>
            <td>{{vehicle.year}}</td>
            <td>{{vehicle.seats}}</td>
            <td>{{vehicle.licenseNumber}}</td>
            <td>{{vehicle.companyName}}</td>
        </tr>
        </tbody>
    </table>
</div>