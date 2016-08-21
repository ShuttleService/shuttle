describe('Vehicle Service Test', function () {
    var VehicleService;
    var $httpBackend;
    var skip = 0;
    var limit = 10;

    beforeEach(module('services'));

    beforeEach(inject(function (_VehicleService_, _$httpBackend_) {
        VehicleService = _VehicleService_;
        $httpBackend = _$httpBackend_;

        expect(VehicleService).toBeDefined();
        expect($httpBackend).toBeDefined();
    }));

    it('Verifies That The $resource Methods Are Defined On The Service', function () {
        expect(VehicleService.get).toBeDefined();
        expect(VehicleService.query).toBeDefined();
        expect(VehicleService.save).toBeDefined();
        expect(VehicleService.delete).toBeDefined();
        expect(VehicleService.remove).toBeDefined();
    });

    it('Calls Get With The Given Skip And Limit', function () {
        var url =  '/vehicle/' + skip + '/' + limit;
        var page = {size: limit};
        $httpBackend.expectGET(url).respond(page);

        var actual = VehicleService.get({skip: skip, limit: limit});
        $httpBackend.flush();
        expect(actual.size).toEqual(page.size);
    });

    it('Gets A Set Of Bookable Vehicles With The Given BookedRange, skip and limit', function () {
        var from = new Date().toISOString();
        var to = new Date().toISOString();
        console.log("From Date " + from + " ,To Date " + to);
        var pathVariable = 'bookable';
        var companyId = 'TestCompanyId';
        var url =  '/vehicle/' + pathVariable + '/' +companyId+'/'+ from + '/' + to + '/' + skip + '/' + limit;
        var bookableDrivers = [{name: 'Test Driver Name'}];

        $httpBackend.expectGET(url).respond(bookableDrivers);
        var actual = VehicleService.query({
            pathVariable: pathVariable,_companyId:companyId,bookableFrom: from, bookableTo: to, skip: skip, limit: limit
        });

        $httpBackend.flush();
        expect(actual.size).toEqual(bookableDrivers.size);
        expect(actual[0].name).toEqual(bookableDrivers[0].name);

    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
})
;