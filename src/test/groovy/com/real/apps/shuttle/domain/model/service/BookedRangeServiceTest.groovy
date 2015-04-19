package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.BookedRange

/**
 * Created by zorodzayi on 15/04/18.
 */
class BookedRangeServiceTest extends spock.lang.Specification {

    private Date now = new Date()
    private Date earlier
    private Date later
    private Date latest
    private BookedRangeService service = new BookedRangeServiceImpl()

    def setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now)
        calendar.add(Calendar.MILLISECOND, -1)
        earlier = calendar.getTime()
        calendar.add(Calendar.MILLISECOND, 2)
        later = calendar.getTime()
        calendar.add(Calendar.MILLISECOND, 1)
        latest = calendar.getTime()
    }

    def 'Should Be Available for Booking When The Potential BookedRange List Is Null'() {

        given: 'An Null List And A Booked Range'
        Set<BookedRange> list = null
        BookedRange bookedRange = new BookedRange(earlier, later)

        when: 'You check if a booked range is coincide for booking'
        boolean available = service.availableForBooking(list, bookedRange)

        then:
        available
    }

    def 'Should Be Available For Booking When The Potential BookedRange List Is Empty'() {

        given: 'An Empty List And Any Booked Range'
        Set<BookedRange> list = new HashSet<>()
        BookedRange bookedRange = new BookedRange(now, later)

        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(list, bookedRange)

        then:
        available
    }

    def 'Should Be Available For Booking When The Potential BookedRange List Has A Booked Range To The Same As The Subject BookedRange From'() {

        given: 'A BookingRange List With A BookedRange With A To The Same As The Subject To BookedRange'
        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange subject = new BookedRange(now, later)
        Set<BookedRange> list = new HashSet<>(Arrays.asList(bookedRange))

        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(list, subject)

        then:
        available
    }

    def 'Should Be Available For Booking When The Potential Booked Range List Has A Booked Range With A To Before The Subject BookedRange From'() {

        given: 'A BookedRange List With A Booked Range With A To Before The Subject BookedRange From'

        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange subject = new BookedRange(later, latest)
        Set<BookedRange> list = new HashSet<>(Arrays.asList(bookedRange))

        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(list, subject)

        then:
        available
    }

    def 'Should Be Available For Booking When The Potential Booked Range List Has A Booked Range With A From After The Subject To'() {

        given: 'A BookedRange List With A Booked Range With A From The Same As A The Subject BookedRange To'

        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange subject = new BookedRange(earlier, now)
        Set<BookedRange> set = new HashSet<>(Arrays.asList(bookedRange))

        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(set, subject)

        then:
        available
    }

    def 'Should Be Available For Booking When The Potential Booked Range List Has A Booked Range With A From The Same As The Subject To'() {

        given: 'A BookRange List With A Booked Range With A From Date After The Subject BookedRange To'
        BookedRange bookedRange = new BookedRange(earlier, now)
        BookedRange subject = new BookedRange(later, later)
        Set<BookedRange> set = new HashSet<>(Arrays.asList(bookedRange))
        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(set, subject)

        then:
        available
    }

    def 'Should Be Unavailable For Booking When The BookedRange List Has A BookedRange The Same As The Subject'() {

        given: 'A BookedRange List With A Booked Range The Same As The Subject Booked Range'
        BookedRange bookedRange = new BookedRange(now, later)
        BookedRange subject = new BookedRange(now, later)

        Set<BookedRange> set = new HashSet<>(Arrays.asList(bookedRange))

        when: 'You Check If A Booked Range Is Available For Booking'
        boolean available = service.availableForBooking(set, subject)

        then:
        !available

    }

    def 'Should Be Available For Booking When The Potential BookedRange List Has 2 BookedRanges Outside The Booking Range'() {
        given: 'A List With 2 BookedRanges And A BookedRange Outside The BookedRanges Of Both Of Them'
        BookedRange bookedRange = new BookedRange(earlier, now)
        Set<BookedRange> set = new HashSet<>()
        set.add(bookedRange)
        bookedRange = new BookedRange(later, latest)
        set.add(bookedRange)

        BookedRange subject = new BookedRange(now, later)

        when: 'You Check If A BookedRange Is Available For Booking'
        boolean available = service.availableForBooking(set, subject)

        then:
        available
    }

    def 'Should Not Be Available For Booking When The Potential BookedRange List Has BookedRanges One Of Which Is Within The Subject BookedRange'() {
        given: 'A List With 2 BookedRanges And A Booked Range Within The BookedRange Of One Of Them'
        BookedRange bookedRange = new BookedRange(earlier, now)
        Set<BookedRange> set = new HashSet<>()
        set.add(bookedRange)
        bookedRange = new BookedRange(now, later)
        set.add(bookedRange)
        BookedRange subject = new BookedRange(now, later)

        when: 'You Check If A BookedRange Is Available For Booking'
        boolean available = service.availableForBooking(set, subject)

        then:
        !available
    }

}