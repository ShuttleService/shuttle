package com.real.apps.shuttle.domain.model

/**
 * Created by zorodzayi on 15/04/14.
 */
class BookedRangeSpec extends spock.lang.Specification {

    Date now = new Date()
    Date earlier;
    Date later;

    def setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now)

        calendar.add(Calendar.HOUR, -1)
        earlier = calendar.getTime()
        calendar.add(Calendar.HOUR, 2)
        later = calendar.getTime()
    }

    def 'Should not be able to create a BookedRange with a null from date '() {

        given: 'A null from date and a proper to date'
        Date from = null;

        when: 'You try to instantiate A BookedRange'
        new BookedRange(from, now)

        then: 'Validation Should Fail'
        thrown(NullPointerException.class)
    }

    def "Should Be Unavailable When BookedRanges Have The Same from and to dates"() {
        given: "2 BookedRanges That Are The Same"

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange anotherBookedRange = new BookedRange(now, later)

        when: "You check whether or not there are withing range"
        boolean available = bookedRange.available(anotherBookedRange)
        boolean available1 = anotherBookedRange.available(bookedRange)

        then:
        !available
        !available1
    }

    def "The from Date Should Be After The to Date"() {
        given: "2 dates from after to"

        when: 'You instantiate a BookedRange'
        new BookedRange(now, earlier)
        then:
        thrown(RuntimeException.class)
    }

    def "Should Be Unavailable For BookedRanges When 1 has a smaller lower from date"() {
        given: '2 BookedRanges, 1 with a lower from date that the other'

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange another = new BookedRange(earlier, later)

        when: 'You check whether or not there are within range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'Should Be Unavailable Given BookRanges When 1 has a greater to date'() {
        given: '2 BookedRanges, 1 with a higher to date that the other '

        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange another = new BookedRange(earlier, later)

        when: 'You check whether or not there are withing range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'Null should be without range'() {
        given: 'A null and a BookedRange'
        BookedRange bookedRange = new BookedRange(earlier, later);

        when: 'You check whether or not a null is within range'
        boolean withoutRange = bookedRange.available(null)

        then:
        withoutRange == false
    }

    def 'When There is no to date then should be unavailable with for any slots'() {
        given: 'A BookedRange With With A Null to date'

        BookedRange bookedRange = new BookedRange(now, null);
        BookedRange another = new BookedRange(later, null)

        when: 'You check whether or not within range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'When A BookedRange has no to date then any BookedRange is unavailable'() {
        given: 'A BookedRange With no to date and one with a to date'
        BookedRange bookedRange = new BookedRange(earlier, null)
        BookedRange another = new BookedRange(earlier, later)

        when: 'You check whether or not withing range'
        boolean available = bookedRange.available(another)

        then:
        !available
    }

}
