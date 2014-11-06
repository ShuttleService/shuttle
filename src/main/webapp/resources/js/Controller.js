/**
 * Created by zorodzayi on 14/10/09.
 */

angular.module('controllers', ['services']).

    controller('DriverController', function ($scope, $log, DriverService, FormSubmissionUtilService, RESULT_SIZE) {
        $scope.new = true;
        $scope.driver = {};
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.query = function () {

            $scope.drivers = DriverService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {

            if ($scope.new === true) {

                $scope.recentlyAddedDriver = DriverService.save($scope.driver, function (data) {
                    $scope.list();
                });
            }
        };

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling get with ' + params);
            $scope.page = DriverService.get(params);
            $log.debug('Got the page ' + $scope.page);
        };

        $scope.list();
    }).

    controller('TripController', function ($scope, TripService, FormSubmissionUtilService, $log, RESULT_SIZE) {
        $scope.trip = {};
        $scope.new = true;
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.query = function () {
            $scope.trips = TripService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {
            console.log('Clicked The Save Button');
            if ($scope.new === true) {
                console.log('About To Post Since This Is A New Trip');
                $scope.trip = TripService.save($scope.trip, function (data) {
                    $scope.list();
                });
            }
        };

        $scope.price = function () {
            console.log('Calculating The Price .');
            $scope.trip.price = $scope.trip.pricePerKm * $scope.trip.distance;
        };

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling Get With ' + params);
            $scope.page = TripService.get(params);
        };

        $scope.list();
    }).

    controller('ReviewController', function ($scope, $log, ReviewService, FormSubmissionUtilService, RESULT_SIZE) {

        $scope.review = {};
        $scope.new = true;
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.query = function () {

            $scope.reviews = ReviewService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {
            console.log('Clicked Save Button');

            if ($scope.review.reviews === undefined) {
                console.log('No Review Array. Creating One');
                $scope.review.reviews = [];
            }

            $scope.review.reviews.push($scope.review.text);

            if ($scope.new === true) {
                console.log('This is a new Review. Adding It');
                $scope.review = ReviewService.save($scope.review, function (data) {
                    $scope.list();
                });
            }
            console.log('Set The Review To ' + $scope.review);

        };

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Listing The Reviews {Params:' + params + '}');
            $scope.page = ReviewService.get(params);
        };

        $scope.list();
    }).

    controller('CompanyController', function ($scope, $log, CompanyService, FormSubmissionUtilService, RESULT_SIZE) {

        $scope.company = {};
        $scope.new = true;
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.get = function () {

            $scope.company = CompanyService.get({id: $scope.id});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {

            console.log('Clicked Save Button');

            if ($scope.new === true) {
                $scope.recentlyAddedCompany = CompanyService.save($scope.company, function (data) {
                    $log.debug('Added The Company ' + $scope.recentlyAddedCompany.tradingAs + '. Listing All The Companies.');
                    $scope.list();
                });
            }
        };

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling list with {skip:' + params.skip + ',limit:' + params.limit + '}');
            $scope.page = CompanyService.get(params);
            $log.debug('Got Back {page:' + $scope.page + '}');
        };

        $scope.list();

    }).

    controller('UserController', function ($scope,$log, UserService, FormSubmissionUtilService,RESULT_SIZE) {
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;
        $scope.user = {};
        $scope.new = true;

        $scope.saveClick = function () {
            console.log('Clicked Submit Button');

            $scope.user = UserService.save($scope.user);
            $log.info('Submitted To The Rest Service');
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling Get With The Params ' + params);
            $scope.page = UserService.get(params);
        };

        $scope.list();
    }).

    controller('VehicleController', function ($scope,$log,VehicleService,RESULT_SIZE) {
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;
        $scope.vehicle = {};

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling page with the params '+params);
            $scope.page = VehicleService.get(params);
        };

        $scope.saveClick = function(){
            $log.debug('Posting The Vehicle '+$scope.vehicle);
            VehicleService.save($scope.vehicle,function(data){
               $scope.list();
            });
        };

        $scope.list();
    }).

    constant('RESULT_SIZE', 100);

