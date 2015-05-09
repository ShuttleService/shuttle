describe('Vehicle Controller Test', function () {

    var $scope;
    var VehicleService;
    var RESULT_SIZE;
    var FormSubmissionUtilService;
    var skip = 0;
    var limit = 10;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope, $controller, _VehicleService_, _FormSubmissionUtilService_, _RESULT_SIZE_) {
        $scope = $rootScope.$new();
        VehicleService = _VehicleService_;
        RESULT_SIZE = _RESULT_SIZE_;
        FormSubmissionUtilService = _FormSubmissionUtilService_;

        expect($scope).toBeDefined();
        expect(VehicleService).toBeDefined();
        expect(RESULT_SIZE).toBeDefined();
        expect(FormSubmissionUtilService).toBeDefined();
        var controller = $controller('VehicleController', {$scope: $scope});
        expect(controller).toBeDefined();
    }));

    it('Should Initialize The Controller With The Skip And The Limit ', function () {
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Should Initialize The Controller With The Vehicle', function () {
        expect($scope.vehicle).toBeDefined();
    });

    it('Should Initialize The Controller With A List Of Vehicle Types', function () {
        expect($scope.vehicleTypes.length >= 10).toEqual(true);
    });

    it('Should Call The Service Get With The Given Skip And Limit And Set The Returned Page On The Scope', function () {

        $scope.skip = skip;
        $scope.limit = limit;
        var page = {size: limit};

        spyOn(VehicleService, 'get').andReturn(page);

        expect($scope.page).toBeDefined();
        $scope.list();

        var params = {skip: skip, limit: limit};
        expect(VehicleService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Should Assign The vehicle.companyName To The $scope.company name And vehicle.companyId To $scope.company.id', function () {
        var company = {
            id: 'Test Company Id To Be Set On The vehicle.companyId',
            tradingAs: 'Test Company Name To Be Set On vehicle.companyName'
        };
        $scope.company = company;
        $scope.saveClick();
        expect($scope.vehicle.companyId).toEqual(company.id);
        expect($scope.vehicle.companyName).toEqual(company.tradingAs);
    });

    it('Should Call VehicleService Save With The Vehicle On The Scope And Call Page AfterWards', function () {

        expect($scope.saveClick).toBeDefined();
        var page = {size: 'Page'};
        spyOn(VehicleService, 'save');
        spyOn($scope, 'list').andReturn(page);

        $scope.saveClick();
        expect(VehicleService.save).toHaveBeenCalledWith($scope.vehicle, jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Should Revert The Vehicle Back To It\'s Prestine State', function () {
        var prestine = $scope.vehicle;
        $scope.vehicle = {name: 'Vehicle Name To Be Reverted'};
        expect($scope.reset).toBeDefined();
        $scope.reset();
        expect($scope.vehicle).toEqual(prestine);
    });

    it('Should Call The FormSubmissionUtilService Can Save With The Form', function () {
        $scope.addForm = {$valid: true};
        spyOn(FormSubmissionUtilService, 'canSave').andReturn(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
        expect(actual).toBeTruthy();
    });

    it('Should Call Service query With Bookable From And To Dates And Set The List Of Bookable Vehicles On The $scope',

        function () {
            var from = new Date();
            var to = new Date();
            var companyId = 'Test Company Id';

            $scope.bookableFrom = from;
            $scope.bookableTo = to;
            $scope.skip = skip;
            $scope.limit = limit;
            $scope.companyId = companyId;

            var bookable = ['Bookable Vehicle 1', 'Bookable Vehicle 2'];

            expect($scope.bookable).toBeUndefined();
            expect($scope.bookableList).toBeDefined();

            spyOn(VehicleService, 'query').andReturn(bookable);

            $scope.bookableList();

            expect(VehicleService.query).toHaveBeenCalledWith({
                pathVariable: 'bookable',
                _companyId: companyId,
                bookableFrom: from,
                bookableTo: to,
                skip: skip,
                limit: limit
            });

            expect($scope.bookable).toBeDefined();
            expect($scope.bookable).toEqual(bookable);
        });

});