describe('Vehicle Controller Test', function () {

    var $scope;
    var VehicleService;
    var RESULT_SIZE;
    var FormSubmissionUtilService;

    beforeEach(module('controllers'));

    beforeEach(inject(function ($rootScope,$controller,_VehicleService_,_FormSubmissionUtilService_,_RESULT_SIZE_) {
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

    it('Should Call The Service Get With The Given Skip And Limit And Set The Returned Page On The Scope', function () {

        var skip = 0;
        var limit = 10;
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

    it('Should Call VehicleService Save With The Vehicle On The Scope And Call Page AfterWards', function () {

        expect($scope.saveClick).toBeDefined();
        var page = {size:'Page'};
        spyOn(VehicleService,'save');
        spyOn($scope,'list').andReturn(page);

        $scope.saveClick();
        expect(VehicleService.save).toHaveBeenCalledWith($scope.vehicle,jasmine.any(Function));
        expect($scope.list).toHaveBeenCalled();
    });

    it('Should Revert The Vehicle Back To It\'s Prestine State',function(){
        var prestine = $scope.vehicle;
        $scope.vehicle = {name:'Vehicle Name To Be Reverted'};
        expect($scope.reset).toBeDefined();
        $scope.reset();
        expect($scope.vehicle).toEqual(prestine);
    });

    it('Should Call The FormSubmissionUtilService Can Save With The Form',function(){

        $scope.addForm = {$valid:true};
        spyOn(FormSubmissionUtilService,'canSave').andReturn(true);
        expect($scope.canSave).toBeDefined();

        var actual = $scope.canSave();
        expect(FormSubmissionUtilService.canSave).toHaveBeenCalledWith($scope.addForm);
        expect(actual).toBeTruthy();

    });

});