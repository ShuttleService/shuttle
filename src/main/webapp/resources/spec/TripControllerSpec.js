/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Trip Controller', function () {

    var $scope;
    var TripService;
    var $controller;
    var FormSubmissionUtilService;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _TripService_, _FormSubmissionUtilService_) {

        $scope = _$rootScope_.$new();
        TripService = _TripService_;
        $controller = _$controller_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;

        expect($scope).toBeDefined();
        expect(TripService).toBeDefined();
        expect($controller).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
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
        var trip = {source:'Test Trip Source To POST'};
        var savedTrip = {source:'Test Trip Source POSTED'};

        $scope.trip = trip;

        spyOn(TripService,'save').andReturn(savedTrip);

        expect($scope.saveClick).toBeDefined();

        $scope.saveClick();

        expect(TripService.save).toHaveBeenCalledWith(trip);
        expect($scope.trip).toEqual(savedTrip);
    });
});
