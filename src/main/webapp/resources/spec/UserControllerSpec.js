/**
 * Created by zorodzayi on 14/10/22.
 */
describe('Testing The User Controller', function () {

    var $scope;
    var UserService;
    var FormSubmissionUtilService;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope, _UserService_, $controller,_FormSubmissionUtilService_) {

        $scope = $rootScope.$new();
        UserService = _UserService_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;

        expect($scope).toBeDefined();
        expect(UserService).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();

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

    it('Should Have a user on the $scope at init',function(){

        expect($scope.user).toBeDefined();
    });

    it('Should Call The User FormSubmissionUtil saveClick with addForm', function () {

        spyOn(FormSubmissionUtilService,'canSave').andReturn(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();

        expect(actual).toBeTruthy();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });
});
