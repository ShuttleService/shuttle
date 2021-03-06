describe('The Company Service Test', function () {

    var CompanyService;
    var $httpBackend;

    beforeEach(module('services'));

    beforeEach(inject(function (_CompanyService_, _$httpBackend_) {

        CompanyService = _CompanyService_;
        $httpBackend = _$httpBackend_;

        expect(CompanyService).toBeDefined();
        expect($httpBackend).toBeDefined();
    }));

    it('Should Verify That The $resource Methods Are exposed', function () {

        expect(CompanyService.get).toBeDefined();
        expect(CompanyService.delete).toBeDefined();
        expect(CompanyService.remove).toBeDefined();
        expect(CompanyService.query).toBeDefined();
        expect(CompanyService.save).toBeDefined();
        expect(CompanyService.put).toBeDefined();

    });

    it('Call Get And Return The Gotten Company', function () {

        var company = {name: 'Test Company Name To Get', id: 'TestCompanyIdToGet'};

        $httpBackend.expectGET('/company/' + company.id).respond(company);
        var actualCompany;

        CompanyService.get({_id: company.id}, function (data) {
            console.log('After Getting The Company The Returned Data ' + data);
            actualCompany = data;
        });

        $httpBackend.flush();

        expect(actualCompany.name).toEqual(company.name);
        expect(actualCompany.id).toEqual(company.id);

    });

    it('Call Get On The Service And Return A List Of Companies', function () {

        var company = {name: 'Test Company Name To Be Gotten In A List', id: 'Test Company Id To Be Gotten In A List'};
        var companyArray = [company];
        var skip = 12;
        var limit = 30;

        $httpBackend.expectGET('/company/' + skip + '/' + limit).respond(companyArray);
        var actualCompanyArray;

        CompanyService.query({skip: skip, limit: limit}, function (data) {
            actualCompanyArray = data;
        });

        $httpBackend.flush();
        expect(actualCompanyArray[0].name).toEqual(company.name);
        expect(actualCompanyArray[0].id).toEqual(company.id);
    });

    it('Post The Company To The Correct URL', function () {

        var company = {name: 'Test Company Name To Post', id: 'Test Company Id To Post'};

        var url =  '/company';

        $httpBackend.expectPOST(url, company).respond(company);

        var actualCompany;

        CompanyService.save(company, function (data) {
            actualCompany = data;
        });

        $httpBackend.flush();

        expect(actualCompany.name).toEqual(company.name);
        expect(actualCompany.id).toEqual(company.id);

    });

    it('Put The Company To The Correct URL', function () {
        var company = {name: 'Test Company Name To Put', id: 'Test Company Id To Put'};

        var url = '/company';

        $httpBackend.expectPUT(url, company).respond(company);
        var actualCompany;

        CompanyService.put(company, function (data) {
            actualCompany = data;
        });

        $httpBackend.flush();

        expect(actualCompany.name).toEqual(company.name);
        expect(actualCompany.id).toEqual(company.id);
    });

    it('Delete The Company On The Correct URL', function () {
        var company = {name: 'Test Company Name To Delete', id: 'TestCompanyIDToDelete'};

        var url =  '/company/' + company.id;

        $httpBackend.expectDELETE(url).respond(company);
        var actualCompany;

        CompanyService.remove({_id: company.id}, function (data) {
            actualCompany = data;
        });

        $httpBackend.flush();

        expect(actualCompany.name).toEqual(company.name);
        expect(actualCompany.id).toEqual(company.id);

    });


    afterEach(function () {
        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });
});