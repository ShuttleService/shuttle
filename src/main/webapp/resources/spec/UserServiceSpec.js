/**
 * Created by zorodzayi on 14/10/22.
 */
describe('Testing The User Service', function () {
    var UserService;
    var $httpBackend;
    var SUB_PATH = '/user'
    beforeEach(module('services'));

    beforeEach(inject(function (_UserService_, _$httpBackend_) {

        UserService = _UserService_;
        $httpBackend = _$httpBackend_;

        expect(UserService).toEqual(UserService);
        expect($httpBackend).toBeDefined();
    }));

    it('Verifies That The $resource methods Are Defined On The UserService', function () {

        expect(UserService.get).toBeDefined();
        expect(UserService.query).toBeDefined();
        expect(UserService.save).toBeDefined();
        expect(UserService.delete).toBeDefined();
        expect(UserService.remove).toBeDefined();
        expect(UserService.update).toBeDefined();
    });

    it('Calls The Rest Services With The Correct URL ', function () {

        var url = '/user';
        var user = {userName: 'Test User Name To Be Returned For A Call To UserService get'};
        $httpBackend.expectGET(url).respond(user);
        var actualUser = UserService.get();

        $httpBackend.flush();

        expect(actualUser.userName).toEqual(user.userName);

    });

    it('Calls User Service Query With skip and limit', function () {
        var skip = 3;
        var limit = 100;
        var user = {userName: 'Test User Name To Be Returned In The List Returned From A Call To query'};
        var url = '/user/' + skip + '/' + limit;
        $httpBackend.expectGET(url).respond([user]);

        var actualUser;

        UserService.query({skip: skip, limit: limit}, function (data) {
            actualUser = data;
        });

        $httpBackend.flush();

        expect(actualUser[0].userName).toEqual(user.userName);
    });

    it('Calls The User Service Save With The given user', function () {

        var user = {userName: 'Test User Name To POST'};
        var savedUser = {userName: 'Test User Name Resulting From The POST'};

        var url = '/user';
        $httpBackend.expectPOST(url, user).respond(savedUser);

        var actualUser;

        UserService.save(user, function (data) {
            actualUser = data;
        });

        $httpBackend.flush();

        expect(actualUser.userName).toEqual(savedUser.userName);
    });

    it('Sets The Sub Path To role. Call The URL And Return The Roles ', function () {
        var pathVariable = {pathVariable: 'role'};
        var url = '/user/role';
        var roles = ['Test Role '];
        $httpBackend.expectGET(url).respond(roles);

        var actual = UserService.query(pathVariable);

        $httpBackend.flush();

        expect(actual.length).toEqual(1);
        expect(actual[0]).toEqual(roles[0]);
    });

    it('Sets The Sub Path To changePassword, Call ChangePassword on user/changePassword Passing the given username,currentPassword ' +
        'and newPassword ', function () {

        var url =  SUB_PATH;

        var changePasswordCommand = {
            username: 'Test User Name',
            currentPassword: 'Test Current Password',
            newPassword: 'Test New Password'
        };
        console.log('The Url To Change Password With ' + url);
        $httpBackend.expectPOST(url, changePasswordCommand).respond(changePasswordCommand);

        var actual = UserService.save(changePasswordCommand);
        $httpBackend.flush();

        expect(actual.username).toEqual(changePasswordCommand.username);
        expect(actual.currentPassword).toEqual(changePasswordCommand.currentPassword);
        expect(actual.newPassword).toEqual(changePasswordCommand.newPassword);
    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
});