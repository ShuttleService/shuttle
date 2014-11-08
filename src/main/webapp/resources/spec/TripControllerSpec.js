/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Trip Controller', function () {

    var $scope;
    var TripService;
    var $controller;
    var FormSubmissionUtilService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _TripService_, _FormSubmissionUtilService_,_RESULT_SIZE_) {

        $scope = _$rootScope_.$new();
        TripService = _TripService_;
        $controller = _$controller_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($scope).toBeDefined();
        expect(TripService).toBeDefined();
        expect($controller).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
    }));

    it('Should Call The Query On Trip Service With Params On The Scope And Set The Result List Of Trips On The Scope', function () {

        var controller = $controller('TripController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();

        var trips = ['', 1, "3"];

        spyOn(TripService, 'query').andReturn(trips);
        var skip = 3;
        var limit = 5;
        $scope.skip = skip;
        $scope.limit = limit;

        var params = {skip: skip, limit: limit};
        $scope.query();

        expect(TripService.query).toHaveBeenCalledWith(params);
        expect($scope.trips).toEqual(trips);
    });

    it('Should Have A trip on The $scope', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.trip).toBeDefined();
    });

    it('Should Call canSave On The FormSubmissionUtilService', function () {

        $controller('TripController', {
            $scope: $scope
        });

        spyOn(FormSubmissionUtilService, 'canSave').andReturn(false);

        expect($scope.canSave).toBeDefined();
        var actual = $scope.canSave();
        expect(actual).toEqual(false);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('Should Call TripService Save When $scope.new is true', function () {

        $controller('TripController', {
            $scope: $scope
        });

        $scope.new = true;
        var trip = {source: 'Test Trip Source To POST'};
        var savedTrip = {source: 'Test Trip Source POSTED'};

        $scope.trip = trip;

        spyOn(TripService, 'save').andReturn(savedTrip);
        spyOn($scope,'list');
        expect($scope.saveClick).toBeDefined();

        $scope.saveClick();

        expect(TripService.save).toHaveBeenCalledWith(trip,jasmine.any(Function));
        expect($scope.trip).toEqual(savedTrip);
        expect($scope.list).toHaveBeenCalled();
    });

    it('Should Calculate The Product Of price Per Km And The Distance And Set The Price On The ', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.price).toBeDefined();

        $scope.trip = {};
        var pricePerKm = 11;
        var distance = 13;

        $scope.trip.distance = distance;
        $scope.trip.pricePerKm = pricePerKm;

        $scope.price();

        expect($scope.trip.price).toEqual(distance * pricePerKm);
    });

    it('Should Call List On The Service With The Given skip and limit and Save The Page On The Scope', function () {
        var skip = 0;
        var limit = 10;
        $controller('TripController', {
            $scope: $scope
        });
        var params = {skip: skip, limit: limit};
        var page = {size: limit};
        spyOn(TripService, 'get').andReturn(page);
        expect($scope.list).toBeDefined();

        $scope.limit = limit;
        $scope.skip = skip;
        $scope.list();
        expect(TripService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toBeDefined(page);
    });

    it('Should Set Skip And Limit On The $scope At Startup', function () {
        $controller('TripController', {$scope: $scope});

        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Revert The Trip To The Prestine State',function(){
        $controller('TripController',{
            $scope:$scope
        });

        var prestine = $scope.trip;
        $scope.trip = {name:'Test Trip To Be Reverted'};
        expect($scope.trip).toBeDefined();
        $scope.reset();
        expect($scope.trip).toEqual(prestine);
    });
});
