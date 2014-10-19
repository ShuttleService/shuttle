/**
 * Created by reality on 14/10/19.
 */
describe('Company Controller Test', function () {

    var $controller;
    var CompanyService;
    var $scope;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope, _$controller_, _CompanyService_) {

        $scope = $rootScope.$new();
        CompanyService = _CompanyService_;
        $controller = _$controller_;

        expect($scope).toBeDefined();
        expect(CompanyService).toBeDefined();
        expect($controller).toBeDefined();
    }));

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
});
