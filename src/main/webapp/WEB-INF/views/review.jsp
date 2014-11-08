<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div ng-controller="ReviewController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Review</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal well" name="addForm">
                        <div class="form-group">
                            <input class="form-control" name="id" type="text" placeholder="Object Id" ng-minlength="1"
                                   ng-model="review.id" ng-required>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" name="text" placeholder="Review Text" ng-minlength="1"
                                      ng-model="review.text" ng-required>
                            </textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-default" ng-click="reset();">Reset</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" ng-disabled="!canSave()" ng-click="saveClick()">Save
                    </button>
                </div>
            </div>
        </div>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <th>For</th>
        <th>Reviews</th>
        </thead>
        <tbody>
        <tr ng-repeat="review in page.content">
            <td>{{review.name}}</td>
            <td>{{review.reviews}}</td>
        </tr>
        </tbody>
    </table>
</div>