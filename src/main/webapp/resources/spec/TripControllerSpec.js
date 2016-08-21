/**
 * Created by zorodzayi on 14/10/12.
 */
describe('Testing The Trip Controller', function () {

    var $scope;
    var TripService;
    var $controller;
    var DriverService;
    var VehicleService;
    var company = {
        id: 'Test Company Id To Be Assigned The trip.companyId',
        reference: "Test Id String",
        tradingAs: 'Test Company Name To Be Assigned To trip.companyName'
    };

    var FormSubmissionUtilService;
    var RESULT_SIZE;

    beforeEach(module('controllers'));

    beforeEach(inject(function (_$rootScope_, _$controller_, _TripService_, _FormSubmissionUtilService_, _RESULT_SIZE_, _DriverService_, _VehicleService_) {

        $scope = _$rootScope_.$new();
        TripService = _TripService_;
        $controller = _$controller_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;
        RESULT_SIZE = _RESULT_SIZE_;
        VehicleService = _VehicleService_;
        DriverService = _DriverService_;

        expect($scope).toBeDefined();
        expect(TripService).toBeDefined();
        expect($controller).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
        expect(DriverService).toBeDefined();
        expect(VehicleService).toBeDefined();

    }));

    it('Calls Get On Trip Service With Params On The Scope And Set The Result Page On The Scope', function () {

        var controller = $controller('TripController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();

        var page = {content: ['', 1, "3"]};

        spyOn(TripService, 'get').and.returnValue(page);
        var skip = 3;
        var limit = 5;
        $scope.skip = skip;
        $scope.limit = limit;

        var params = {skip: skip, limit: limit};
        $scope.list();

        expect(TripService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Has A trip on The $scope', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.trip).toBeDefined();
    });

    it('Calls canSave On The FormSubmissionUtilService', function () {

        $controller('TripController', {
            $scope: $scope
        });

        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(false);

        expect($scope.canSave).toBeDefined();
        var actual = $scope.canSave();
        expect(actual).toEqual(false);
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
    });

    it('saveClick Sets The Company reference And Company Name To The Values On The $scope.company', function () {

        $scope.company = company;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();

        expect($scope.trip.companyId).toEqual(company.reference);
        expect($scope.trip.companyName).toEqual(company.tradingAs);
    });

    it('saveClick Should Set The Driver Id to Driver Reference And Driver Name To The Values On $scope.driver', function () {
        var driver = {
            reference: 'Test Driver Id To Be Assigned The trip.driverId',
            firstName: 'Test Driver Name To Be Assigned To The trip.driverName',
            surname: 'Test Driver Surname To Be Assigned To The trip.driver.name '
        };
        $scope.driver = driver;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();
        expect($scope.trip.driverId).toEqual(driver.reference);
        expect($scope.trip.driverName).toEqual(driver.firstName + ' ' + driver.surname);
    });

    it('saveClick Sets The Vehicle Reference And Vehicle Name From Values on $scope.vehicle', function () {
        var vehicle = {
            reference: 'Test Vehicle Reference To Be Assigned To trip.vehicleId',
            make: 'Test Vehicle Make To Be Assigned To trip.vehicleMake',
            model: 'Test Vehicle Model To Be Assigned To Vehicle Name',
            licenseNumber: 'Test Vehicle License Number To Be Assigned to trip.vehicleLicenseNumber'
        };
        $scope.vehicle = vehicle;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();
        expect($scope.trip.vehicleId).toEqual(vehicle.reference);
        expect($scope.trip.vehicleName).toEqual(vehicle.make + ' ' + vehicle.model + ' ' + vehicle.licenseNumber);

    });

    it('Calls TripService Save When $scope.new is true', function () {

        $controller('TripController', {
            $scope: $scope
        });

        $scope.new = true;
        var trip = {source: 'Test Trip Source To POST'};
        var savedTrip = {source: 'Test Trip Source POSTED'};

        $scope.trip = trip;

        spyOn(TripService, 'save').and.returnValue(savedTrip);
        spyOn($scope, 'list');
        expect($scope.saveClick).toBeDefined();

        $scope.saveClick();

        expect(TripService.save).toHaveBeenCalledWith(trip, jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Calculates The Price Per Km And The Distance And Set The Price On The Scope', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.price).toBeDefined();

        var pricePerKm = 11;
        var distance = 13;

        $scope.trip.distance = distance;
        $scope.trip.pricePerKm = pricePerKm;

        $scope.price();

        expect($scope.trip.price.amount).toEqual(distance * pricePerKm);
    });

    it('Calls List On The Service With The Given skip and limit and Save The Page On The Scope', function () {
        var skip = 0;
        var limit = 10;
        $controller('TripController', {
            $scope: $scope
        });
        var params = {skip: skip, limit: limit};
        var page = {size: limit};
        spyOn(TripService, 'get').and.returnValue(page);
        expect($scope.list).toBeDefined();

        $scope.limit = limit;
        $scope.skip = skip;
        $scope.list();
        expect(TripService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toBeDefined(page);
    });

    it('Sets Skip And Limit On The $scope At Startup', function () {
        $controller('TripController', {$scope: $scope});

        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Reverts The Trip To The Pristine State', function () {
        $controller('TripController', {
            $scope: $scope
        });

        var pristine = $scope.trip;
        $scope.trip = {name: 'Test Trip To Be Reverted'};
        expect($scope.trip).toBeDefined();
        $scope.reset();
        expect($scope.trip).toEqual(pristine);
    });

    it('Sets The List Of Currencies On The Scope. Should this be on the Front End?', function () {
        $controller('TripController', {$scope: $scope});
        expect($scope.currencyCodes.length).toEqual(1);
    });

    it('Defines The Currency on the Trip Price', function () {

        $controller('TripController', {
            $scope: $scope
        });
        expect($scope.trip.price.currency).toBeDefined();
    });

    it('Sets A Booked Range On The Trip Given A From Date And A To Date ', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.trip.bookedRange).toBeUndefined();
        $scope.from = new Date(2015, 4, 12, 8, 16, 27, 0);
        $scope.to = new Date(2015, 4, 12, 8, 21, 27, 0);

        var bookedRange = {from: $scope.from, to: $scope.to};

        $scope.saveClick();

        expect($scope.trip.bookedRange.from).toEqual(bookedRange.from);
        expect($scope.trip.bookedRange.to).toEqual(bookedRange.to);

    });

    it('When The From Date Is Not Set. No Call Is Be Made To Find Bookable Drivers And Bookable Vehicles', function () {

        $controller('TripController', {$scope: $scope});
        $scope.to = new Date();
        $scope.company = company;
        spyOn(DriverService, 'query');
        spyOn(VehicleService, 'query');

        $scope.findBookableDriversAndVehicles();

        expect(DriverService.query).not.toHaveBeenCalled();
        expect(VehicleService.query).not.toHaveBeenCalled();
        expect($scope.bookableDrivers).toEqual({});
        expect($scope.bookableDrivers).toEqual({});

    });

    it('When The To Date Is Not Set. No Call Is Made To Find Bookable Drivers And Bookable Vehicles', function () {
        $controller('TripController', {$scope: $scope});
        $scope.from = new Date();
        $scope.company = company;
        spyOn(DriverService, 'query');
        spyOn(VehicleService, 'query');

        $scope.findBookableDriversAndVehicles();

        expect(DriverService.query).not.toHaveBeenCalled();
        expect(VehicleService.query.wasCalled).toBeFalsy();
        expect($scope.bookableDrivers).toEqual({});
        expect($scope.bookableVehicles).toEqual({});
    });

    it('When The Company Is Not Set. No Call Is Be Made To Find Bookable Drivers And Bookable Vehicles', function () {
        $controller('TripController', {$scope: $scope});
        $scope.from = new Date();
        $scope.to = new Date();

        spyOn(DriverService, 'query');
        spyOn(VehicleService, 'query');

        $scope.findBookableDriversAndVehicles();

        expect(DriverService.query).not.toHaveBeenCalled();
        expect(VehicleService.query).not.toHaveBeenCalled();
        expect($scope.bookableDrivers).toEqual({});
        expect($scope.bookableVehicles).toEqual({});
    });
    it('Finds A List Of Bookable Drivers When The Company, From And To Are All Set', function () {
        $controller('TripController', {$scope: $scope});
        $scope.from = new Date(1978, 09, 07);
        $scope.to = new Date();
        $scope.company = company;

        var bookableDrivers = ['Driver 1', 'Driver 2'];
        var bookableVehicles = ['Vehicle'];

        spyOn(DriverService, 'query').and.returnValue(bookableDrivers);
        spyOn(VehicleService, 'query').and.returnValue(bookableVehicles);

        $scope.findBookableDriversAndVehicles();

        var params = {
            pathVariable: 'bookable',
            _companyId: $scope.company.reference,
            bookableFrom: $scope.from.toISOString(),
            bookableTo: $scope.to.toISOString(),
            skip: 0,
            limit: 100
        };

        expect(DriverService.query).toHaveBeenCalledWith(params);
        expect(VehicleService.query).toHaveBeenCalledWith(params);
        expect($scope.bookableDrivers).toEqual(bookableDrivers);
        expect($scope.bookableVehicles).toEqual(bookableVehicles);

    })
});
