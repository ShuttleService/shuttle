<div ng-controller="ReviewController">
    <div class="modal fade" id="add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Adding A Review</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form name="addForm">
                        <md-input-container>
                            <label>Reviewee</label>
                            <input name="id" type="text" ng-minlength="1" ng-model="review.id" required>
                        </md-input-container>
                        <md-input-container>
                            <label>Review Text</label>
                            <textarea name="text" ng-minlength="5" ng-model="review.text" required>
                            </textarea>
                        </md-input-container>
                    </form>
                </div>
                <div class="modal-footer">
                    <md-button type="reset" ng-click="reset();">Reset</md-button>
                    <md-button type="button" ng-disabled="!canSave()" ng-click="saveClick()">Save
                    </md-button>
                </div>
            </div>
        </div>
    </div>
    <table class="table table-striped table-hover">
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
</div>