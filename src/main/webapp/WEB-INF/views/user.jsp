<div ng-controller="UserController">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Reference</th>
            <th>First Name</th>
            <th>Surname</th>
            <th>User Name</th>
            <th>Email</th>
            <th>Cellphone Number</th>
            <th>Date Of Birth</th>
            <th>Street Address</th>
            <th>Suburb</th>
            <th>City / Town</th>
            <th>Postal Code</th>
            <th>Province</th>
            <th>Company</th>
            <th>Role</th>
        </tr>
        </thead>
        <tr ng-repeat="user in page.content">
            <td>{{user.reference}}</td>
            <td>{{user.firstName}}</td>
            <td>{{user.surname}}</td>
            <td>{{user.email}}</td>
            <td>{{user.email}}</td>
            <td>{{user.cellNumber}}</td>
            <td>{{user.dateOfBirth}}</td>
            <td>{{user.streetAddress}}</td>
            <td>{{user.suburb}}</td>
            <td>{{user.town}}</td>
            <td>{{user.postalCode}}</td>
            <td>{{user.province}}</td>
            <td>{{user.companyName}}</td>
            <td>{{user.authorities[0].role}}</td>
        </tr>
    </table>
</div>