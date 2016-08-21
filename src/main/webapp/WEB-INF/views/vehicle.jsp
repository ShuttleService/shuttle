<div ng-controller="VehicleController">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Reference</th>
            <th>Make</th>
            <th>Model</th>
            <th>Type</th>
            <th>Year</th>
            <th>Seats</th>
            <th>License Number</th>
            <th>Company</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="vehicle in page.content">
            <td>{{vehicle.reference}}</td>
            <td>{{vehicle.make}}</td>
            <td>{{vehicle.model}}</td>
            <td>{{vehicle.type}}</td>
            <td>{{vehicle.year}}</td>
            <td>{{vehicle.seats}}</td>
            <td>{{vehicle.licenseNumber}}</td>
            <td>{{vehicle.companyName}}</td>
        </tr>
        </tbody>
    </table>
</div>