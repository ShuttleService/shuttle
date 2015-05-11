package com.real.apps.shuttle.domain.model

/**
 * Created by zorodzayi on 15/05/11.
 */
class IdentifiableSpec extends spock.lang.Specification {

    def 'Should Set The Reference To The Id String On Init'() {
        when: 'An We Create A New Instance Of An Identifiable '
        Identifiable identifiable = new Identifiable() {}

        then: 'A Reference And Id Should Have Been Created. The Reference To Be Equal To The Id String Value'

        identifiable.id != null
        identifiable.reference != null
        identifiable.reference == identifiable.id.toString()

    }
}
