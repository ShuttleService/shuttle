/**
 * Created by zorodzayi on 14/12/15.
 */
describe('Testing The "SharedController" (The Controller That Holds The State That Is To Be Used Across Other Controller)', function () {

    var controller, $rootScope, CompanyService, $scope,RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_,_CompanyService_,$controller,_RESULT_SIZE_){
        $rootScope = _$rootScope_;
        expect($rootScope).toBeDefined();
        $scope = $rootScope.$new();
        CompanyService = _CompanyService_;
        RESULT_SIZE = _RESULT_SIZE_;
        expect(RESULT_SIZE).toBeDefined();
        expect(CompanyService).toBeDefined();

        controller = $controller('SharedController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();
    }));

    it('Should Set The SharedState On The $rootScope',function(){
        expect($rootScope.sharedState).toBeDefined();
    });

    it('init function should be defined',function(){
        expect($scope.init).toBeDefined();
    });

    it('Should Call CompanyService.query and set the list of companies on the $rootScope sharedState', function () {
        var companies = [1,2,3,4,5];
        var page      = {page:{content:companies}};

        spyOn(CompanyService,'get').andReturn(page);

        $scope.init();

        expect(CompanyService.get).toHaveBeenCalledWith({skip:0,limit:RESULT_SIZE});
        expect($rootScope.sharedState.companyPage).toEqual(page);

    });
});
