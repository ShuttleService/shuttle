<div ng-controller="DriverController">
    <table class="table table-striped table-hover">
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