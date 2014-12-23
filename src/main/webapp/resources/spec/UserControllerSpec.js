/**
 * Created by zorodzayi on 14/10/22.
 */
describe('Testing The User Controller', function () {

    var $scope, $rootScope;
    var UserService;
    var FormSubmissionUtilService;
    var CompanyService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, $controller, _UserService_, _FormSubmissionUtilService_, _RESULT_SIZE_, _CompanyService_) {
        $rootScope = _$rootScope_;
        expect($rootScope).toBeDefined();
        $scope = $rootScope.$new();
        $scope.user = {};
        $scope.company = {};
        CompanyService = _CompanyService_;
        UserService = _UserService_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($scope).toBeDefined();
        expect(UserService).toBeDefined();
        expect(CompanyService).toBeDefined();
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
        spyOn(UserService, 'get');
        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();
        expect(UserService.save).toHaveBeenCalledWith(user, jasmine.any(Function));
        expect($scope.user.userName).toEqual(savedUser.userName);
        expect(UserService.get).toHaveBeenCalled();
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
        var limit = 100;
        var params = {skip: skip, limit: limit};
        var page = {size: limit};
        spyOn(UserService, 'get').andReturn(page);
        $scope.range = {};
        $scope.range.skip = skip;
        $scope.range.limit = limit;
        expect($scope.list).toBeDefined();
        $scope.list();
        expect(UserService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Set Skip And Limit On The Scope On Init', function () {
        expect($scope.range.skip).toEqual(0);
        expect($scope.range.limit).toEqual(RESULT_SIZE);
    });

    it('Should Revert The User To It\'s Prestine State', function () {
        var confirmPassword = $scope.confirmPassword;
        var prestine = $scope.user;

        expect($scope.reset).toBeDefined();
        $scope.user = {name: 'Test User To Be Reverted'};
        $scope.formHolder.confirmPassword = 'Test Confirm Password To Be Reverted';
        $scope.reset();
        expect($scope.user).toEqual(prestine);
        expect($scope.formHolder.confirmPassword).toEqual(confirmPassword);
    });

    it('saveClick Should Set The username To Be The Same As Email ', function () {

        var email = "Test Email To Be Copied To The User Name";
        $scope.user.email = email;
        $scope.saveClick();
        expect($scope.user.username).toEqual(email);
    });

    it("saveClick Should Set The user.companyName and user.companyId from the values on the selected Company", function () {

        var company = {
            tradingAs: 'Test Company Trading Name To Be Set On The User.CompanyName',
            id: 'Test Company Id To Be Set On The User.CompanyId'
        };
        $scope.user.company = company;
        $scope.saveClick();
        expect($scope.user.companyId).toEqual(company.id);
        expect($scope.user.companyName).toEqual(company.tradingAs);

    });

    it('saveClick Should Set The Role As The List Of The Selected Role',function(){
        var role = 'Test Role To Be Set As A Single Element In An Array Of Authorities.';
        $scope.user.authority = role;
        $scope.saveClick();
        expect($scope.user.authority).toEqual(role);
    });


    it("saveClick Should Still work if there is no company set and user.companyId", function () {

        $scope.saveClick();
        expect($scope.user.companyId).toBeUndefined();
        expect($scope.user.companyName).toBeUndefined();

    });

    it('Should Set The Company Name And Company Id Based On the Values On The Company', function () {
        var companyName = 'Test Company Name To Be Set On The user.companyName';
        var companyId = 'Test Company Id To Be Set On user.companyId';

        $scope.user.company = {tradingAs: companyName, id: companyId};
        $scope.saveClick();
        expect($scope.user.companyName).toEqual(companyName);
        expect($scope.user.companyId).toEqual(companyId);
    });

    it('Should Initialize The List Of All Supported Roles, admin, agent and world ', function () {
        var roles = [{role:'ROLE_admin'},{role:'ROLE_agent'},{role:'ROLE_world'}];
        expect($scope.roles).toBeDefined();
        var actual = $scope.roles;
        expect(actual).toEqual(roles);
    });
});
