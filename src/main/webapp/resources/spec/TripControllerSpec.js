/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Trip Controller', function () {

    var $scope;
    var TripService;
    var $controller;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _TripService_) {

        $scope = _$rootScope_.$new();
        TripService = _TripService_;
        $controller = _$controller_;

        expect($scope).toBeDefined();
        expect(TripService).toBeDefined();
        expect($controller).toBeDefined();
    }));

    it('Should Call The Query On Trip Service With Params On The Scope And Set The Result List Of Trips On The Scope', function () {

        var controller = $controller('TripController',{
            $scope:$scope
        });

        expect(controller).toBeDefined();

        var trips = ['',1,"3"];

        spyOn(TripService,'query').andReturn(trips);
        var skip = 3;
        var limit = 5;
        $scope.skip = skip;
        $scope.limit = limit;

        var params = {skip:skip,limit:limit};
        $scope.query();

        expect(TripService.query).toHaveBeenCalledWith(params);
        expect($scope.trips).toEqual(trips);
    });
});
