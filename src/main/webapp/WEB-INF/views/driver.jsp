<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div ng-controller="DriverController">
    <table>
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