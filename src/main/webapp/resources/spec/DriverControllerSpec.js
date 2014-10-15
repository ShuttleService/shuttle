/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Driver Controller', function () {

    var $controller;
    var DriverService;
    var $scope;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$controller_, _DriverService_, $rootScope) {

        $controller = _$controller_;
        DriverService = _DriverService_;
        $scope = $rootScope.$new();

        expect($controller).toBeDefined();
        expect(DriverService).toBeDefined();
        expect($scope).toBeDefined();

    }));

    it('Query Should Call Query On The Service Using Parameters On The Scope And Set The Resulting Drivers On The Scope', function () {

        var drivers = [1,2,3];

        spyOn(DriverService,'query').andReturn(drivers);

        var controller = $controller('DriverController',{
            $scope:$scope
        });

        expect(controller).toBeDefined();

        var skip = 1;
        var limit = 3;

        var params = {skip:skip,limit:limit};
        $scope.skip = skip;
        $scope.limit = limit;

        $scope.query();

        expect(DriverService.query).toHaveBeenCalledWith(params);
        expect($scope.drivers).toEqual(drivers);
        });
})