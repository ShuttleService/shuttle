import com.real.apps.shuttle.domain.model.BookedRange
import com.real.apps.shuttle.domain.model.Driver
import com.real.apps.shuttle.domain.model.service.BookedRangeService
import com.real.apps.shuttle.domain.model.service.DriverDomainServiceImpl
import com.real.apps.shuttle.repository.DriverRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

/**
 * Created by zorodzayi on 15/04/26.
 */
class DriverServiceSpec extends spock.lang.Specification {
    private DriverDomainServiceImpl service;
    private Date from = new Date()
    private Date to = new Date()

    def setup() {
        service = new DriverDomainServiceImpl();
    }

    def 'Should Find Drivers That Are Bookable By Checking That Each Driver Is Bookable'() {
        given: 'A Set Of Drivers, A Booked Range And A BookedRangeService Mock'

        Driver driver = new Driver()
        Driver driver1 = new Driver();
        BookedRange available = new BookedRange(from, to)
        BookedRange bookedRange = new BookedRange(from, to)
        Set<Driver> expected = new HashSet<>(Arrays.asList(driver))

        Set<BookedRange> driver1BookedRanges = new HashSet<BookedRange>(Arrays.asList(new BookedRange(from, to)))
        driver1.setBookedRanges(driver1BookedRanges)

        Driver driver2 = new Driver()

        Set<BookedRange> driver2BookedRanges = new HashSet<BookedRange>(Arrays.asList(new BookedRange(from, to), new BookedRange(from, to)))
        driver2.setBookedRanges(driver2BookedRanges)

        Set<Driver> drivers = new HashSet<>(Arrays.asList(driver, driver1))
        DriverRepository repository = Mock()
        ObjectId id = ObjectId.get()
        Pageable pageable = new PageRequest(0, 10)
        Page<Driver> page = new PageImpl<>(Arrays.asList(drivers));
        repository.findByCompanyId(id, pageable) >> page
        BookedRangeService bookedRangeService = Mock()
        bookedRangeService.availableForBooking(driver2BookedRanges, bookedRange) >> expected

        service.bookedRangeService(bookedRangeService)
        service.repository(repository)

        when: 'You Find Bookable Drivers For A Given Company'

        Set<BookedRange> result = service.bookableDrivers(id, pageable, bookedRange)

        then:

        1 * bookedRangeService.availableForBooking(null, bookedRange)
        1 * bookedRangeService.availableForBooking(driver1BookedRanges, bookedRange)
        1 * bookedRangeService.availableForBooking(driver2BookedRanges, bookedRange)
        1 * repository.findByCompanyId(id, pageable)
        result == expected
        result[0] == available
    }
}
