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

    it('Initializes The Controller With The Skip And The Limit ', function () {
        expect($scope.skip).toEqual(0);
        expect($scope.limit).toEqual(RESULT_SIZE);
    });

    it('Initializes The Controller With The Vehicle', function () {
        expect($scope.vehicle).toBeDefined();
    });

    it('Initializes The Controller With A List Of Vehicle Types', function () {
        expect($scope.vehicleTypes.length >= 10).toEqual(true);
    });

    it('Calls The Service Get With The Given Skip And Limit And Set The Returned Page On The Scope', function () {

        $scope.skip = skip;
        $scope.limit = limit;
        var page = {size: limit};

        spyOn(VehicleService, 'get').and.returnValue(page);

        expect($scope.page).toBeDefined();
        $scope.list();

        var params = {skip: skip, limit: limit};
        expect(VehicleService.get).toHaveBeenCalledWith(params);
        expect($scope.page).toEqual(page);
    });

    it('Assigns The vehicle.companyName To The $scope.company name And vehicle.companyId To $scope.company.id', function () {
        var company = {
            reference: 'Test Company Id To Be Set On The vehicle.companyId',
            tradingAs: 'Test Company Name To Be Set On vehicle.companyName'
        };
        $scope.company = company;
        $scope.saveClick();
        expect($scope.vehicle.companyId).toEqual(company.reference);
        expect($scope.vehicle.companyName).toEqual(company.tradingAs);
    });

    it('Calls VehicleService Save With The Vehicle On The Scope And Call Page AfterWards', function () {

        expect($scope.saveClick).toBeDefined();
        var page = {size: 'Page'};
        spyOn(VehicleService, 'save');
        spyOn($scope, 'list').and.returnValue(page);

        $scope.saveClick();
        expect(VehicleService.save).toHaveBeenCalledWith($scope.vehicle, jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Reverts The Vehicle Back To It\'s Pristine State', function () {
        var prestine = $scope.vehicle;
        $scope.vehicle = {name: 'Vehicle Name To Be Reverted'};
        expect($scope.reset).toBeDefined();
        $scope.reset();
        expect($scope.vehicle).toEqual(prestine);
    });

    it('Calls The FormSubmissionUtilService Can Save With The Form', function () {
        $scope.addForm = {$valid: true};
        spyOn(FormSubmissionUtilService, 'canSave').and.returnValue(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
        expect(actual).toBeTruthy();
    });

    it('Calls Service query With Bookable From And To Dates And Set The List Of Bookable Vehicles On The $scope',

        function () {
            var from = new Date();
            var to = new Date();
            var companyReference = 'Test Company Id';

            $scope.bookableFrom = from;
            $scope.bookableTo = to;
            $scope.skip = skip;
            $scope.limit = limit;
            $scope.companyId = companyReference;

            var bookable = ['Bookable Vehicle 1', 'Bookable Vehicle 2'];

            expect($scope.bookable).toBeUndefined();
            expect($scope.bookableList).toBeDefined();

            spyOn(VehicleService, 'query').and.returnValue(bookable);

            $scope.bookableList();

            expect(VehicleService.query).toHaveBeenCalledWith({
                pathVariable: 'bookable',
                _companyId: companyReference,
                bookableFrom: from,
                bookableTo: to,
                skip: skip,
                limit: limit
            });

            expect($scope.bookable).toBeDefined();
            expect($scope.bookable).toEqual(bookable);
        });

});