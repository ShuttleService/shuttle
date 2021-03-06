<div flex layout="column" ng-controller="ReviewController">
    <div>
        <h4 class="md-title">Adding A Review</h4>
        <form name="addForm" layout="column">
            <md-input-container flex>
                <label>Reviewee</label>
                <input name="id" type="text" ng-minlength="1" ng-model="review.id" required>
            </md-input-container>
            <md-input-container flex>
                <label>Review Text</label>
                <textarea name="text" ng-minlength="5" ng-model="review.text" required>
                            </textarea>
            </md-input-container>
        </form>
        <div layout="row">
            <md-button type="reset" ng-click="reset();">Reset</md-button>
            <md-button type="button" ng-disabled="!canSave()" ng-click="saveClick()">Save
            </md-button>
        </div>
    </div>
</div>