/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Review Controller', function () {

    var $controller;
    var ReviewService;
    var $scope;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$controller_, _ReviewService_, $rootScope) {

        $controller = _$controller_;
        ReviewService = _ReviewService_;
        $scope = $rootScope.$new();

        expect($controller).toBeDefined();
        expect(ReviewService).toBeDefined();
        expect($scope).toBeDefined();

    }));

    it('Should Call The ReviewService Query With The Params On The Scope And Set The Resulting Reviews On The Scope', function () {

        var controller = $controller('ReviewController', {
             $scope:$scope
        });

        var reviews = [2,4,6];
        var skip = 3;
        var limit = 10;
        var params = {skip:skip,limit:limit};

        spyOn(ReviewService,'query').andReturn(reviews);
        $scope.skip = skip;
        $scope.limit = limit;

        $scope.query();

        expect(ReviewService.query).toHaveBeenCalledWith(params);
        expect($scope.reviews).toEqual(reviews);

    });
});