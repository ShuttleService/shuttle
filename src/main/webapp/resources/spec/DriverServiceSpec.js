/**
 * Created by zorodzayi on 14/10/09.
 */

describe('Driver Service Test', function () {

    var $httpBackend;
    var DriverService;
    var skip = 1, limit = 2;

    beforeEach(module('services'));

    beforeEach(inject(function (_$controller_, _DriverService_, _$httpBackend_) {

        DriverService = _DriverService_;
        $httpBackend = _$httpBackend_;

        expect(DriverService).toBeDefined();
        expect($httpBackend).toBeDefined();
    }));

    it('Should Verify That The $resource methods are exposed', function () {
        expect(DriverService.query).toBeDefined();
    });

    it('Should Call The Spring List Rest Service', function () {
        var driver1 = 'Driver 1';
        var driver2 = 'Driver 2';
        var drivers = [driver1, driver2];

        $httpBackend.whenGET( '/driver/' + skip + '/' + limit).respond(drivers);
        var result = DriverService.query({skip: skip, limit: limit});
        $httpBackend.flush();
        expect(result[0]).toEqual(driver1);
        expect(result[1]).toEqual(driver2);
    });

    it('Should Get A Set Of All Bookable Drivers For Given From And To Booking Range', function () {
        var bookableDrivers = [{name: 'Test Bookable Driver Available For Booking'}];
        var from = new Date(2015, 04, 05).toISOString();
        var to = new Date(2015, 04, 06).toISOString();
        var pathVariable = 'bookable';
        var companyId = 'TestCompanyID';

        var url = '/driver/' + pathVariable + '/' + companyId + '/' + from + '/' + to + '/' + skip + '/' + limit;

        $httpBackend.expectGET(url).respond(bookableDrivers);

        var actual = DriverService.query({
            pathVariable: pathVariable,
            _companyId:companyId,
            bookableFrom: from,
            bookableTo: to,
            skip: skip,
            limit: limit
        });

        $httpBackend.flush();

        expect(actual[0].name).toEqual(bookableDrivers[0].name);
    });


    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});