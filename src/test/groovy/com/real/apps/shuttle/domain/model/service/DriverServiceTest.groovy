package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.BookedRange
import com.real.apps.shuttle.domain.model.Driver

/**
 * Created by zorodzayi on 15/04/18.
 */
class DriverServiceTest extends spock.lang.Specification {

    private DriverService service = new DriverServiceImpl();
    private Date now = new Date();
    private Date earlier;
    private Date later;

    def setup() {

        Calendar calendar = Calendar.getInstance()
        calendar.setTime(now)
        calendar.add(Calendar.MILLISECOND, -1)
        earlier = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, 2)
        later = calendar.getTime()
    }

    def 'Should Return The Drivers Outside The BookedRange'() {

        given: 'A driver with no booked range'

        Driver driver = new Driver()
        BookedRange bookedRange = new BookedRange(now, later)

        when: 'A booking is attempted'
        boolean successfullyBooked = service.book(driver, bookedRange);

        then:
        successfullyBooked
    }
}