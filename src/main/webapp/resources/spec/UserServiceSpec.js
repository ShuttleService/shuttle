/**
 * Created by zorodzayi on 14/10/22.
 */
describe('Testing The User Service', function () {
    var UserService;
    var CONTEXT_ROOT;
    var $httpBackend;
    beforeEach(module('services'));

    beforeEach(inject(function (_UserService_, _CONTEXT_ROOT_, _$httpBackend_) {

        UserService = _UserService_;
        CONTEXT_ROOT = _CONTEXT_ROOT_;
        $httpBackend = _$httpBackend_;

        expect(UserService).toEqual(UserService);
        expect(CONTEXT_ROOT).toBeDefined();
        expect($httpBackend).toBeDefined();
    }));

    it('Should Verify That The $resource methods Are Defined On The UserService', function () {

        expect(UserService.get).toBeDefined();
        expect(UserService.query).toBeDefined();
        expect(UserService.save).toBeDefined();
        expect(UserService.delete).toBeDefined();
        expect(UserService.remove).toBeDefined();
        expect(UserService.put).toBeDefined();
    });

    it('Should Call The Rest Services With The Correct URL ', function () {

        var url = CONTEXT_ROOT + '/user';
        var user = {userName: 'Test User Name To Be Returned For A Call To UserService get'};
        $httpBackend.expectGET(url).respond(user);
        var actualUser = UserService.get();

        $httpBackend.flush();

        expect(actualUser.userName).toEqual(user.userName);

    });

    it('Should Call User Service Query With skip and limit', function () {
        var skip = 3;
        var limit = 100;
        var user = {userName: 'Test User Name To Be Returned In The List Returned From A Call To query'};
        var url = CONTEXT_ROOT + '/user/' + skip + '/' + limit;
        $httpBackend.expectGET(url).respond([user]);

        var actualUser;

        UserService.query({skip: skip, limit: limit}, function (data) {
            actualUser = data;
        });

        $httpBackend.flush();

        expect(actualUser[0].userName).toEqual(user.userName);
    });

    it('Should Call The User Service Save With The given user', function () {

        var user = {userName: 'Test User Name To POST'};
        var savedUser = {userName: 'Test User Name Resulting From The POST'};

        var url = CONTEXT_ROOT + '/user';
        $httpBackend.expectPOST(url, user).respond(savedUser);

        var actualUser;

        UserService.save(user, function (data) {
            actualUser = data;
        });

        $httpBackend.flush();

        expect(actualUser.userName).toEqual(savedUser.userName);
    });

    it('Should Set The Sub Path To role. Call The URL And Return The Roles ',function(){
        var subPath1 = {subPath1:'role'};
        var url = CONTEXT_ROOT+'/user/role';
        var roles = ['Test Role '];
        $httpBackend.expectGET(url).respond(roles);

        var actual = UserService.query(subPath1);

        $httpBackend.flush();

        expect(actual.length).toEqual(1);
        expect(actual[0]).toEqual(roles[0]);
    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});