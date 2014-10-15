/**
 * Created by zorodzayi on 14/10/09.
 */

describe('Driver Service Test', function () {

    var $httpBackend;
    var DriverService;
    var CONTEXT_ROOT;

    beforeEach(module('services'));

    beforeEach(inject(function (_$controller_, _DriverService_, _$httpBackend_, _CONTEXT_ROOT_) {

        DriverService = _DriverService_;
        $httpBackend = _$httpBackend_;
        CONTEXT_ROOT = _CONTEXT_ROOT_;

        expect(DriverService).toBeDefined();
        expect($httpBackend).toBeDefined();
        expect(CONTEXT_ROOT).toBeDefined();
    }));

    it('Should Verify That The $resource methods are exposed', function () {
        expect(DriverService.query).toBeDefined();
    });

    it('Should Call The Spring List Rest Service', function () {
        var driver1 = 'Driver 1';
        var driver2 = 'Driver 2';
        var drivers = [driver1,driver2];

        var skip = 1, limit = 2;
        $httpBackend.whenGET(CONTEXT_ROOT + '/driver/' + skip + '/' + limit).respond(drivers);
        var result = DriverService.query({skip: skip, limit: limit});
        $httpBackend.flush();
        expect(result[0]).toEqual(driver1);
        expect(result[1]).toEqual(driver2);
    });

    afterEach(function () {

        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});