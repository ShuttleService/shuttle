/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Driver Controller', function () {

    var $controller;
    var DriverService;
    var $scope;
    var FormSubmissionUtilService;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$controller_, _DriverService_, $rootScope, _FormSubmissionUtilService_) {

        $controller = _$controller_;
        DriverService = _DriverService_;
        $scope = $rootScope.$new();
        FormSubmissionUtilService = _FormSubmissionUtilService_;

        expect($controller).toBeDefined();
        expect(DriverService).toBeDefined();
        expect($scope).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
    }));

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

    it('Should Call Service Save With $scope.drive when $scope.new is true ', function () {

        $controller('DriverController', {
            $scope: $scope
        });

        var driverToSave = {name: 'Test Driver Name To Be Posted', id: 'Test Driver Id To Be Posted'};
        var savedDriver = {name: 'Test Driver Name To Be Returned After We Posted', id: 'Test Driver Id To Returned After We Posted'};
        $scope.driver = driverToSave;

        $scope.new = true;
        spyOn(DriverService, 'save').andReturn(savedDriver);

        expect($scope.saveClick).toBeDefined();
        $scope.saveClick();

        expect(DriverService.save).toHaveBeenCalledWith(driverToSave);
        expect($scope.driver).toEqual(savedDriver);

    });

});