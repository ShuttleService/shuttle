<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div ng-controller="DriverController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Driver</h4>
                    <button type="button" data-dismiss="modal" class="close">&times;</button>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal well col-xs-12" name="addForm">
                        <div class="form-group col-xs-6">
                            <label class="control-label">First Name</label>
                            <input type="text" placeholder="First Name" minlength="2" ng-model="driver.firstName"
                                   name="firstName" class="form-control" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Surname</label>
                            <input type="text" placeholder="Surname" ng-model="driver.surname" name="surname"
                                   minlength="2"
                                   class="form-control" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Email Address</label>
                            <input type="email" class="form-control" placeholder="Email Address" minlength="6"
                                   ng-model="driver.email" name="email" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Cellphone Number</label>
                            <input type="number" class="form-control" placeholder="Cellphone Number" ng-minlength="9"
                                   name="cellNumber" ng-model="driver.cellPhone" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Driver's License Class</label>
                            <input type="text" class="form-control" minlength="1" maxlength="3"
                                   name="driversLicenseClass"
                                   ng-model="driver.driversLicenseClass"
                                   placeholder="Drivers License Class" required>
                        </div>
                        <div class="form-group col-xs-6">
                            <label class="control-label">Driver's License Number</label>
                            <input type="text" class="form-control" minlength="4" name="driversLicenseNumber"
                                   ng-model="driver.driversLicenseNumber" placeholder="Drivers License Number"
                                   required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default" ng-click="reset()">Reset</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save
                    </button>
                </div>
            </div>

        </div>
    </div>
    <div>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th>First Name</th>
                <th>Surname</th>
                <th>Email Address</th>
                <th>Driver's License Number</th>
                <th>Driver's License Class</th>
                <th>Company</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="driver in page.content">
                <td>{{driver.firstName}}</td>
                <td>{{driver.surname}}</td>
                <td>{{driver.email}}</td>
                <td>{{driver.driversLicenseNumber}}</td>
                <td>{{driver.driversLicenseClass}}</td>
                <td>{{driver.company.name}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>