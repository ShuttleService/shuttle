package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.User
import com.real.apps.shuttle.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Created by zorodzayi on 15/06/10.
 */
class UserDomainServiceSpec extends spock.lang.Specification {

    User user = new User()
    String plainTextNewPassword = 'Test Plain Text Password To Be Encrypted'
    String encryptedNewPassword = 'Test Encrypted Password'
    String plainTextOldPassword = 'Test Plain Text Old Password'
    String encryptedOldPassword = 'Text Encrypted Old Password'
    String username = 'Test User Name'
    PasswordEncoder passwordEncoder = Stub()
    UserRepository repository = Mock()
    UserRepository stubbedRepository = Stub()
    UserDomainServiceImpl service


    def setup() {
        user.setPassword(encryptedOldPassword)
        passwordEncoder.encode(plainTextNewPassword) >> encryptedNewPassword
        passwordEncoder.encode(plainTextOldPassword) >> encryptedOldPassword
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

    def "Should Find The User With The Given Username And Change It's Password If The Given Old Password Matches The Stored One."() {

        given: 'A User Name, An Old Password And A New Password'
        service.repository = stubbedRepository
        when: 'A Call Is Made To Change The Password'
        service.changePassword(username, plainTextOldPassword, plainTextNewPassword)

        then: 'A User With The Given User Name Should Be Found, The Password Checked Against The Old Password, Set The New Password And Save The User'

        user.password == encryptedNewPassword
    }

}
