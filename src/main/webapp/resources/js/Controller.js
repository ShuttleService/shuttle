/**
 * Created by zorodzayi on 14/10/09.
 */

angular.module('controllers', ['services']).

    controller('DriverController', function ($scope, DriverService, FormSubmissionUtilService) {
        $scope.new = true;
        $scope.driver = {};
        $scope.query = function () {

            $scope.drivers = DriverService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {

            if ($scope.new === true) {

                $scope.driver = DriverService.save($scope.driver);
            }
        };
    }).

    controller('TripController', function ($scope, TripService) {

        $scope.query = function () {
            $scope.trips = TripService.query({skip: $scope.skip, limit: $scope.limit});
        }
    }).

    controller('ReviewController', function ($scope, ReviewService,FormSubmissionUtilService) {

        $scope.review = {};
        $scope.new = true;
        $scope.query = function () {

            $scope.reviews = ReviewService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function(){

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
                $scope.review = ReviewService.save($scope.review);
            }
            console.log('Set The Review To ' + $scope.review);
        };
    }).

    controller('CompanyController', function ($scope, CompanyService, FormSubmissionUtilService) {

        $scope.company = {};
        $scope.new = true;

        $scope.get = function (id) {

            $scope.company = CompanyService.get({id: $scope.id});
        };

        $scope.canSave = function () {

            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {

            console.log('Clicked Save Button');

            if ($scope.new === true) {

                $scope.company = CompanyService.save($scope.company);
            }
        }
    });


