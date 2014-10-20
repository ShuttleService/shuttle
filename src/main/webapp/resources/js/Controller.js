/**
 * Created by zorodzayi on 14/10/09.
 */

angular.module('controllers', ['services']).

    controller('DriverController', function ($scope, DriverService) {

        $scope.query = function () {

            $scope.drivers = DriverService.query({skip: $scope.skip, limit: $scope.limit});
        }
    }).

    controller('TripController', function ($scope, TripService) {

        $scope.query = function () {
            $scope.trips = TripService.query({skip: $scope.skip, limit: $scope.limit});
        }
    }).

    controller('ReviewController', function ($scope, ReviewService) {

        $scope.query = function () {

            $scope.reviews = ReviewService.query({skip: $scope.skip, limit: $scope.limit});
        }
    }).

    controller('CompanyController', function ($scope, CompanyService,FormSubmissionUtilService) {

        $scope.company = {};
        $scope.new    = true;

        $scope.get = function (id) {

            $scope.company = CompanyService.get({id: $scope.id});
        };

        $scope.canSave = function(){

            return FormSubmissionUtilService.canSave($scope.addForm) ;
        }

        $scope.saveClick = function(){

            console.log('Clicked Save Button');

            if($scope.new === true ){

                CompanyService.save($scope.company);
            }
        }
    })


