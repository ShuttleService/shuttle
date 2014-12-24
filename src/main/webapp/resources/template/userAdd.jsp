<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New User Sign Up Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <ng-form class="form-horizontal well col-xs-12" name="formHolder.addForm">

                    <div class="form-group col-xs-6">
                        <label class="control-label">First Name</label>
                        <input type="text" class="form-control" placeholder="First Name" ng-minlength="1"
                               ng-model="user.firstName" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Surname</label>
                        <input type="text" class="form-control" placeholder="Surname" ng-minlength="1"
                               ng-model="user.surname" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Email</label>
                        <input type="email" class="form-control" placeholder="Email" name="email" ng-minlength="6"
                               ng-model="user.email" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">User Name</label>
                        <input type="text" placeholder="User Name" class="form-control" name="userName"
                               ng-minlength="6" ng-model="user.email" required readonly>
                    </div>

                    <label class="label label-danger col-xs-11" ng-show="user.password !== formHolder.confirmPassword">Password
                        And Confirm Password Must Match.</label>

                    <div class="form-group col-xs-6">
                        <label class="control-label">Password</label>
                        <input type="password" class="form-control" placeholder="Password" ng-minlength="6"
                               name="password" ng-model="user.password" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Confirm Password</label>
                        <input type="password" placeholder="Confirm Password" class="form-control" ng-minlength="6"
                               name="confirmPassword" ng-model="formHolder.confirmPassword" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Role</label>
                        <select placeholder="Role" class="form-control"
                               name="authorities" ng-model="user.authority"  ng-options="role.role for role in roles" required></select>
                    </div>
                  <div class="form-group col-xs-6">
                    <label class="control-label">Company</label>
                    <select class="form-control" name="company" ng-model="user.company" ng-options="company.tradingAs for company in sharedState.companyPage.content"></select>
                  </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Cell Number</label>
                        <input type="tel" class="form-control" placeholder="Cell Number" ng-minlength="10"
                               name="cellNumber" ng-model="user.cellNumber" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Date Of Birth</label>
                        <input type="date" class="form-control" placeholder="yyyy-mm-dd" ng-minlength="10"
                               name="dateOfBirth" ng-model="user.dateOfBirth" required>
                    </div>
                    <div class="form-group col-xs-6 text-center">
                        <label class="control-label">Street Address</label>
                        <input type="text" class="form-control" placeholder="Street Address" ng-minlength="2" name="streetAddress" ng-model="user.streetAddress" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Suburb</label>
                        <input type="text" class="form-control" placeholder="Suburb" ng-minlength="1"
                               name="suburb" ng-model="user.suburb" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">City/Town</label>
                        <input type="text" class="form-control" placeholder="City/Town" ng-minlength="1" name="town"
                               ng-model="user.town" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Province/State</label>
                        <input type="text" class="form-control" placeholder="Province/State" ng-minlength="1"
                               name="province" ng-model="user.province" required>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">Postal Code</label>
                        <input type="number" class="form-control" placeholder="Postal Code" ng-minlength="2"
                               name="postalCode" ng-model="user.postalCode" required>
                    </div>
                </ng-form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default" ng-click="reset()">Reset</button>
                <button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save
                </button>
            </div>
        </div>
    </div>
</div>