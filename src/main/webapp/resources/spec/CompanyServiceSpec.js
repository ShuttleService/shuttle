describe('The Company Service Test', function () {

    var CompanyService;
    var $httpBackend;
    var CONTEXT_ROOT;

    beforeEach(module('services'));

    beforeEach(inject(function (_CompanyService_, _$httpBackend_, _CONTEXT_ROOT_) {

        CompanyService = _CompanyService_;
        $httpBackend = _$httpBackend_;
        CONTEXT_ROOT = _CONTEXT_ROOT_;

        expect(CompanyService).toBeDefined();
        expect($httpBackend).toBeDefined();
        expect(CONTEXT_ROOT).toBeDefined();
    }));

    it('Should Verify That The $resource Methods Are exposed', function () {

        expect(CompanyService.get).toBeDefined();
    });

    it('Should Call Get And Return The Got Company', function () {
    });

    it('Should Post The Company To The Correct URL', function () {

        var company = {name: 'Test Company Name', id: 'Test Company Id'};

        var url = CONTEXT_ROOT + '/company';

        $httpBackend.expectPOST(url, company).respond(company);

        var actualCompany = CompanyService.post(url, company);
        $httpBackend.flush();
        expect(actualCompany).toEqual(company);

    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });
});