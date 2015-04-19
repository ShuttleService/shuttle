package com.real.apps.shuttle.domain.model

/**
 * Created by zorodzayi on 15/04/14.
 */
class BookedRangeSpec extends spock.lang.Specification {

    Date now = new Date()
    Date earlier;
    Date later;
    Date latest;

    def setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now)

        calendar.add(Calendar.HOUR, -1)
        earlier = calendar.getTime()
        calendar.add(Calendar.HOUR, 2)
        later = calendar.getTime()
        calendar.add(Calendar.HOUR, 1)
        latest = calendar.getTime()
    }

    def 'Should not be able to create a BookedRange with a null from date '() {

        given: 'A null from date and a proper to date'
        Date from = null;

        when: 'You try to instantiate A BookedRange'
        new BookedRange(from, now)

        then: 'Validation Should Fail'
        thrown(NullPointerException.class)
    }

    def 'Should Not Be Able To Create A BookedRange With No To Date'() {
        given: 'A Booked Range With A Null To Date'

        when: 'You Attempt To Instantiate A Booked Range'
        new BookedRange(now, null)

        then:
        thrown(NullPointerException.class)
    }

    def "The from Date Should Be After The to Date"() {
        given: "2 dates from after to"

        when: 'You instantiate a BookedRange'
        new BookedRange(now, earlier)
        then:
        thrown(RuntimeException.class)
    }

    def "Should Coincide When BookedRanges Have The Same from and to dates"() {
        given: "2 BookedRanges That Are The Same"

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange anotherBookedRange = new BookedRange(now, later)

        when: "You check whether or not they coincide"
        boolean coincide = bookedRange.coincide(anotherBookedRange)
        boolean coincide1 = anotherBookedRange.coincide(bookedRange)

        then:
        coincide
        coincide1
    }

    def "Should Coincide For BookedRanges With The Same To Date"() {
        given: '2 BookedRanges, 1 with a lower from date that the other'

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange another = new BookedRange(earlier, later)

        when: 'You check whether or not there are within range'
        boolean coincide = bookedRange.coincide(another)
        boolean coincide1 = another.coincide(bookedRange)

        then:
        coincide
        coincide
    }

    def 'Should Be Coincide When They Have The Same From Date'() {
        given: '2 BookedRanges, 1 with a higher to date that the other '

        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange another = new BookedRange(earlier, later)

        when: 'You check whether or not there are withing range'
        boolean coincide = bookedRange.coincide(another)
        boolean coincide1 = another.coincide(bookedRange)

        then:
        coincide
        coincide1
    }

    def 'Should Not Coincide When The BookedRange To Date Is The Same As The Subject From Date'() {
        given: '2 Booked Ranges, One With A To The Same As The Subject From'
        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange subject = new BookedRange(now, later)

        when: 'You Check If The Coincide'
        boolean coincide = bookedRange.coincide(subject)
        boolean coincide1 = subject.coincide(bookedRange)
        then:
        !coincide
        !coincide1
    }

    def 'Should Not Coincide When The BookedRange From Is The Same As The Subject To '() {
        given: '2 Booked Ranges, One With From The Same As The Other To'

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange subject = new BookedRange(earlier, now)

        when: 'You Check If They Coincide'
        boolean coincide = bookedRange.coincide(subject)
        boolean coincide1 = subject.coincide(bookedRange)

        then:
        !coincide
        !coincide1
    }

    def 'Should Not Coincide When The BookedRange From Is Before The Subject To'() {
        given: '2 Booked Ranges, One With A From Before The Subject To'
        BookedRange bookedRange = new BookedRange(later, latest)
        BookedRange subject = new BookedRange(earlier, now)

        when: 'You Check If The Coincide'
        boolean coincide = bookedRange.coincide(subject)
        boolean coincide1 = subject.coincide(bookedRange)

        then:
        !coincide
        !coincide1
    }

    def 'Should Not Coincide When The Booked Range To Is Before The Subject From'() {

        given: '2 Booked Ranges, One With A To Before The Subject From'
        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange subject = new BookedRange(later, latest)

        when: 'You Check If They Coincide'
        boolean coincide = bookedRange.coincide(subject)
        boolean coincide1 = subject.coincide(bookedRange)

        then:
        !coincide
        !coincide1
    }

    def 'When A Booked Range Is Checked For Coincidence With A Null, An Exception Should Be Thrown'() {

        given: 'A Booked Range And A Null';
        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange subject = null;

        when: 'You Check If The Coincide'
        bookedRange.coincide(subject)

        then:

        thrown(RuntimeException.class)
    }

    def 'Two Booked Ranges With The Same From And To Dates Should Be Equal'() {
        given: 'Two Booked Ranges With The Same from and to'
        BookedRange first = new BookedRange(earlier, later);
        BookedRange second = new BookedRange(earlier, later);

        when: 'You Test Them For Equality'
        boolean equal = first.equals(second)
        boolean equal1 = second.equals(first)

        then:
        equal
        equal1
    }

    def 'A BookedRange Should Be Equal To Itself '() {
        given: 'The Same BookedRange'

        BookedRange bookedRange = new BookedRange(now, later)
        when: 'You Compare It To Itself'

        boolean equal = bookedRange.equals(bookedRange);

        then:
        equal
    }

    def 'Two Booked Ranges With Different From Should Be Unequal'() {

        given: 'Two BookedRanges With Different From'
        BookedRange first = new BookedRange(now, later)
        BookedRange second = new BookedRange(earlier, later);

        when: 'They Are Checked For Equality'

        boolean equal = first.equals(second)
        boolean equal1 = second.equals(first)

        then:

        !equal
        !equal1
    }

    def 'Two Booked Ranges With Different To Should Be Unequal'() {

        given: 'Two BookedRanges With Different To Dates '
        BookedRange first = new BookedRange(earlier, now)
        BookedRange second = new BookedRange(earlier, later)

        when: 'There Are Checked For Equality'
        boolean equal = first.equals(second)
        boolean equal1 = second.equals(first)

        then:

        !equal
        !equal1
    }

    def 'A Booked Range Should Be Unequal To A Object Of A Different Class'() {

        given: 'A Booked Range And An Agent'
        BookedRange range = new BookedRange(now, later)
        Agent agent = new Agent()

        when: 'They Are Compared For Equality'

        boolean equal = range.equals(agent)

        then:
        !equal
    }

}