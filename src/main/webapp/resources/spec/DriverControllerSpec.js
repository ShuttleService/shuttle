/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Driver Controller', function () {

    var $controller;
    var DriverService;
    var $scope;
    var FormSubmissionUtilService;
    var RESULT_SIZE;
    var CompanyService;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$controller_, _DriverService_,_CompanyService_, $rootScope, _FormSubmissionUtilService_, _RESULT_SIZE_) {

        $controller = _$controller_;
        DriverService = _DriverService_;
        CompanyService = _CompanyService_;
        $scope = $rootScope.$new();
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($controller).toBeDefined();
        expect(DriverService).toBeDefined();
        expect(CompanyService).toBeDefined();
        expect($scope).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
    }));

    it('saveClick Should Set The CompanyId And The CompanyName The Company Object On The Scope',function(){
        var company = {id:'Test Company Id To Be Set On Driver.companyId',tradingAs:'Test Company Trading As To Be Set On The Driver.companyName'};
        $scope.company = company;

        $controller('DriverController',{
            $scope:$scope
        });
        $scope.saveClick();
        expect($scope.driver.companyId).toEqual(company.id);
        expect($scope.driver.companyName).toEqual(company.tradingAs);
    });

    it('Query Should Call Query On The Service Using Parameters On The Scope And Set The Resulting Drivers On The Scope', function () {

        var drivers = [1, 2, 3];

        spyOn(DriverService, 'query').andReturn(drivers);

        var controller = $controller('DriverController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();

        var skip = 1;
        var limit = 3;

        var params = {skip: skip, limit: limit};
        $scope.skip = skip;
        $scope.limit = limit;

        $scope.query();

        expect(DriverService.query).toHaveBeenCalledWith(params);
        expect($scope.drivers).toEqual(drivers);
    });

    it('Should Have The Driver On The $scope at init', function () {

        $controller('DriverController', {
            $scope: $scope
        });

        expect($scope.driver).toBeDefined();
    });

    it('Should Call Util Service canSave', function () {

        $controller('DriverController', {
            $scope: $scope
        });
        expect($scope.canSave).toBeDefined();
        $scope.addForm = {};
        spyOn(FormSubmissionUtilService, 'canSave').andReturn(true);

        $scope.canSave();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('Should Call Service Save With $scope.driver when $scope.new is true ', function () {

        $controller('DriverController', {
            $scope: $scope
        });

        var driverToSave = {name: 'Test Driver Name To Be Posted', id: 'Test Driver Id To Be Posted'};
        var savedDriver = {
            name: 'Test Driver Name To Be Returned After We Posted',
            id: 'Test Driver Id To Returned After We Posted'
        };
        $scope.driver = driverToSave;

        $scope.new = true;
        spyOn(DriverService, 'save').andReturn(savedDriver);
        spyOn($scope, 'list');
        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();
        expect(DriverService.save).toHaveBeenCalledWith(driverToSave, jasmine.any(Function));
        expect($scope.recentlyAddedDriver).toEqual(savedDriver);
        expect($scope.list).toHaveBeenCalled();
    });

    it('Should Call get with the given skip and limit and set the returned page on the scope', function () {
        $controller('DriverController', {
            $scope: $scope
        });
        var skip = 10;
        var limit = 100;
        var page = {size: limit};
        var params = {skip: skip, limit: limit};
        spyOn(DriverService, 'get').andReturn(page);
        expect($scope.list).toBeDefined();

        $scope.skip = skip;
        $scope.limit = limit;
        $scope.list();
        expect(DriverService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Set skip to 0 and limit to Default Result Size', function () {
        $controller('DriverController', {
            $scope: $scope
        });

        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Revert The Driver To The Prestine One', function () {
        $controller('DriverController', {
            $scope: $scope
        });

        var prestine = $scope.driver;

        expect($scope.reset).toBeDefined();
        $scope.driver = {name: 'Test Driver To Be Reverted'};
        $scope.reset();
        expect($scope.driver).toEqual(prestine);
    });

});