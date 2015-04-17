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

        when: 'You try to instantiate A BookingRange'
        new BookingRange(from, now)

        then: 'Validation Should Fail'
        thrown(NullPointerException.class)
    }

    def "Should Be Unavailable When BookedRanges Have The Same from and to dates"() {
        given: "2 BookedRanges That Are The Same"

        BookingRange bookedRange = new BookingRange(now, later)
        BookingRange anotherBookedRange = new BookingRange(now, later)

        when: "You check whether or not there are withing range"
        boolean available = bookedRange.available(anotherBookedRange)
        boolean available1 = anotherBookedRange.available(bookedRange)

        then:
        !available
        !available1
    }

    def "The from Date Should Be After The to Date"() {
        given: "2 dates from after to"

        when: 'You instantiate a BookingRange'
        new BookingRange(now, earlier)
        then:
        thrown(RuntimeException.class)
    }

    def "Should Be Unavailable For BookedRanges When 1 has a smaller lower from date"() {
        given: '2 BookedRanges, 1 with a lower from date that the other'

        BookingRange bookedRange = new BookingRange(now, later)
        BookingRange another = new BookingRange(earlier, later)

        when: 'You check whether or not there are within range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'Should Be Unavailable Given BookRanges When 1 has a greater to date'() {
        given: '2 BookedRanges, 1 with a higher to date that the other '

        BookingRange bookedRange = new BookingRange(earlier, now)
        BookingRange another = new BookingRange(earlier, later)

        when: 'You check whether or not there are withing range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'Null should be without range'() {
        given: 'A null and a BookingRange'
        BookingRange bookedRange = new BookingRange(earlier, later);

        when: 'You check whether or not a null is within range'
        boolean withoutRange = bookedRange.available(null)

        then:
        withoutRange == false
    }

    def 'When There is no to date then should be unavailable with for any slots'() {
        given: 'A BookingRange With With A Null to date'

        BookingRange bookedRange = new BookingRange(now, null);
        BookingRange another = new BookingRange(later, null)

        when: 'You check whether or not within range'
        boolean available = bookedRange.available(another)
        boolean available1 = another.available(bookedRange)

        then:
        !available
        !available1
    }

    def 'When A BookedRange has no to date then any BookedRange is unavailable'() {
        given: 'A BookingRange With no to date and one with a to date'
        BookingRange bookedRange = new BookingRange(earlier, null)
        BookingRange another = new BookingRange(earlier, later)

        when: 'You check whether or not withing range'
        boolean available = bookedRange.available(another)

        then:
        !available
    }

}
