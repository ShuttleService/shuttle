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

        $scope.query = function(){

            $scope.reviews = ReviewService.query({skip:$scope.skip,limit:$scope.limit});
        }
    })


