/**
 * Created by zorodzayi on 14/10/09.
 */

angular.module('controllers', ['services']).
    controller('SharedController', function ($rootScope,$scope, $log,CompanyService,DriverService,VehicleService,RESULT_SIZE) {
        $rootScope.sharedState = {};
        var params = {skip:0,limit:RESULT_SIZE};

        $scope.findCompanies = function(){
            $log.debug('Calling Company Service Get To Get A CompanyPage To Set On The $rootScope Shared State');
            $rootScope.sharedState.companyPage = CompanyService.get(params);
        };

        $scope.findVehicles = function(){
            $log.debug('Calling Vehicle Service Get To Get A VehiclePage To Set On The $rootScope Shared State');
            $rootScope.sharedState.vehiclePage = VehicleService.get(params);
        };

        $scope.findDrivers = function(){
            $log.debug('Calling Driver Service Get To Get A DriverPage To Set On The $rootScope Shared State');
            $rootScope.sharedState.driverPage = DriverService.get(params)
        };

        $scope.init = function(){
            $scope.findCompanies();
            $scope.findVehicles();
            $scope.findDrivers();
        };

        $scope.init();
    }).
    controller('DriverController', function ($scope, $log, DriverService, FormSubmissionUtilService, RESULT_SIZE) {
        $scope.new = true;
        $scope.driver = {};
        $scope.prestineDriver = angular.copy($scope.driver);
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.query = function () {
            $scope.drivers = DriverService.query({skip: $scope.skip, limit: $scope.limit});
        };

        $scope.canSave = function () {
            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {
            $scope.driver.companyId = $scope.company.id;
            $scope.driver.companyName = $scope.company.tradingAs;

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

        $scope.reset = function () {
            $log.debug("Reverting The Driver To It's Prestine State");
            $scope.driver = angular.copy($scope.prestineDriver);
        };

        $scope.list();
    }).

    controller('TripController', function ($scope, TripService, FormSubmissionUtilService, $log, RESULT_SIZE) {
        $scope.trip = {};
        $scope.prestineTrip = angular.copy($scope.trip);
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
            console.log('Attempting To Save The Trip');
            if($scope.company !== undefined) {
                var companyId = $scope.company.id;
                var companyName = $scope.company.tradingAs;
                $log.debug('Setting The Trip Company Id To ' + companyId + ' Company Name To ' + companyName);
                $scope.trip.companyId = companyId;
                $scope.trip.companyName = companyName;
            }
            if($scope.driver !== undefined) {
                var driverId = $scope.driver.id;
                var driverName = $scope.driver.firstName+' '+$scope.driver.surname;
                $log.debug('Setting The Driver Id To ' + driverId + ' And Driver Name To ' + driverName);
                $scope.trip.driverId = driverId;
                $scope.trip.driverName = driverName;
            }
            if($scope.vehicle !== undefined) {
                var vehicleName = $scope.vehicle.make + ' ' + $scope.vehicle.model +' '+ $scope.vehicle.licenseNumber;
                var vehicleId = $scope.vehicle.id;
                $log.debug('Setting The VehicleName To ' + vehicleName + ' Vehicle License Number To ' + vehicleId);
                $scope.trip.vehicleName = vehicleName;
                $scope.trip.vehicleId = vehicleId;
            }
            if ($scope.new === true) {
                console.log('About To Post Since This Is A New Trip');
                $scope.savedTrip = TripService.save($scope.trip, function (data) {
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

        $scope.reset = function () {
            $log.debug('Reverting The Trip To Its Prestine State');
            $scope.trip = angular.copy($scope.prestineTrip);
        };

        $scope.list();
    }).

    controller('ReviewController', function ($scope, $log, ReviewService, FormSubmissionUtilService, RESULT_SIZE) {

        $scope.review = {};
        $scope.prestineReview = angular.copy($scope.review);
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

        $scope.reset = function () {
            $log.debug('Reverting The Review To The Prestine State');
            $scope.review = angular.copy($scope.prestineReview);
        };

        $scope.list();
    }).

    controller('CompanyController', function ($scope, $log, CompanyService, FormSubmissionUtilService, RESULT_SIZE) {

        $scope.company = {};
        $scope.prestineCompany = angular.copy($scope.company);
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

        $scope.reset = function () {
            $log.debug('Attempting To Reset The Company Back To Its Prestine Form ' + $scope.prestineCompany);
            $scope.company = angular.copy($scope.prestineCompany);
        };

        $scope.list();
    }).

    controller('UserController', function ($scope, $log, UserService, FormSubmissionUtilService, CompanyService, RESULT_SIZE) {
        $scope.range = {};
        $scope.range.skip = 0;
        $scope.range.limit = RESULT_SIZE;
        $scope.user = {};
        $scope.formHolder = {};
        $scope.prestineUser = angular.copy($scope.user);
        $scope.confirmPasswordPrestine = angular.copy($scope.confirmPassword);
        $scope.new = true;

        $scope.saveClick = function () {
            console.log('Clicked Submit Button');
            $scope.user.username = $scope.user.email;

            if ($scope.user.company !== undefined && $scope.user.company !== null) {
                var companyName = $scope.user.company.tradingAs;
                var companyId = $scope.user.company.id;
                $log.debug('Setting Company Name to ' + companyName + ' Company Id To ' + companyId);
                $scope.user.companyName = companyName;
                $scope.user.companyId = companyId;
            } else {
                $log.debug("Did not set the Company Name Or Company Id As There Is No company on the scope");
            }

            UserService.save($scope.user, function (data) {
                $log.info('Submitted To The Rest Service');
                $scope.user = data;
                $scope.list();
            });

        };

        $scope.canSave = function () {
            return FormSubmissionUtilService.canSave($scope.formHolder.addForm);
        };

        $scope.list = function () {
            var params = {skip: $scope.range.skip, limit: $scope.range.limit};
            $log.debug('Calling Get With The Params {skip:' + params.skip + ', limit:' + params.limit + '}');
            $scope.page = UserService.get(params);
        };

        $scope.reset = function () {

            $log.debug('Reverting The User To It\'s Prestine State');
            $scope.user = angular.copy($scope.prestineUser);
            $scope.formHolder.confirmPassword = angular.copy($scope.confirmPasswordPrestine);
        };

        $scope.list();
    }).

    controller('VehicleController', function ($scope, $log, FormSubmissionUtilService, VehicleService, RESULT_SIZE) {
        $scope.skip = 0;
        $scope.vehicleTypes = ['Bakkie','Bus','Hatch Back','Lorry','Mini Bus','Sedan','Station Wagon','SUV','Truck','Van'];
        $scope.limit = RESULT_SIZE;
        $scope.vehicle = {};
        $scope.prestineVehicle = angular.copy($scope.vehicle);

        $scope.list = function () {
            var params = {skip: $scope.skip, limit: $scope.limit};
            $log.debug('Calling page with the params ' + params);
            $scope.page = VehicleService.get(params);
        };

        $scope.canSave = function () {

            $log.debug('Checking The Validity Of the Vehicle add Form');
            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function () {
            $log.debug('Posting The Vehicle ' + $scope.vehicle);
            var companyName = $scope.company.tradingAs;
            var companyId   = $scope.company.id;
            $log.debug('Setting The Vehicle Company Name To '+companyName+' And The Company Id To '+companyId);
            $scope.vehicle.companyId = companyId;
            $scope.vehicle.companyName = companyName;

            VehicleService.save($scope.vehicle, function (data) {
                $scope.list();
            });
        };

        $scope.reset = function () {
            $log.debug('Reverting The Vehicle To It\'s Prestine State');
            $scope.vehicle = angular.copy($scope.prestineVehicle);
        };

        $scope.list();
    }).

    controller('AgentController',function($scope,AgentService,FormSubmissionUtilService,RESULT_SIZE,$log){

        $scope.agent = {};
        $scope.skip = 0;
        $scope.limit = RESULT_SIZE;

        $scope.list = function(){
            $scope.page = AgentService.get({skip:$scope.skip,limit:$scope.limit});
        };

        $scope.canSave = function(){
            return FormSubmissionUtilService.canSave($scope.addForm);
        };

        $scope.saveClick = function(){
            $log.debug('Attempting To Save An Agent. Please Wait...');
            $scope.recentlySaved = AgentService.save($scope.agent,function(data){
                $log.debug('Successfully saved The Agent And Got Back '+data);
                $scope.list();
            });
        };

        $scope.list();

    }).

    constant('RESULT_SIZE', 100);

