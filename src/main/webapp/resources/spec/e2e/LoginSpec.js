/**
 * Created by zorodzayi on 15/05/06.
 */
describe('Testing The Login Functionality ', function () {

    beforeEach(function () {
        browser.get('/shuttle/login');
    });

    it('Entering An InValid User Name And Password And Clicking The Login Should Login The User', function () {

        element(by.css('#username')).sendKeys('root');
        element(by.id('password')).sendKeys('Wrong Password');

        element(by.id('login')).click();

        var message = element(by.id('message')).getText();

        expect(message).toEqual('Access Denied');
    });

    it('Entering Valid User Name And Password And Clicking The Login Button Should Login The User And Take Him To The Home Page', function () {
        var menu = element(by.id('menu'));

        expect(menu.isPresent()).toBe(false);

        element(by.id('username')).sendKeys('root');
        element(by.id('password')).sendKeys('adminadmin');
        element(by.id('login')).click();

        menu = element(by.id('menu'));
        expect(menu.isPresent()).toBe(true);
        expect(menu.isDisplayed()).toBe(true);
    });
});