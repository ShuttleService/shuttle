/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Review Controller', function () {

    var $controller;
    var ReviewService;
    var $scope;
    var FormSubmissionUtilService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$controller_, _ReviewService_, $rootScope, _FormSubmissionUtilService_,_RESULT_SIZE_) {

        $controller = _$controller_;
        ReviewService = _ReviewService_;
        $scope = $rootScope.$new();
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;

        expect($controller).toBeDefined();
        expect(ReviewService).toBeDefined();
        expect($scope).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
    }));

    it('Should Call The ReviewService Query With The Params On The Scope And Set The Resulting Reviews On The Scope', function () {

        $controller('ReviewController', {
            $scope: $scope
        });

        var reviews = [2, 4, 6];
        var skip = 3;
        var limit = 10;
        var params = {skip: skip, limit: limit};

        spyOn(ReviewService, 'query').andReturn(reviews);
        $scope.skip = skip;
        $scope.limit = limit;

        $scope.query();

        expect(ReviewService.query).toHaveBeenCalledWith(params);
        expect($scope.reviews).toEqual(reviews);

    });

    it('Should Have A review On The $scope', function () {

        $controller('ReviewController', {
            $scope: $scope
        });

        expect($scope.review).toBeDefined();
    });

    it('Should Call Review Service save when $scope.new is true', function () {

        $controller('ReviewController', {
            $scope: $scope
        });
        var reviewToSave = {id: 'Test Review ID To POST', text: ['Test First Review Text To POST']};
        var savedReview = {id: 'Test Review ID To Be Returned From The Call To Review.save'};

        expect($scope.saveClick).toBeDefined();

        $scope.new = true;
        $scope.review = reviewToSave;

        spyOn(ReviewService, 'save').andReturn(savedReview);

        $scope.saveClick();

        expect(ReviewService.save).toHaveBeenCalledWith(reviewToSave,jasmine.any(Function));

        expect($scope.review).toEqual(savedReview);
    });

    it('Should Create A Review Text Array And Add The New Review If There Are No Reviews', function () {

        $controller('ReviewController', {
            $scope: $scope
        });

        var initialReviewToPost = {};
        var reviewText = "Test Review Text To Be Added To The Newly Created Array Of Reviews";

        $scope.review = initialReviewToPost;

        $scope.review.text = reviewText;
        var expectedReviewToBeCreated = {text: reviewText, reviews: [reviewText]};

        $scope.new = true;
        spyOn(ReviewService, 'save');
        $scope.saveClick();

        expect(ReviewService.save).toHaveBeenCalledWith(expectedReviewToBeCreated,jasmine.any(Function));

    });

    it('Should Add The Review Text To The Already Existent Array Of Reviews ', function () {

        $controller('ReviewController', {
            $scope: $scope
        });
        var initialReviewText = 'Test Review Already In Array Of Reviews';
        var initialReviewToPost = {reviews: [initialReviewText]};
        var reviewTextToAdd = 'Test Review Text To Add To Array';
        var reviewToPost = {text: reviewTextToAdd, reviews: [initialReviewText, reviewTextToAdd]};

        $scope.review = initialReviewToPost;
        $scope.new = true;
        $scope.review.text = reviewTextToAdd;

        spyOn(ReviewService, 'save');
        spyOn($scope,'list');

        $scope.saveClick();

        expect(ReviewService.save).toHaveBeenCalledWith(reviewToPost,jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();

    });

    it('Should Call FormSubmissionUtilService canSave ', function () {

        $controller('ReviewController', {
            $scope: $scope
        });

        expect($scope.canSave).toBeDefined();

        spyOn(FormSubmissionUtilService, 'canSave').andReturn(false);

        expect($scope.canSave()).toEqual(false);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('Should Call Get On The Service With The Given skip and limit And Put The Page On The Scope', function () {
        $controller('ReviewController',{
            $scope:$scope
        });
        var skip = 0;
        var limit = 10;
        var params = {skip:skip,limit:limit};
        var page = {size:limit};
        spyOn(ReviewService,'get').andReturn(page);

        expect($scope.list).toBeDefined();
        $scope.skip = skip;
        $scope.limit = limit;
        $scope.list();
        expect(ReviewService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Set Skip And Limit On Init',function(){
        $controller('ReviewController',{$scope:$scope});
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

});