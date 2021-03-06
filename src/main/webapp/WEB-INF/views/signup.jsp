<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div layout="column" ng-controller="UserController">
    <p class="md-title">New User Sign Up Form</p>
    <form name="formHolder.addForm" layout="column" layout-padding class="md-inline-form">
        <input type="hidden" name="userName" ng-minlength="6" ng-model="user.email" required readonly>
        <div layout-padding layout-gt-sm="row" flex>
            <md-input-container class="md-block" flex>
                <label>First Name</label>
                <input type="text" ng-minlength="1" ng-model="user.firstName" required>
            </md-input-container>
            <md-input-container class="md-block" flex>
                <label>Surname</label>
                <input type="text" ng-minlength="1" ng-model="user.surname" required>
            </md-input-container>
            <md-input-container flex>
                <label>Cell Number</label>
                <input type="tel" ng-minlength="10" name="cellNumber" ng-model="user.cellNumber" required>
            </md-input-container>
        </div>
        <div layout-padding layout-gt-sm="row">
            <md-input-container flex>
                <label>Email</label>
                <input type="email" name="email" ng-minlength="6" ng-model="user.email" required>
            </md-input-container>
            <label class="md-warn" ng-show="user.password !== formHolder.confirmPassword">
                Password And Confirm Password Must Match.</label>
            <md-input-container flex>
                <label>Password</label>
                <input type="password" ng-minlength="6" name="password" ng-model="user.password" required>
            </md-input-container>
            <md-input-container flex>
                <label>Confirm Password</label>
                <input type="password" ng-minlength="6" name="confirmPassword"
                       ng-model="formHolder.confirmPassword" required>
            </md-input-container>
        </div>
        <div layout-padding layout-gt-sm="row" flex>
            <security:authorize access="hasAnyRole('ROLE_admin')">
                <md-input-container flex>
                    <label>Role</label>
                    <md-select name="authorities" ng-model="user.authority" required>
                        <md-option ng-value="role.role" ng-repeat="role in sharedState.roles">{{role.role}}</md-option>
                    </md-select>
                </md-input-container>
                <md-input-container flex>
                    <label>Company</label>
                    <md-select name="company" ng-model="user.company">
                        <md-option ng-value="company.tradingAs" ng-repeat="company in sharedState.companyPage.content">
                            {{company.tradingAs}}
                        </md-option>
                    </md-select>
                </md-input-container>
            </security:authorize>
            <md-datepicker type="date" md-placeholder="Date Of Birth" flex
                           name="dateOfBirth" ng-model="user.dateOfBirth" required></md-datepicker>
        </div>
        <div layout-padding layout="row" flex>
            <md-input-container flex>
                <label>Street Address</label>
                <input type="text" ng-minlength="2"
                       name="streetAddress" ng-model="user.streetAddress" required>
            </md-input-container>
        </div>
        <div layout-padding layout-gt-sm="row" flex>
            <md-input-container flex>
                <label>Suburb</label>
                <input type="text" ng-minlength="1" name="suburb" ng-model="user.suburb" required>
            </md-input-container>
            <md-input-container flex>
                <label>City/Town</label>
                <input type="text" ng-minlength="1" name="town" ng-model="user.town" required>
            </md-input-container>
            <md-input-container flex>
                <label>Province/State</label>
                <input type="text" ng-minlength="1" name="province" ng-model="user.province" required>
            </md-input-container>
        </div>
        <div flex layout-padding layout-gt-sm="row">
            <md-input-container flex>
                <label>Postal Code</label>
                <input type="number" ng-minlength="2" name="postalCode" ng-model="user.postalCode" required>
            </md-input-container>
            <md-input-container flex>
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
        <md-button type="button" ng-disabled="false" ng-click="saveClick()">Save
        </md-button>
    </div>
</div>