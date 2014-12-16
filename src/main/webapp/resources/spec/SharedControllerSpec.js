/**
 * Created by zorodzayi on 14/12/15.
 */
describe('Testing The "SharedController" (The Controller That Holds The State That Is To Be Used Across Other Controller)', function () {

    var controller, $rootScope, CompanyService,VehicleService,DriverService,$scope, RESULT_SIZE,params;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _CompanyService_,_VehicleService_,_DriverService_,$controller, _RESULT_SIZE_) {
        $rootScope = _$rootScope_;
        expect($rootScope).toBeDefined();
        $scope = $rootScope.$new();
        CompanyService = _CompanyService_;
        VehicleService = _VehicleService_;
        DriverService = _DriverService_;

        RESULT_SIZE = _RESULT_SIZE_;
        expect(RESULT_SIZE).toBeDefined();
        expect(CompanyService).toBeDefined();
        expect(VehicleService).toBeDefined();
        expect(DriverService).toBeDefined();
        params = {skip:0, limit:RESULT_SIZE};

        controller = $controller('SharedController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();
    }));

    it('Should Set The SharedState On The $rootScope', function () {
        expect($rootScope.sharedState).toBeDefined();
    });

    it('init function should be defined', function () {
        expect($scope.init).toBeDefined();
    });

    it('Should Call CompanyService.query and set the companyPage on the $rootScope sharedState', function () {
        var companies = [1, 2, 3, 4, 5];
        var page = {page: {content: companies}};

        spyOn(CompanyService, 'get').andReturn(page);
        expect($scope.findCompanies).toBeDefined();
        $scope.findCompanies();

        expect(CompanyService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.companyPage).toEqual(page);

    });

    it('Should Call VehicleService.get And Set The VehiclePage On The $rootScope SharedState', function () {
        var vehicles = [3,4,5,6,7,7,8];
        var page = {page:{content:vehicles}};
        spyOn(VehicleService,'get').andReturn(page);
        expect($scope.findVehicles).toBeDefined();
        $scope.findVehicles();
        expect(VehicleService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.vehiclePage).toEqual(page);
    });

    it('Should Call DriverService.get And Set The DriverPage On The $rooScope SharedState',function(){
        var drivers = [5,6,7,9,0,23];
        var page = {page:{content:drivers}};
        spyOn(DriverService,'get').andReturn(page);
        expect($scope.findDrivers).toBeDefined();
        $scope.findDrivers();
        expect(DriverService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.driverPage).toEqual(page);

    });

    it('Init Should Call The Various Methods On The Scope To Populate The Drop Downs', function () {
        spyOn($scope, 'findCompanies');
        spyOn($scope,'findVehicles');
        spyOn($scope,'findDrivers');
        $scope.init();
        expect($scope.findCompanies).toHaveBeenCalled();
        expect($scope.findVehicles).toHaveBeenCalled();
        expect($scope.findDrivers).toHaveBeenCalled();
    });
});
