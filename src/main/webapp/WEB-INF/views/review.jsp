<table class="table table-striped table-hover" ng-controller="ReviewController">
    <thead>
    <tr>
        <th>For</th>
        <th>Reviews</th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="review in page.content">
        <td>{{review.name}}</td>
        <td>{{review.reviews}}</td>
    </tr>
    </tbody>
</table>
