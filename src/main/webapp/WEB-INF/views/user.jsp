<div ng-controller="UserController">
    <ng-include src="'resources/template/userAdd.jsp'"></ng-include>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Surname</th>
            <th>User Name</th>
            <th>Password</th>
            <th>Email</th>
            <th>Cellphone Number</th>
            <th>Date Of Birth</th>
            <th>Street Address</th>
            <th>Surburb</th>
            <th>City / Town</th>
            <th>Postal Code</th>
            <th>Province</th>
        </tr>
        </thead>
        <tr ng-repeat="user in page.content">
            <td>{{user.firstName}}</td>
            <td>{{user.surname}}</td>
            <td>{{user.email}}</td>
            <td>{{user.password}}</td>
            <td>{{user.email}}</td>
            <td>{{user.cellNumber}}</td>
            <td>{{user.dateOfBirth}}</td>
            <td>{{user.streetAddress}}</td>
            <td>{{user.surburb}}</td>
            <td>{{user.town}}</td>
            <td>{{user.postalCode}}</td>
            <td>{{user.province}}</td>
        </tr>
    </table>
</div>