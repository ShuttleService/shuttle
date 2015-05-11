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

    it('Should Call Get On Trip Service With Params On The Scope And Set The Result Page On The Scope', function () {

        var controller = $controller('TripController', {
            $scope: $scope
        });

        expect(controller).toBeDefined();

        var page = {content: ['', 1, "3"]};

        spyOn(TripService, 'get').andReturn(page);
        var skip = 3;
        var limit = 5;
        $scope.skip = skip;
        $scope.limit = limit;

        var params = {skip: skip, limit: limit};
        $scope.list();

        expect(TripService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
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

    it('saveClick Should Set The Company Id And Company Name To The Values On The $scope.company', function () {

        $scope.company = company;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();

        expect($scope.trip.companyId).toEqual(company.id);
        expect($scope.trip.companyName).toEqual(company.tradingAs);
    });

    it('saveClick Should Set The Driver Id And Driver Name To The Values On $scope.driver', function () {
        var driver = {
            id: 'Test Driver Id To Be Assigned The trip.driverId',
            firstName: 'Test Driver Name To Be Assigned To The trip.driverName',
            surname: 'Test Driver Surname To Be Assigned To The trip.driver.name '
        };
        $scope.driver = driver;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();
        expect($scope.trip.driverId).toEqual(driver.id);
        expect($scope.trip.driverName).toEqual(driver.firstName + ' ' + driver.surname);
    });

    it('saveClick Should Set The Vehicle Id And Vehicle Name To Values on $scope.vehicle', function () {
        var vehicle = {
            id: 'Test Vehicle Id To Be Assigned To trip.vehicleId',
            make: 'Test Vehicle Make To Be Assigned To trip.vehicleMake',
            model: 'Test Vehicle Model To Be Assigned To Vehicle Name',
            licenseNumber: 'Test Vehicle License Number To Be Assigned to trip.vehicleLicenseNumber'
        };
        $scope.vehicle = vehicle;

        $controller('TripController', {
            $scope: $scope
        });

        $scope.saveClick();
        expect($scope.trip.vehicleId).toEqual(vehicle.id);
        expect($scope.trip.vehicleName).toEqual(vehicle.make + ' ' + vehicle.model + ' ' + vehicle.licenseNumber);

    });

    it('Should Call TripService Save When $scope.new is true', function () {

        $controller('TripController', {
            $scope: $scope
        });

        $scope.new = true;
        var trip = {source: 'Test Trip Source To POST'};
        var savedTrip = {source: 'Test Trip Source POSTED'};

        $scope.trip = trip;

        spyOn(TripService, 'save').andReturn(savedTrip);
        spyOn($scope, 'list');
        expect($scope.saveClick).toBeDefined();

        $scope.saveClick();

        expect(TripService.save).toHaveBeenCalledWith(trip, jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Should Calculate The Price Per Km And The Distance And Set The Price On The Scope', function () {

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

    it('Should Call List On The Service With The Given skip and limit and Save The Page On The Scope', function () {
        var skip = 0;
        var limit = 10;
        $controller('TripController', {
            $scope: $scope
        });
        var params = {skip: skip, limit: limit};
        var page = {size: limit};
        spyOn(TripService, 'get').andReturn(page);
        expect($scope.list).toBeDefined();

        $scope.limit = limit;
        $scope.skip = skip;
        $scope.list();
        expect(TripService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toBeDefined(page);
    });

    it('Should Set Skip And Limit On The $scope At Startup', function () {
        $controller('TripController', {$scope: $scope});

        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Revert The Trip To The Prestine State', function () {
        $controller('TripController', {
            $scope: $scope
        });

        var prestine = $scope.trip;
        $scope.trip = {name: 'Test Trip To Be Reverted'};
        expect($scope.trip).toBeDefined();
        $scope.reset();
        expect($scope.trip).toEqual(prestine);
    });

    it('Should Set The List Of Currencies On The Scope. Should this be on the Front End?', function () {
        $controller('TripController', {$scope: $scope});
        expect($scope.currencyCodes.length).toEqual(1);
    });

    it('Should Define The Currency on the Trip Price', function () {

        $controller('TripController', {
            $scope: $scope
        });
        expect($scope.trip.price.currency).toBeDefined();
    });

    it('Should Set A Booked Range On The Trip Given A From Date And A To Date ', function () {

        $controller('TripController', {
            $scope: $scope
        });

        expect($scope.trip.bookedRange).toBeUndefined();
        $scope.from = new Date();
        $scope.to = {date: 'Fake date'};

        var bookedRange = {from: $scope.from, to: $scope.to};

        $scope.saveClick();

        expect($scope.trip.bookedRange).toEqual(bookedRange);

    });

    it('When The From Date Is Not Set. No Call Should Be Made To Find Bookable Drivers And Bookable Vehicles', function () {

        $controller('TripController', {$scope: $scope});
        $scope.to = new Date();
        $scope.company = company;
        spyOn(DriverService, 'query');
        spyOn(VehicleService, 'query');

        $scope.findBookableDriversAndVehicles();

        expect(DriverService.query.wasCalled).toBe(false);
        expect(VehicleService.query).not.toHaveBeenCalled();
        expect($scope.bookableDrivers).toEqual({});
        expect($scope.bookableDrivers).toEqual({});

    });

    it('When The To Date Is Not Set. No Call Should Be Made To Find Bookable Drivers And Bookable Vehicles', function () {
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

    it('When The Company Is Not Set. No Call Should Be Made To Find Bookable Drivers And Bookable Vehicles', function () {
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
    it('Should Find A List Of Bookable Drivers When The Company, From And To Are All Set', function () {
        $controller('TripController', {$scope: $scope});
        $scope.from = new Date(1978, 09, 07);
        $scope.to = new Date();
        $scope.company = company;

        var bookableDrivers = ['Driver 1', 'Driver 2'];
        var bookableVehicles = ['Vehicle'];

        spyOn(DriverService, 'query').andReturn(bookableDrivers);
        spyOn(VehicleService, 'query').andReturn(bookableVehicles);

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
