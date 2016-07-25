/**
 * Created by zorodzayi on 14/10/11.
 */
describe('Testing The Trip Service', function () {

    var TripService;
    var $httpBackend;

    beforeEach(module('services'));

    beforeEach(inject(function (_TripService_, _$httpBackend_) {

        TripService = _TripService_;
        $httpBackend = _$httpBackend_;

        expect(TripService).toBeDefined();
        expect($httpBackend).toBeDefined();
    }));

    it('Should Verify That The $resource Methods Are Defined ', function () {
        expect(TripService.query).toBeDefined();
    });

    it('Should Call The Trip List Rest Service', function () {
        var trip1 = 'Test Trip';
        var trips = [trip1];
        var skip = 1;
        var limit = 3;

        $httpBackend.whenGET('/trip/' + skip + '/' + limit).respond(trips);
        var result = TripService.query({skip: skip, limit: limit});
        $httpBackend.flush();

        expect(result[0]).toEqual(trip1);
    });

    afterEach(function () {

        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });

});