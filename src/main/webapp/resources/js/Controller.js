/**
 * Created by zorodzayi on 14/10/09.
 */

angular.module('controllers', ['services', 'ngMaterial']).controller('SharedController',
    function ($rootScope, $scope, $log, AgentService, CompanyService, DriverService, UserService, VehicleService,
              RESULT_SIZE) {
        $rootScope.sharedState = {};
        var params = {skip: 0, limit: RESULT_SIZE};

        $scope.findCompanies = function () {
            $log.debug('Calling Company Service Get To Get A CompanyPage To Set On The $rootScope Shared State');
            $rootScope.sharedState.companyPage = CompanyService.get(params);
        };

        $scope.findVehicles = function () {
            $log.debug('Calling Vehicle Service Get To Get A VehiclePage To Set On The $rootScope Shared State');
            $rootScope.sharedState.vehiclePage = VehicleService.get(params);
        };

        $scope.findDrivers = function () {
            $log.debug('Calling Driver Service Get To Get A DriverPage To Set On The $rootScope Shared State');
            $rootScope.sharedState.driverPage = DriverService.get(params);
        };

        $scope.findAgents = function () {
            $log.debug('Calling Agent Service Get To Get An AgentPage To Set On The $rootScope Shared State');
            $rootScope.sharedState.agentPage = AgentService.get(params);
        };

        $scope.findRoles = function () {
            $log.debug('Calling User Service Find Roles To Get Roles And Set Them On The $rootScope Shared State ');
            $rootScope.sharedState.roles = UserService.query({pathVariable: 'role'});
            $log.debug('The roles will be ' + JSON.stringify($rootScope.sharedState.roles));
        };

        $scope.init = function () {
            $scope.findCompanies();
            $scope.findVehicles();
            $scope.findDrivers();
            $scope.findAgents();
            $scope.findRoles();
        };

        $scope.init();
    }).controller('DriverController', function ($rootScope, $scope, $log, DriverService, FormSubmissionUtilService, RESULT_SIZE) {
    $rootScope.addUrl = 'driver-add';
    $scope.new = true;
    $scope.driver = {bookedRanges: []};
    $scope.prestineDriver = angular.copy($scope.driver);
    $scope.skip = 0;
    $scope.limit = RESULT_SIZE;

    $scope.canSave = function () {
        return FormSubmissionUtilService.canSave($scope.addForm);
    };

    $scope.saveClick = function () {
        if ($scope.company) {
            $scope.driver.companyId = $scope.company.reference;
            $scope.driver.companyName = $scope.company.tradingAs;
        }
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
        $log.debug("Reverting The Driver To It's Pristine State");
        $scope.driver = angular.copy($scope.prestineDriver);
    };

    $scope.bookableList = function () {
        $log.debug('Finding Bookable Drivers ');
        $scope.bookable = DriverService.query({
            pathVariable: 'bookable',
            _companyId: $scope.companyId,
            bookableFrom: $scope.bookableFrom,
            bookableTo: $scope.bookableTo,
            skip: $scope.skip,
            limit: $scope.limit
        });
    }

    $scope.list();
}).controller('TripController', function ($rootScope, $scope, TripService, DriverService, VehicleService, FormSubmissionUtilService, $log, RESULT_SIZE) {
    $rootScope.addUrl = 'trip-add';
    $scope.trip = {price: {currency: {}}};
    $scope.prestineTrip = angular.copy($scope.trip);
    $scope.new = true;
    $scope.skip = 0;
    $scope.limit = RESULT_SIZE;
    $scope.currencyCodes = ['R'];
    $scope.bookableDrivers = {};
    $scope.bookableVehicles = {};

    $scope.canSave = function () {
        return FormSubmissionUtilService.canSave($scope.addForm);
    };

    $scope.saveClick = function () {

        if ($scope.from && $scope.to) {
            $scope.trip.bookedRange = {from: $scope.from, to: $scope.to};
            $log.debug("{BookedRange:{from:" + $scope.trip.bookedRange.from + ", to:" + $scope.trip.bookedRange.to);
        }

        if ($scope.company) {
            var companyReference = $scope.company.reference;
            var companyName = $scope.company.tradingAs;
            $log.debug('Setting The Trip Company Id To ' + companyReference + ' Company Name To ' + companyName);
            $scope.trip.companyId = companyReference;
            $scope.trip.companyName = companyName;
        }
        if ($scope.driver) {
            var driverReference = $scope.driver.reference;
            var driverName = $scope.driver.firstName + ' ' + $scope.driver.surname;
            $scope.trip.driverId = driverReference;
            $scope.trip.driverName = driverName;
        }
        if ($scope.vehicle) {
            var vehicleName = $scope.vehicle.make + ' ' + $scope.vehicle.model + ' ' + $scope.vehicle.licenseNumber;
            var vehicleReference = $scope.vehicle.reference;
            $scope.trip.vehicleName = vehicleName;
            $scope.trip.vehicleId = vehicleReference;
        }
        if ($scope.new === true) {
            console.log('About To Post Since This Is A New Trip ' + JSON.stringify($scope.trip));
            $scope.savedTrip = TripService.save($scope.trip, function (data) {
                $scope.list();
            });
        }
    };

    $scope.price = function () {
        console.log('Calculating The Price .');
        $scope.trip.price.amount = $scope.trip.pricePerKm * $scope.trip.distance;
    };

    $scope.list = function () {
        var params = {skip: $scope.skip, limit: $scope.limit};
        $log.debug('Calling Get With ' + params);
        $scope.page = TripService.get(params);
    };

    $scope.reset = function () {
        $log.debug('Reverting The Trip To Its Pristine State');
        $scope.trip = angular.copy($scope.prestineTrip);
    };

    $scope.findBookableDriversAndVehicles = function () {

        $log.debug('Attempting To Find The Bookable Drivers And The Bookable Vehicles, company ');

        if ($scope.from && $scope.to && $scope.company) {
            $log.debug('I Am Going Ahead Finding The Bookable Drivers And The Bookable Vehicles As All From, To And Company Have Values');

            var params = {
                pathVariable: 'bookable',
                _companyId: $scope.company.reference,
                bookableFrom: $scope.from.toISOString(),
                bookableTo: $scope.to.toISOString(),
                skip: 0,
                limit: 100
            };

            $log.debug("from "+params.bookableFrom+" to "+params.bookableTo+" company id"+params._companyId);
            $scope.bookableDrivers = DriverService.query(params);
            $scope.bookableVehicles = VehicleService.query(params);
        } else {
            $log.debug('I Am Not Finding Any Bookable Driver And / Or Bookable Vehicles As Either From, To Or Company Are Null');
        }
    };

    $scope.list();
}).controller('ReviewController', function ($scope, $log, $rootScope, ReviewService, FormSubmissionUtilService, RESULT_SIZE) {

    $scope.review = {};
    $scope.prestineReview = angular.copy($scope.review);
    $scope.new = true;
    $scope.skip = 0;
    $scope.limit = RESULT_SIZE;
    $rootScope.addUrl = 'review-add';

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
        $log.debug('Reverting The Review To The Pristine State');
        $scope.review = angular.copy($scope.prestineReview);
    };

    $scope.list();
}).controller('CompanyController', function ($scope, $log, CompanyService, FormSubmissionUtilService, RESULT_SIZE,
                                             $rootScope) {
    $rootScope.addUrl = 'company-add';

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
        console.log('Saving The Company. Please Wait...');

        if ($scope.agent) {
            $scope.company.agentName = $scope.agent.fullName;
            $scope.company.agentId = $scope.agent.reference;
        }
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
}).controller('UserController', function ($scope, $log, UserService, CountryService, FormSubmissionUtilService,
                                          CompanyService, RESULT_SIZE, $rootScope) {
    $rootScope.addUrl = 'signup';
    $scope.range = {};
    $scope.range.skip = 0;
    $scope.countries = CountryService.countries();
    $scope.range.limit = RESULT_SIZE;
    $scope.user = {};
    $scope.formHolder = {};
    $scope.prestineUser = angular.copy($scope.user);
    $scope.confirmPasswordPrestine = angular.copy($scope.confirmPassword);
    $scope.new = true;
    $scope.roles = [{role: 'ROLE_admin'}, {role: 'ROLE_agent'}, {role: 'ROLE_world'}, {role: 'ROLE_companyUser'}];

    $scope.saveClick = function () {
        $log.debug('Clicked Submit Button. This Be The Authorities Submitted ' + $scope.user.authority);
        $scope.user.username = $scope.user.email;

        if ($scope.user.company) {
            var companyName = $scope.user.company.tradingAs;
            var companyReference = $scope.user.company.reference;
            $log.debug('Setting Company Name to ' + companyName + ' Company Reference To ' + companyReference);
            $scope.user.companyName = companyName;
            $scope.user.companyId = companyReference;
        } else {
            $log.debug("Did not set the Company Name Or Company Id As There Is No company on the scope");
        }

        if ($scope.user.authority) {
            $scope.user.authorities = [$scope.user.authority];
            $log.debug('Set The Authority To ' + $scope.user.authority);
        } else {
            $log.debug('There Are No Authorities Defined On The Scope');
        }

        $log.debug('This Be The Authorities Submitted With The User ' + $scope.user.authorities);

        UserService.save($scope.user, function (data) {
            $log.info('Submitted To The Rest Service With The Role ' + $scope.user.authorities + 'Got Back Id ' + data.id);
            $scope.list();
        });

    };

    $scope.canSave = function () {
        return FormSubmissionUtilService.canSave($scope.formHolder.addForm) && $scope.user.password === $scope.formHolder.confirmPassword;
    };

    $scope.list = function () {
        var params = {skip: $scope.range.skip, limit: $scope.range.limit};
        $log.debug('Calling Get With The Params {skip:' + params.skip + ', limit:' + params.limit + '}');
        $scope.page = UserService.get(params);
    };

    $scope.reset = function () {

        $log.debug("Reverting The User To It's Pristine State");
        $scope.user = angular.copy($scope.prestineUser);
        $scope.formHolder.confirmPassword = angular.copy($scope.confirmPasswordPrestine);
    };

    $scope.canChangePassword = function () {
        return FormSubmissionUtilService.canSave($scope.changePasswordForm);
    }
    $scope.changePassword = function () {

        UserService.update({
            username: $scope.username,
            currentPassword: $scope.currentPassword,
            newPassword: $scope.newPassword
        });
    }

    $scope.list();
}).controller('VehicleController', function ($scope, $log, FormSubmissionUtilService, VehicleService, RESULT_SIZE, $rootScope) {
    $scope.skip = 0;
    $scope.vehicleTypes = ['Bakkie', 'Bus', 'Hatch Back', 'Lorry', 'Mini Bus', 'Sedan', 'Station Wagon', 'SUV', 'Truck', 'Van'];
    $scope.limit = RESULT_SIZE;
    $scope.vehicle = {};
    $scope.prestineVehicle = angular.copy($scope.vehicle);
    $rootScope.addUrl = 'vehicle-add';

    $scope.list = function () {
        var params = {skip: $scope.skip, limit: $scope.limit};
        $log.debug('Calling page with the params ' + params);
        $scope.page = VehicleService.get(params);
    };

    $scope.canSave = function () {
        return FormSubmissionUtilService.canSave($scope.addForm);
    };

    $scope.saveClick = function () {
        $log.debug('Posting The Vehicle ' + $scope.vehicle);
        if ($scope.company) {
            var companyName = $scope.company.tradingAs;
            var companyId = $scope.company.reference;
            $log.debug('Setting The Vehicle Company Name To'+companyName+' And The Company Id To '+companyId+' company ref is '+$scope.company.reference);
            $scope.vehicle.companyId = companyId;
            $scope.vehicle.companyName = companyName;
        }
        VehicleService.save($scope.vehicle, function (data) {
            $scope.list();
        });
    };

    $scope.reset = function () {
        $scope.vehicle = angular.copy($scope.prestineVehicle);
    };

    $scope.bookableList = function () {

        $scope.bookable = VehicleService.query({
            pathVariable: 'bookable',
            _companyId: $scope.companyId,
            bookableFrom: $scope.bookableFrom,
            bookableTo: $scope.bookableTo,
            skip: $scope.skip,
            limit: $scope.limit
        });
    }

    $scope.list();
}).controller('AgentController', function ($scope, AgentService, FormSubmissionUtilService, RESULT_SIZE,
                                           $log, $rootScope) {
    $rootScope.addUrl = 'agent-add';

    $scope.agent = {};
    $scope.pristineAgent = angular.copy($scope.agent);
    $scope.skip = 0;
    $scope.limit = RESULT_SIZE;

    $scope.list = function () {
        $scope.page = AgentService.get({skip: $scope.skip, limit: $scope.limit});
    };

    $scope.canSave = function () {
        return FormSubmissionUtilService.canSave($scope.addForm);
    };

    $scope.saveClick = function () {
        $log.debug('Attempting To Save An Agent. Please Wait...');
        $scope.recentlySaved = AgentService.save($scope.agent, function (data) {
            $log.debug('Successfully saved The Agent And Got Back ' + data);
            $scope.list();
        });
    };

    $scope.reset = function () {
        $scope.agent = $scope.pristineAgent;
    };

    $scope.list();

}).constant('RESULT_SIZE', 100);

