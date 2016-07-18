<div ng-controller="AgentController">
    <table class="table-cell">
        <thead>
        <tr>
            <th>Reference</th>
            <th>Slug</th>
            <th>Trading As</th>
            <th>Full Name</th>
            <th>Description</th>
            <th>VAT Number</th>
            <th>Registration Number</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="agent in page.content">
            <td>{{agent.reference}}</td>
            <td>{{agent.slug}}</td>
            <td>{{agent.tradingAs}}</td>
            <td>{{agent.fullName}}</td>
            <td>{{agent.description}}</td>
            <td>{{agent.vatNumber}}</td>
            <td>{{agent.registrationNumber}}</td>
        </tr>
        </tbody>
    </table>
</div>
