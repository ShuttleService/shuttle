/**
 * Created by zorodzayi on 14/10/22.
 */
describe('Testing The User Controller', function () {

    var $scope;
    var UserService;
    var FormSubmissionUtilService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope,$controller,_UserService_,_FormSubmissionUtilService_,_RESULT_SIZE_) {

        $scope = $rootScope.$new();
        UserService = _UserService_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($scope).toBeDefined();
        expect(UserService).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
        var controller = $controller('UserController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();
    }));

    it('Should Call The UserService save if $scope.new is true', function () {

        $scope.new = true;
        var user = {userName: 'Test UserName To POST '};
        $scope.user = user;
        var savedUser = {userName: 'Test UserName Returned From POSTED'};
        spyOn(UserService, 'save').andReturn(savedUser);

        expect($scope.saveClick).toBeDefined();

        $scope.saveClick();

        expect(UserService.save).toHaveBeenCalledWith(user);
        expect($scope.user.userName).toEqual(savedUser.userName);

    });

    it('Should Have a user on the $scope at init', function () {

        expect($scope.user).toBeDefined();
    });

    it('Should Call The User FormSubmissionUtil saveClick with addForm', function () {

        spyOn(FormSubmissionUtilService, 'canSave').andReturn(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();

        expect(actual).toBeTruthy();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('Should Call The Service get With The Given Skip And Limit', function () {
        var skip = 0;
        var limit = 10;
        var params = {skip:skip,limit:limit};
        var page   = {size:limit};
        spyOn(UserService,'get').andReturn(page);
        $scope.skip = skip;
        $scope.limit = limit;
        expect($scope.list).toBeDefined();
        $scope.list();
        expect(UserService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Set Skip And Limit On The Scope On Init',function(){
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });
});
