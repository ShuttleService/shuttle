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
                    <form name="addForm">
                        <md-input-container>
                            <label>First Name</label>
                            <input type="text" minlength="2" ng-model="driver.firstName" name="firstName" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Surname</label>
                            <input type="text"  ng-model="driver.surname" name="surname" minlength="2" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Email Address</label>
                            <input type="email" minlength="6" ng-model="driver.email" name="email" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Cellphone Number</label>
                            <input type="number" ng-minlength="9" name="cellNumber" ng-model="driver.cellPhone" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Company</label>
                            <select name="company" ng-model="company"
                                       ng-options="company.tradingAs for company in sharedState.companyPage.content"
                                       required></select>
                        </md-input-container>
                        <md-input-container>
                            <label>Driver's License Class</label>
                            <input type="text" minlength="1" maxlength="3" name="driversLicenseClass"
                                   ng-model="driver.driversLicenseClass" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Driver's License Number</label>
                            <input type="text" minlength="4" name="driversLicenseNumber" ng-model="driver.driversLicenseNumber" required>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" class="btn btn-default" ng-click="reset()">Reset</md-button>
                    <md-button type="button" class="btn btn-default" data-dismiss="modal">Close</md-button>
                    <md-button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">
                        Save
                    </md-button>
                </div>
            </div>

        </div>
    </div>
    <div>
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th>Reference</th>
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
                <td>{{driver.reference}}</td>
                <td>{{driver.firstName}}</td>
                <td>{{driver.surname}}</td>
                <td>{{driver.email}}</td>
                <td>{{driver.driversLicenseNumber}}</td>
                <td>{{driver.driversLicenseClass}}</td>
                <td>{{driver.companyName}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>