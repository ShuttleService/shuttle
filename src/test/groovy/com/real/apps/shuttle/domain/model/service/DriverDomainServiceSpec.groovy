package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.BookedRange
import com.real.apps.shuttle.domain.model.Driver
import com.real.apps.shuttle.repository.DriverRepository
import org.apache.log4j.Logger
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable


/**
 * Created by zorodzayi on 15/04/18.
 */
class DriverDomainServiceSpec extends spock.lang.Specification {

    private DriverDomainServiceImpl service = new DriverDomainServiceImpl();
    private Date now = new Date();
    private Date earlier;
    private Date later;
    private Date from = new Date()
    private Date to = new Date()
    private Logger logger = Logger.getLogger(DriverDomainServiceSpec.class)
    private BookedRange bookedRange = new BookedRange(from, to)

    def setup() {
        service = new DriverDomainServiceImpl();
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(now)
        calendar.add(Calendar.MILLISECOND, -1)
        earlier = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, 2)
        later = calendar.getTime()
    }

    def 'Should Find Drivers That Are Bookable By Checking That Each Driver Is Bookable'() {
        given: 'A Set Of Drivers, A Booked Range And A BookedRangeService Mock'

        Driver driver = new Driver()
        Driver driver1 = new Driver();

        BookedRange bookedRange = new BookedRange(from, to)
        Set<Driver> expected = new HashSet<>()
        expected << driver
        expected << driver1

        Set<BookedRange> driver1BookedRanges = new HashSet<BookedRange>(Arrays.asList(new BookedRange(from, to)))
        driver1.setBookedRanges(driver1BookedRanges)

        Driver driver2 = new Driver()

        Set<BookedRange> driver2BookedRanges = new HashSet<BookedRange>(Arrays.asList(new BookedRange(from, to), new BookedRange(from, to)))
        driver2.setBookedRanges(driver2BookedRanges)

        List<Driver> drivers = new ArrayList<>()

        drivers << driver
        drivers << driver1
        drivers << driver2

        DriverRepository repository = Stub()
        ObjectId id = ObjectId.get()
        Pageable pageable = new PageRequest(0, 10)
        Page<Driver> page = new PageImpl<>(drivers)
        repository.findByCompanyId(id, pageable) >> page

        logger.debug(page.getTotalElements())

        BookedRangeService bookedRangeService = Stub()
        bookedRangeService.availableForBooking(null, bookedRange) >> true
        bookedRangeService.availableForBooking(driver1BookedRanges, bookedRange) >> true
        bookedRangeService.availableForBooking(driver2BookedRanges, bookedRange) >> false


        service.bookedRangeService(bookedRangeService)
        service.repository(repository)

        when: 'You Find Bookable Drivers For A Given Company'

        Set<BookedRange> result = service.bookableDrivers(id, pageable, bookedRange)

        then:

        /*1 * bookedRangeService.availableForBooking(null, bookedRange)
        1 * bookedRangeService.availableForBooking(driver1BookedRanges, bookedRange)
        1 * bookedRangeService.availableForBooking(driver2BookedRanges, bookedRange)
        repository.findByCompanyId(id, pageable)*/
        result.size() == expected.size()
        result.contains(driver)
        result.contains(driver1)
        !result.contains(driver2)
    }

    def 'Booking A Driver Who Is Bookable For The Given BookedRange Should Result In A Successful Booking'() {
        given: 'A Set Of BookedRanges'
        Set<BookedRange> bookedRanges = new HashSet<>()
        bookedRanges << bookedRange

        and: 'A Driver With The Booked Ranges'
        Driver driver = new Driver(bookedRanges: bookedRanges)

        and: 'A Mock BookedRangeDomainService That Says The Driver Is Bookable'
        BookedRangeService bookedRangeService = Stub()
        service.bookedRangeService = bookedRangeService
        bookedRangeService.availableForBooking(bookedRanges, bookedRange) >> true

        when: 'A Booking Is Attempted'
        boolean booked = service.book(driver, bookedRange)

        then:
        booked
    }

    def '''Booking A Driver Who Is Not Bookable For The Given BookedRange Should Result In An
           Unsuccessful Booking '''() {
        given: 'A Set Of BookedRanges'
        Set<BookedRange> bookedRanges = new HashSet<>()
        bookedRanges << bookedRange

        and: 'A Driver With The Booked Ranges'
        Driver driver = new Driver(bookedRanges: bookedRanges)

        and: 'A Mock BookedRangeService That Says The Driver Is Not Bookable'
        BookedRangeService bookedRangeService = Mock()
        service.bookedRangeService = bookedRangeService
        bookedRangeService.availableForBooking(bookedRanges, bookedRange) >> false

        when: 'A Booking Is Attempted '
        boolean booked = service.book(driver, bookedRange)

        then : ''
        !booked
        1 * bookedRangeService.availableForBooking(bookedRanges, bookedRange)
    }
}