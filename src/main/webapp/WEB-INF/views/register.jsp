<div>
    <div>
        <h4 class="md-title">New User Sign Up Form</h4>
    </div>
    <form name="formHolder.addForm" layout-align="center center" layout="column">
        <div layout="row">
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

            <label class="md-warn" ng-show="user.password !== formHolder.confirmPassword">
                Password And Confirm Password Must Match.</label>
            <md-input-container>
                <label>Password</label>
                <input type="password" ng-minlength="6" name="password" ng-model="user.password" required>
            </md-input-container>
            <md-input-container>
                <label>Confirm Password</label>
                <input type="password" ng-minlength="6" name="confirmPassword"
                       ng-model="formHolder.confirmPassword" required>
            </md-input-container>
        </div>
        <div layout="row">
            <security:authorize access="hasAnyRole('ROLE_admin')">
                <md-input-container>
                    <label>Role</label>
                    <md-select name="authorities" ng-model="user.authority" required>
                        <md-option ng-value="role.role" ng-repeat="role in sharedState.roles">{{role.role}}</md-option>
                    </md-select>
                </md-input-container>
                <md-input-container>
                    <label>Company</label>
                    <md-select name="company" ng-model="user.company">
                        <md-option ng-value="company.tradingAs" ng-repeat="company in sharedState.companyPage.content">
                            {{company.tradingAs}}
                        </md-option>
                    </md-select>
                </md-input-container>
            </security:authorize>
        </div>
        <div layout="row">
            <md-input-container>
                <label>Cell Number</label>
                <input type="tel" ng-minlength="10" name="cellNumber" ng-model="user.cellNumber" required>
            </md-input-container>

            <md-datepicker type="date" placeholder="Date Of Birth"
                           name="dateOfBirth" ng-model="user.dateOfBirth" required></md-datepicker>

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
        </div>
        <div layout="row">
            <md-input-container>
                <label>Province/State</label>
                <input type="text" ng-minlength="1" name="province" ng-model="user.province" required>
            </md-input-container>
            <md-input-container>
                <label>Postal Code</label>
                <input type="number" ng-minlength="2" name="postalCode" ng-model="user.postalCode" required>
            </md-input-container>
            <md-input-container>
                <label>Country</label>
                <md-select ng-model="user.country">
                    <md-option ng-value="country.code" ng-repeat="country in countries">{{country.name}}
                    </md-option>
                </md-select>
            </md-input-container>
        </div>
    </form>
    <div>
        <md-button type="reset" ng-click="reset()">Reset</md-button>
        <md-button type="button" data-dismiss="modal">Close</md-button>
        <md-button type="button" ng-disabled="false" ng-click="saveClick()">Save
        </md-button>
    </div>
</div>
</div>
