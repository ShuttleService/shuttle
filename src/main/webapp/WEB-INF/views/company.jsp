<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div ng-controller="CompanyController">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Reference</th>
            <th>Slug</th>
            <th>Trading As</th>
            <th>Full Name</th>
            <th>Description</th>
            <th>VAT Number</th>
            <th>Registration Number</th>
            <th>Agent</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="company in page.content">
            <td>{{company.reference}}</td>
            <td>{{company.slug}}</td>
            <td>{{company.tradingAs}}</td>
            <td>{{company.fullName}}</td>
            <td>{{company.description}}</td>
            <td>{{company.vatNumber}}</td>
            <td>{{company.registrationNumber}}</td>
            <td>{{company.agentName}}</td>
        </tr>
        </tbody>
    </table>
</div>
