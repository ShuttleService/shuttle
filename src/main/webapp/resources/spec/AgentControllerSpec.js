/**
 * Created by zorodzayi on 14/12/17.
 */
describe('Testing The Agent Controller', function () {
    var AgentService,FormSubmissionUtilService,$scope, RESULT_SIZE;

    beforeEach(module('controllers'));
    beforeEach(inject(function (_AgentService_,_FormSubmissionUtilService_,$controller, $rootScope, _RESULT_SIZE_) {
        $scope = $rootScope.$new();
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        expect(FormSubmissionUtilService).toBeDefined();
        expect($scope).toBeDefined();
        RESULT_SIZE = _RESULT_SIZE_;

        var controller = $controller('AgentController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();
        AgentService = _AgentService_;
        expect(AgentService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
    }));

    it('Should Have An Agent On The Scope', function () {
        expect($scope.agent).toBeDefined();
    });



    it('Should Have skip and limit defined with the default values',function(){
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Call Service Get And Set The Returned Page On The $scope', function () {
        var page = {content: ['Test Content To Be Returned From Service.get']};
        var params = {skip: 0, limit: RESULT_SIZE};
        $scope.skip = params.skip;
        $scope.limit = params.limit;

        spyOn(AgentService, 'get').andReturn(page);
        expect($scope.list).toBeDefined();
        $scope.list();
        expect(AgentService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Call FormSubmissionUtils canSave',function(){
        $scope.addForm = {};
        spyOn(FormSubmissionUtilService,'canSave').andReturn(false);
        expect($scope.canSave).toBeDefined();
        var actual = $scope.canSave();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
        expect(actual).toEqual(false);
    });

    it('Should Call Service Save With The Agent On The $scope', function () {

        var agent = {name:'Test '};
        $scope.agent = agent;
        spyOn(AgentService,'save').andReturn(agent);

        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();
        expect(AgentService.save).toHaveBeenCalledWith(agent,jasmine.any(Function));
        expect($scope.recentlySaved).toEqual(agent);
    });

});