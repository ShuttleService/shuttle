/**
 * Created by zorodzayi on 15/05/06.
 */
describe('Testing The Login Functionality ', function () {

    beforeEach(function () {
        browser.get('shuttle');
    });

    it('Entering An InValid User Name And Password And Clicking The Login Should Login The User', function () {

        element.all(by.id('username')).sendKeys('root');
        element.all(by.id('password')).sendKeys('Wrong Password');

        element.all(by.id('btnLogin')).click();

        var message = element.all(by.buttonText('msg')).getText();

        expect(message).toEqual('Access Denied');
    });
});