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


    it('Calls The UserService save if $scope.new is true', function () {

        $scope.new = true;
        var user = {userName: 'Test UserName To POST '};
        $scope.user = user;
        spyOn(UserService, 'save');
        spyOn($scope, 'list');
        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();
        expect(UserService.save).toHaveBeenCalledWith(user, jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Has a user on the $scope at init', function () {

        expect($scope.user).toBeDefined();
    });

    it('Calls The User FormSubmissionUtil saveClick with addForm', function () {

        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();

        expect(actual).toBeTruthy();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('Calls The Service get With The Given Skip And Limit', function () {
        var skip = 0;
        var limit = 100;
        var params = {skip: skip, limit: limit};
        var page = {size: limit};
        spyOn(UserService, 'get').and.returnValue(page);
        $scope.range = {};
        $scope.range.skip = skip;
        $scope.range.limit = limit;
        expect($scope.list).toBeDefined();
        $scope.list();
        expect(UserService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Sets Skip And Limit On The Scope On Init', function () {
        expect($scope.range.skip).toEqual(0);
        expect($scope.range.limit).toEqual(RESULT_SIZE);
    });

    it('Reverts The User To It\'s Prestine State', function () {
        var confirmPassword = $scope.confirmPassword;
        var prestine = $scope.user;

        expect($scope.reset).toBeDefined();
        $scope.user = {name: 'Test User To Be Reverted'};
        $scope.formHolder.confirmPassword = 'Test Confirm Password To Be Reverted';
        $scope.reset();
        expect($scope.user).toEqual(prestine);
        expect($scope.formHolder.confirmPassword).toEqual(confirmPassword);
    });

    it('saveClick Sets The username To Be The Same As Email ', function () {
        var email = "Test Email To Be Copied To The User Name";
        $scope.user.email = email;
        $scope.saveClick();
        expect($scope.user.username).toEqual(email);
    });

    it("saveClick Sets The user.companyName and user.companyId from the values on the selected Company", function () {

        var company = {
            tradingAs: 'Test Company Trading Name To Be Set On The User.CompanyName',
            reference: 'Test Company Id To Be Set On The User.CompanyId'
        };
        $scope.user.company = company;
        $scope.saveClick();
        expect($scope.user.companyId).toEqual(company.reference);
        expect($scope.user.companyName).toEqual(company.tradingAs);
    });

    it('saveClick  Sets The Role As The List Of The Selected Role', function () {
        var role = 'Test Role To Be Set As A Single Element In An Array Of Authorities.';
        $scope.user.authority = role;
        $scope.saveClick();
        expect($scope.user.authorities).toEqual([role]);
    });


    it("saveClick Still works if there is no company set and user.companyId", function () {

        $scope.saveClick();
        expect($scope.user.companyId).toBeUndefined();
        expect($scope.user.companyName).toBeUndefined();
    });

    it('Sets The Company Name And Company Id Based On the Values On The Company', function () {
        var companyName = 'Test Company Name To Be Set On The user.companyName';
        var reference = 'Test Company Id To Be Set On user.companyId';

        $scope.user.company = {tradingAs: companyName, reference: reference};
        $scope.saveClick();
        expect($scope.user.companyName).toEqual(companyName);
        expect($scope.user.companyId).toEqual(reference);
    });

    it('Is Able To Save When The password And Password Retype Are The Same.', function () {
        var samePassword = 'Test Password The Same For Both Password And Password Retype';
        $scope.user.password = samePassword;
        $scope.formHolder.confirmPassword = samePassword;
        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(true);

        var actual = $scope.canSave();

        expect(actual).toBe(true);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalled();
    });
    it('Is Unable To Save When The Password And Password Retype Are Different', function () {
        $scope.user.password = 'Test Password Different From Password Retype';
        $scope.formHolder.confirmPassword = 'Test Retype Password Different From Password';

        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(true);

        var actual = $scope.canSave();

        expect(actual).toBe(false);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalled();
    });
    it('Calls The update (with parameters on the scope i.e username,currentPassword and newPassword) To Change The Password ', function () {

        $scope.username = 'Test Username';
        $scope.currentPassword = 'Test Current Password';
        $scope.newPassword = 'Test New Password';
        spyOn(UserService, 'update');

        expect($scope.changePassword).toBeDefined();

        $scope.changePassword();

        expect(UserService.update).toHaveBeenCalledWith({
            username: $scope.username,
            currentPassword: $scope.currentPassword,
            newPassword: $scope.newPassword
        });
    });

    it('Calls FormSubmissionUtil.canSave With Change Password Form', function () {
        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(true);
        $scope.canChangePasswordForm = {};
        expect($scope.canChangePassword).toBeDefined();

        var actual = $scope.canChangePassword();

        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.changePasswordForm);
        expect(actual).toBeTruthy();
    });

});
