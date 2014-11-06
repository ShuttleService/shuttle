<div ng-controller="UserController">
    <table class="table table-striped table-hover">
        <thead>
        <th>Id</th>
        <th>User Name</th>
        <th>Password</th>
        <th>First Name</th>
        <th>Email</th>
        <th>Cell Number</th>
        <th>Date Of Birth</th>
        <th>Street Address</th>
        <th>Surburb</th>
        <th>City / Town</th>
        <th>Postal Code</th>
        <th>Province</th>
        </thead>
        <tr ng-repeat="user in page.content">
            <td>{{user.id}}</td>
            <td>{{user.userName}}</td>
            <td>{{user.password}}</td>
            <td>{{user.firstName}}</td>
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