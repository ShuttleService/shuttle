/**
 * Created by zorodzayi on 14/10/11.
 */
describe('Testing The Review Service', function () {

    var ReviewService;
    var CONTEXT_ROOT;
    var $httpBackend;

    beforeEach(module('services'));

    beforeEach(inject(function (_ReviewService_, _CONTEXT_ROOT_, _$httpBackend_) {

        ReviewService = _ReviewService_;
        CONTEXT_ROOT = _CONTEXT_ROOT_;
        $httpBackend = _$httpBackend_;
    }));

    it('Should Verify The $resource Methods Are Defined', function () {

        expect(ReviewService.query).toBeDefined();
    });

    it('Should Call The List Review Rest Service', function () {

        var review = 'Test Review';
        var reviews = [review];
        var skip = 1;
        var limit = 3;

        $httpBackend.whenGET(CONTEXT_ROOT + '/review/' + skip + '/' + limit).respond(reviews);
        var result = ReviewService.query({skip:skip,limit:limit});
        $httpBackend.flush();
        expect(result[0]).toEqual(review);

    });

    afterEach(function () {

            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        }
    );
});
