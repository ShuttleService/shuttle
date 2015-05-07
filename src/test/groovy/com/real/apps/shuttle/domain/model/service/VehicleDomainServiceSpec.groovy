package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.BookedRange
import com.real.apps.shuttle.domain.model.Vehicle
import com.real.apps.shuttle.repository.VehicleRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

import java.awt.print.Book

/**
 * Created by zorodzayi on 15/04/26.
 */
class VehicleDomainServiceSpec extends spock.lang.Specification {

    private VehicleDomainServiceImpl service = new VehicleDomainServiceImpl()
    private Date from = new Date()
    private Date to = new Date()
    private BookedRange bookedRange = new BookedRange(from, to)


    def "Should Check All The Vehicles For A Given Company To See If They Are Bookable "() {
        given: 'A List Of Vehicles With BookedRanges'
        Vehicle vehicle = new Vehicle()

        BookedRange vehicle1BookedRange = new BookedRange(from, to)
        Set<BookedRange> vehicle1BookedRanges = new HashSet<>(Arrays.asList(vehicle1BookedRange))
        Vehicle vehicle1 = new Vehicle(bookedRanges: vehicle1BookedRanges)

        BookedRange vehicle2BookedRange = new BookedRange(from, to)
        BookedRange vehicle2BookedRange1 = new BookedRange(from, to)
        Set<BookedRange> vehicle2BookedRanges = new HashSet<>(Arrays.asList(vehicle2BookedRange, vehicle2BookedRange1))
        Vehicle vehicle2 = new Vehicle(bookedRanges: vehicle2BookedRanges)

        List<Vehicle> vehicles = Arrays.asList(vehicle, vehicle1, vehicle2)

        and: 'A Booked Range '
        BookedRange bookedRange = new BookedRange(from, to)

        and: 'A Company Id, A Pageable, A Page , A Mock Repository And A Mock BookedRangeService'
        Pageable pageable = new PageRequest(0, 10)
        VehicleRepository repository = Stub()
        ObjectId id = ObjectId.get()
        Page<Vehicle> page = new PageImpl<>(vehicles)
        BookedRangeService bookedRangeService = Stub()

        and: 'We Get the following page from the repository'
        repository.findByCompanyId(id, pageable) >> page

        and: 'Checking for availability on the booked ranges yield the following results'
        bookedRangeService.availableForBooking(null, bookedRange) >> true
        bookedRangeService.availableForBooking(vehicle1BookedRanges, bookedRange) >> false
        bookedRangeService.availableForBooking(vehicle2BookedRanges, bookedRange) >> true

        and: 'A Service With The Mocked Repository And BookedRange Service'
        service = new VehicleDomainServiceImpl(repository: repository, bookedRangeService: bookedRangeService)

        when: 'We Find Vehicles That Are Available For Booking'
        Set<Vehicle> actual = service.bookable(id, pageable, bookedRange)

        then:

        actual.size() == 2
        actual.contains(vehicle)
        actual.contains(vehicle2)
        !actual.contains(vehicle1)
    }

    def 'Given A Vehicle To Book That Is Not Bookable. The Booking Should Fail'() {

        given: 'A Set Of BookedRanges '
        Set<BookedRange> bookedRanges = new HashSet<>()
        bookedRanges << bookedRange

        and: 'A Vehicle With The Booked Ranges'
        Vehicle vehicle = new Vehicle(bookedRanges: bookedRanges)

        and: 'A Stubbed BookingService That Returns False When Asked Whether Or Not A Vehicle Is Bookable'
        BookedRangeService bookedRangeService = Mock()
        service.bookedRangeService = bookedRangeService
        bookedRangeService.availableForBooking(bookedRanges, bookedRange) >> false

        when: 'A Booking Is Attempted'
        boolean bookable = service.book(vehicle,bookedRange)

        then:
        !bookable
        1 * bookedRangeService.availableForBooking(bookedRanges, bookedRange)
    }

    def 'Given A Vehicle To Book That Is Bookable. The Booking Should Process And Be Successful'() {

        given: 'A Set Of BookedRanges'
        Set<BookedRange> bookedRanges = new HashSet<>()
        bookedRanges << bookedRange

        and: 'A Vehicle With The Booked Ranges'
        Vehicle vehicle = new Vehicle(bookedRanges: bookedRanges)

        and: 'A Stubbed BookedRangeService That Returns true when asked whether or not a vehicle is bookable'
        BookedRangeService bookedRangeService = Stub()
        service.bookedRangeService = bookedRangeService
        bookedRangeService.availableForBooking(bookedRanges, bookedRange) >> true

        when: 'A Booking Is Attempted'
        boolean booked = service.book(vehicle,bookedRange)

        then:
        booked
    }
}