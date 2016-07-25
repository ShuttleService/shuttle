/**
 * Created by zorodzayi on 14/12/15.
 */
describe('Testing The "SharedController" (The Controller That Holds The State That Is To Be Used Across Other Controller)', function () {

    var controller, $rootScope,UserService,CompanyService,VehicleService,DriverService,AgentService,$scope, RESULT_SIZE,params;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _CompanyService_,_UserService_,_VehicleService_,_DriverService_,_AgentService_,$controller, _RESULT_SIZE_) {
        $rootScope = _$rootScope_;
        expect($rootScope).toBeDefined();
        $scope = $rootScope.$new();
        CompanyService = _CompanyService_;
        VehicleService = _VehicleService_;
        DriverService = _DriverService_;
        AgentService = _AgentService_;
        UserService = _UserService_;

        RESULT_SIZE = _RESULT_SIZE_;

        expect(RESULT_SIZE).toBeDefined();
        expect(CompanyService).toBeDefined();
        expect(VehicleService).toBeDefined();
        expect(DriverService).toBeDefined();
        expect(UserService).toBeDefined();
        params = {skip:0, limit:RESULT_SIZE};
        expect(AgentService).toBeDefined();

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

        spyOn(CompanyService, 'get').and.returnValue(page);
        expect($scope.findCompanies).toBeDefined();
        $scope.findCompanies();

        expect(CompanyService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.companyPage).toEqual(page);

    });

    it('Should Call VehicleService.get And Set The VehiclePage On The $rootScope SharedState', function () {
        var vehicles = [3,4,5,6,7,7,8];
        var page = {page:{content:vehicles}};
        spyOn(VehicleService,'get').and.returnValue(page);
        expect($scope.findVehicles).toBeDefined();
        $scope.findVehicles();
        expect(VehicleService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.vehiclePage).toEqual(page);
    });

    it('Should Call DriverService.get And Set The DriverPage On The $rooScope SharedState',function(){
        var drivers = [5,6,7,9,0,23];
        var page = {page:{content:drivers}};
        spyOn(DriverService,'get').and.returnValue(page);
        expect($scope.findDrivers).toBeDefined();
        $scope.findDrivers();
        expect(DriverService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.driverPage).toEqual(page);

    });

    it('Should Call The AgentService.get And Set The AgentPage On The $rootScope SharedState',function(){
        var agents = [2,3,4,5];
        var page = {content:agents};
        spyOn(AgentService,'get').and.returnValue(page);
        expect($scope.findAgents).toBeDefined();
        $scope.findAgents();
        expect(AgentService.get).toHaveBeenCalledWith(params);
        expect($rootScope.sharedState.agentPage).toEqual(page);
    });

    it('Should Call Service query with {pathVariable:role}, get the roles and set them on the $scope ', function () {
        var roles = ['ROLE_admin','ROLE_agent','ROLE_world','ROLE_companyUser'];
        spyOn(UserService,'query').and.returnValue(roles);
        expect($scope.findRoles).toBeDefined();
        $scope.findRoles();
        expect(UserService.query).toHaveBeenCalledWith({pathVariable:'role'});
        expect($scope.sharedState.roles).toEqual(roles);
    });

    it('Init Should Call The Various Methods On The Scope To Populate The Drop Downs', function () {
        spyOn($scope, 'findCompanies');
        spyOn($scope,'findVehicles');
        spyOn($scope,'findDrivers');
        spyOn($scope,'findAgents');
        spyOn($scope,'findRoles');

        $scope.init();

        expect($scope.findCompanies).toHaveBeenCalled();
        expect($scope.findVehicles).toHaveBeenCalled();
        expect($scope.findDrivers).toHaveBeenCalled();
        expect($scope.findAgents).toHaveBeenCalled();
        expect($scope.findRoles).toHaveBeenCalled();
    });
});
