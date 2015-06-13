package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.User
import com.real.apps.shuttle.domain.model.exception.NoUserForGivenUsernameException
import com.real.apps.shuttle.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Created by zorodzayi on 15/06/10.
 */
class UserDomainServiceSpec extends spock.lang.Specification {

    User user = new User()
    String plainTextNewPassword = 'Test Plain Text Password To Be Encrypted'
    String encryptedNewPassword = 'Test Encrypted Password'
    String plainTextCurrentPassword = 'Test Plain Text Old Password'
    String encryptedCurrentPassword = 'Text Encrypted Old Password'
    String username = 'Test User Name'
    PasswordEncoder passwordEncoder = Stub()
    UserRepository repository = Mock()
    UserRepository stubbedRepository = Stub()
    UserDomainServiceImpl service


    def setup() {
        user.setPassword(encryptedCurrentPassword)
        passwordEncoder.encode(plainTextNewPassword) >> encryptedNewPassword
        passwordEncoder.encode(plainTextCurrentPassword) >> encryptedCurrentPassword
        stubbedRepository.findOneByUsername(username) >> user
        service = new UserDomainServiceImpl(passwordEncoder: passwordEncoder, repository: repository)
    }

    def 'Should Set The Password To The New Password(Encrypted).'() {

        given: 'A User And A Plain Text Password'

        when: 'A Call Is Made To Change The Password'
        service.changePassword(user, plainTextNewPassword)

        then: 'The Password Should Be Changed To The Given New Password After Being Encrypted. The User Should Then Be Saved To The Database.'
        user.getPassword() == encryptedNewPassword
        1 * repository.save(user)

    }

    def "Should Find The User With The Given Username And Change It's Password If The Given Current Password Matches The Stored One."() {

        given: 'A User Name, A Correct Current Password And A New Password'
        service.repository = stubbedRepository
        when: 'A Call Is Made To Change The Password'
        service.changePassword(username, plainTextCurrentPassword, plainTextNewPassword)

        then: 'A User With The Given User Name Should Be Found, The Password Checked Against The Old Password, Set The New Password And Save The User'

        user.password == encryptedNewPassword
    }

    def 'When A Wrong Current Password Is Given, The Current Password Should Remain As Is i.e it should not be changed'() {
        given: 'A User Name, A Wrong Current Password '
        String wrongPlainTextCurrentPassword = 'Test Wrong Plain Text Current Password';
        service.repository = stubbedRepository
        passwordEncoder.encode(wrongPlainTextCurrentPassword) >> 'Test Encrypted Wrong Entered Current Password'

        when: 'A Call Is Made To Change The Password'
        service.changePassword(username, wrongPlainTextCurrentPassword, plainTextNewPassword)

        then: 'The Current Password Should Remain As Is'
        user.password == encryptedCurrentPassword
    }

    def 'When There Is No User With The Given Username. An Exception Should Be Raised To Say The Same'() {

        given: 'A username with no corresponding user'
        String usernameWithNoCorrespondingUser = 'Username with no corresponding user'

        when: 'A Call Is Made To Change The Password'
        service.changePassword(usernameWithNoCorrespondingUser, plainTextCurrentPassword, plainTextNewPassword)

        then: 'An Exception Should Be Raised That The Username Does Not Have A Corresponding User '

        thrown(NoUserForGivenUsernameException);
    }

}
