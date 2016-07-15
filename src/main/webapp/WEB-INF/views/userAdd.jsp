<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">New User Sign Up Form</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <ng-form class="form-horizontal well col-xs-12" name="formHolder.addForm">

                    <md-input-container>
                        <label>First Name</label>
                        <input type="text" ng-minlength="1" ng-model="user.firstName" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Surname</label>
                        <input type="text" ng-minlength="1" ng-model="user.surname" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Email</label>
                        <input type="email" name="email" ng-minlength="6" ng-model="user.email" required>
                    </md-input-container>
                    <md-input-container>
                        <label>User Name</label>
                        <input type="text" name="userName" ng-minlength="6" ng-model="user.email"
                               required readonly>
                    </md-input-container>

                    <label class="label label-danger col-xs-11" ng-show="user.password !== formHolder.confirmPassword">Password
                        And Confirm Password Must Match.</label>

                    <md-input-container>
                        <label>Password</label>
                        <input type="password" ng-minlength="6" name="password" ng-model="user.password" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Confirm Password</label>
                        <input type="password" ng-minlength="6" name="confirmPassword"
                               ng-model="formHolder.confirmPassword" required>
                    </md-input-container>
                    <security:authorize access="hasAnyRole('ROLE_admin')">
                        <md-input-container>
                            <label>Role</label>
                            <select name="authorities" ng-model="user.authority"
                                    ng-options="role.role as role.role for role in sharedState.roles"
                                    required></select>
                        </md-input-container>
                        <md-input-container>
                            <label>Company</label>
                            <select name="company" ng-model="user.company"
                                    ng-options="company.tradingAs for company in sharedState.companyPage.content"></select>
                        </md-input-container>
                    </security:authorize>
                    <md-input-container>
                        <label>Cell Number</label>
                        <input type="tel" ng-minlength="10" name="cellNumber" ng-model="user.cellNumber" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Date Of Birth</label>
                        <md-datepicker type="date" ng-minlength="10"
                                        name="dateOfBirth" ng-model="user.dateOfBirth" required/>
                    </md-input-container>
                    </md-input-container>
                    <md-input-container>
                        <label>Street Address</label>
                        <input type="text" ng-minlength="2"
                               name="streetAddress" ng-model="user.streetAddress" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Suburb</label>
                        <input type="text" ng-minlength="1" name="suburb" ng-model="user.suburb" required>
                    </md-input-container>
                    <md-input-container>
                        <label>City/Town</label>
                        <input type="text" ng-minlength="1" name="town" ng-model="user.town" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Province/State</label>
                        <input type="text" ng-minlength="1" name="province" ng-model="user.province" required>
                    </md-input-container>
                    <md-input-container>
                        <label>Postal Code</label>
                        <input type="number" ng-minlength="2" name="postalCode" ng-model="user.postalCode" required>
                    </md-input-container>
                </ng-form>
            </div>
            <div class="modal-footer">
                <md-button type="reset" ng-click="reset()">Reset</md-button>
                <md-button type="button" data-dismiss="modal">Close</md-button>
                <md-button type="button" ng-disabled="false" ng-click="saveClick()">Save
                </md-button>
            </div>
        </div>
    </div>
</div>