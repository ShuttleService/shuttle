<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New User Sign Up Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <ng-form class="form-horizontal well col-xs-12" name="addForm">
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="First Name" ng-minlength="4"
                               ng-model="user.firstName" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Surname" ng-minlength="4"
                               ng-model="user.surname" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="email" class="form-control" placeholder="Email" name="email" ng-minlength="4"
                               ng-model="user.email" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" placeholder="User Name" class="form-control" name="userName"
                               ng-minlength="6" ng-model="user.userName" ng-required>
                    </div>
                    <div class="well well-lg text-center" ng-show="user.password !== confirmPassword">
                        Passwords Do Not Match.
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" class="form-control" placeholder="Password" ng-minlength="6"
                               name="password" ng-model="user.password" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="password" placeholder="Confirm Password" class="form-control" ng-minlength="6"
                               name="confirmPassword" ng-model="confirmPassword" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="tel" class="form-control" placeholder="Cell Number" ng-minlength="10"
                               name="cellNumber" ng-model="user.cellNumber" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="date" class="form-control" placeholder="Date Of Birth" ng-minlength="10"
                               name="dateOfBirth" ng-model="user.dateOfBirth" ng-required>
                    </div>
                    <div class="form-group col-xs-11">
                        <textarea class="form-control" placeholder="Street Address" ng-minlength="2"
                                  name="streetAddress" ng-model="user.streetAddress" ng-required></textarea>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="Surburb" ng-minlength="2"
                               name="surburb" ng-model="user.surburb" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control" placeholder="City/Town" ng-minlength="2" name="town"
                               ng-model="user.town" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="text" class="form-control " placeholder="Province" ng-minlength="2"
                               name="province" ng-model="user.province" ng-required>
                    </div>
                    <div class="form-group col-xs-6">
                        <input type="number" class="form-control" placeholder="Postal Code" ng-minlength="4"
                               name="postalCode" ng-model="user.postalCode" ng-required>
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
