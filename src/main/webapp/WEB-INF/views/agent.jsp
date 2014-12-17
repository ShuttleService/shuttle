<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container" ng-controller="AgentController">
  <div class="modal fade" id="add">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">Adding An Agent</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form class="form-horizontal well col-xs-12" name="addForm">
            <div class="form-group col-xs-6">
              <label class="control-label">Agent Full Name</label>
              <input type="text" class="form-control" ng-minlength="2" name="fullName"
                     placeholder="Agent Full Name" ng-model="agent.fullName" required>
            </div>
            <div class="form-group col-xs-6">
              <label class="control-label">Trading As</label>
              <input type="text" class="form-control" ng-minlength="2" name="tradingAs"
                     placeholder="Trading As" ng-model="agent.tradingAs" required>
            </div>
            <div class="form-group col-xs-6">
              <label class="control-label">Slug</label>
              <input type="text" class="form-control" ng-minlength="1" name="slug" placeholder="Slug"
                     ng-model="agent.slug" required>
            </div>
            <div class="form-group col-xs-6">
              <label class="control-label">Registration Number</label>
              <input type="text" class="form-control" ng-minlength="1" name="registrationNumber"
                     placeholder="Registration Number" ng-model="agent.registrationNumber" required>
            </div>
            <div class="form-group col-xs-6">
              <label class="control-label">VAT Number</label>
              <input type="text" class="form-control" ng-minlength="1" name="vatNumber"
                     placeholder="VAT Number" ng-model="agent.vatNumber" required>
            </div>
            <div class="form-group col-xs-6">
              <label class="control-label">Logo</label>
              <input type="text" class="form-control" name="logo" placeholder="Logo"
                     ng-model="agent.logo">
            </div>
            <div class="form-group col-xs-12">
              <label class="control-label">Description</label>
                            <textarea class="col-xs-12" ng-minlength="2" data-ng-model="agent.description"
                                      placeholder="Description" required></textarea>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="reset" class="btn btn-default" ng-click="reset()">Reset</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="col-xs-12 text-center">
    <table class="table table-striped table-hover">
      <thead>
      <tr>
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
</div>
