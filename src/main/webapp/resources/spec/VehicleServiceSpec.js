describe('Vehicle Service Test', function () {
    var VehicleService;
    var $httpBackend;
    var CONTEXT_ROOT;

    beforeEach(module('services'));

    beforeEach(inject(function (_VehicleService_, _$httpBackend_,_CONTEXT_ROOT_) {
        VehicleService = _VehicleService_;
        $httpBackend = _$httpBackend_;
        CONTEXT_ROOT = _CONTEXT_ROOT_;

        expect(VehicleService).toBeDefined();
        expect($httpBackend).toBeDefined();
        expect(CONTEXT_ROOT).toBeDefined();
    }));

    it('Should Verify That The $resource Methods Are Defined On The Service', function () {
        expect(VehicleService.get).toBeDefined();
        expect(VehicleService.query).toBeDefined();
        expect(VehicleService.save).toBeDefined();
        expect(VehicleService.delete).toBeDefined();
        expect(VehicleService.remove).toBeDefined();
    });

    it('Should Call Get With The Given Skip And Limit', function () {

        var skip = 0;
        var limit = 10;
        var url = CONTEXT_ROOT+'/vehicle/'+skip+'/'+limit;
        var page = {size:limit};
        $httpBackend.expectGET(url).respond(page);

        var actual = VehicleService.get({skip:skip,limit:limit});
        $httpBackend.flush();
        expect(actual.size).toEqual(page.size);
    });

    afterEach(function(){
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});