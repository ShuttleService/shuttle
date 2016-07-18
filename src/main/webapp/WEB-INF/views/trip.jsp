<table ng-controller="TripController" class="table table-striped table-hover">
    <thead>
    <tr>
        <th>Reference</th>
        <th>Client Name</th>
        <th>Cell Number</th>
        <th>Source</th>
        <th>Destination</th>
        <th>Distance</th>
        <th>Price Per Km</th>
        <th>Total Price</th>
        <th>Vehicle</th>
        <th>Driver</th>
        <th>Company</th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="trip in page.content">
        <td>{{trip.reference}}</td>
        <td>{{trip.clientName}}</td>
        <td>{{trip.clientCellNumber}}</td>
        <td>{{trip.source}}</td>
        <td>{{trip.destination}}</td>
        <td>{{trip.distance}}</td>
        <td>{{trip.price.currency.currencyCode +' ' }} {{trip.pricePerKm | number:2}}</td>
        <td>{{trip.price.currency.currencyCode + ' '}} {{trip.price.amount | number:2}}</td>
        <td>{{trip.vehicleName}}</td>
        <td>{{trip.driverName}}</td>
        <td>{{trip.companyName}}</td>
    </tr>
    </tbody>
</table>