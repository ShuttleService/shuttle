/**
 * Created by reality on 14/10/19.
 */
describe('Company Controller Test', function () {

    var $controller;
    var CompanyService;
    var $scope;
    var FormSubmissionUtilService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope, _$controller_, _CompanyService_, _FormSubmissionUtilService_,_RESULT_SIZE_) {

        $scope = $rootScope.$new();
        CompanyService = _CompanyService_;
        $controller = _$controller_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($scope).toBeDefined();
        expect(CompanyService).toBeDefined();
        expect($controller).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
    }));

    it('Should Return False When addForm is Dirty And Invalid', function () {
        var addForm = {$dirty: true, $valid: false};

        $controller('CompanyController', {
            $scope: $scope
        });

        spyOn(FormSubmissionUtilService, 'canSave').andReturn(false);
        $scope.addForm = addForm;
        expect($scope.canSave).toBeDefined();

        expect($scope.canSave()).toEqual(false);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith(addForm);
    });

    it('Should Return True When addForm is Dirty And Valid ', function () {
        var addForm = {$dirty: true, $valid: true};

        $controller('CompanyController', {
            $scope: $scope
        });

        $scope.addForm = addForm;
        spyOn(FormSubmissionUtilService, 'canSave').andReturn(true);

        expect($scope.canSave()).toEqual(true);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith(addForm);
    });

    it('Should Call Get On Service And Set The Returned Company On The Scope', function () {

        var controller = $controller('CompanyController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();

        expect($scope.get).toBeDefined();

        var company = {name: 'Test Company Spy Name For Get', id: 'Test Company Spy Name For Get'};

        spyOn(CompanyService, 'get').andReturn(company);

        $scope.id = "TestCompanyIdToGet";

        $scope.get($scope.id);

        var params = {id: $scope.id};

        expect(CompanyService.get).toHaveBeenCalledWith(params);
        expect($scope.company).toEqual(company);

    });

    it('Should Have A Company Object On The Scope On Init And Should Default To $scope.new == true', function () {

        $controller('CompanyController', {
            $scope: $scope
        });

        expect($scope.company).toBeDefined();
        expect($scope.new).toEqual(true);
    });

    it('Should Call Service Post On Submit When Creating A Company ', function () {

        $controller('CompanyController', {
            $scope: $scope
        });

        var companyToSave = {name: 'Test Company Name To Be Called With Posted'};
        var savedCompany = {name: 'Test Company Name To Be Returned From After POST'};
        $scope.company = companyToSave;

        $scope.new = true;

        spyOn(CompanyService, 'save').andReturn(savedCompany);
        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();

        expect(CompanyService.save).toHaveBeenCalledWith(companyToSave,jasmine.any(Function));
        expect($scope.recentlyAddedCompany).toEqual(savedCompany);
    });

    it('Should Call Service get with given skip and limit', function () {
        var size = 23;
        $controller('CompanyController', {
            $scope:$scope
        });

        var page = {size:size};

        spyOn(CompanyService,'get').andReturn(page);

        expect($scope.list).toBeDefined();
        var limit = 3;
        var skip = 0;
        $scope.skip = skip;
        $scope.limit = limit;
        var params = {skip:skip,limit:limit};
        $scope.list();
        expect(CompanyService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Set The limit To the Default Result Size, Set Limit  And skip on the scope',function(){
        $controller('CompanyController',{
            $scope:$scope
        });
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Reset The Company On The $scope To The Original One',function(){
        $controller('CompanyController',{
            $scope:$scope
        });

        var prestineCompany = $scope.company;
        $scope.company = {name:'Test Company Name Dirty'};
        expect($scope.reset).toBeDefined();
        $scope.reset();
        expect($scope.company).toEqual(prestineCompany);
    });

});
