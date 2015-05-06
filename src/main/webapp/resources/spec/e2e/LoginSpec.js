/**
 * Created by zorodzayi on 15/05/06.
 */
describe('Testing The Login Functionality ', function () {

    it('Entering An InValid User Name And Password And Clicking The Login Should Login The User', function () {

        browser.get('shuttle');
        element.all(by.id('username')).setText('root');
        element.all(by.id('password')).setText('false pasworrd');
        var btnLogin = element.all(by.id('btnLogin'));
        btnLogin.click();
        var message = element.all(by.id('msg').getText());

        expect(message).toEqual('Access Denied');
    });
});