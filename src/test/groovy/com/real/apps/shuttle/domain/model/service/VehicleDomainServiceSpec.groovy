package com.real.apps.shuttle.domain.model.service

import com.real.apps.shuttle.domain.model.BookedRange
import com.real.apps.shuttle.domain.model.Vehicle
import com.real.apps.shuttle.repository.VehicleRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

/**
 * Created by zorodzayi on 15/04/26.
 */
class VehicleDomainServiceSpec extends spock.lang.Specification {

    private VehicleDomainServiceImpl service
    private Date from = new Date()
    private Date to = new Date()


    def "Should Check All The Vehicles For A Given Company If They Are Bookable "() {
        given: 'A List Of Vehicles With BookedRanges'
        Vehicle vehicle = new Vehicle()

        BookedRange vehicle1BookedRange = new BookedRange(from, to)
        Set<BookedRange> vehicle1BookedRanges = new HashSet<>(Arrays.asList(vehicle1BookedRange))
        Vehicle vehicle1 = new Vehicle(bookedRanges: vehicle1BookedRanges)

        BookedRange vehicle2BookedRange = new BookedRange(from, to)
        BookedRange vehicle2BookedRange1 = new BookedRange(from, to)
        Set<BookedRange> vehicle2BookedRanges = new HashSet<>(Arrays.asList(vehicle2BookedRange, vehicle2BookedRange1))
        Vehicle vehicle2 = new Vehicle(bookedRanges: vehicle2BookedRanges)

        Set<Vehicle> vehicles = new HashSet<>(Arrays.asList(vehicle, vehicle1, vehicle2))

        and: 'A Booked Range '
        BookedRange bookedRange = new BookedRange(from, to)

        and: 'A Company Id, A Pageable, A Page , A Mock Repository And A Mock BookedRangeService'
        Pageable pageable = new PageRequest(0, 10)
        VehicleRepository repository = Mock()
        ObjectId id = ObjectId.get()
        Page<Vehicle> page = new PageImpl<>(Arrays.asList(vehicle, vehicle1, vehicle2))
        repository.findByCompanyId(id, pageable) >> page
        BookedRangeService bookedRangeService = Mock()
        bookedRangeService.availableForBooking(null, bookedRange) >> true
        and: 'A Service With The Mocked Repository And BookedRange Service'
        service = new VehicleDomainServiceImpl(repository: repository, bookedRangeService: bookedRangeService)

        when: 'We Find Vehicles That Are Available For Booking '
        Set<Vehicle> actual = service.bookable(id, pageable, bookedRange)

        then:

        1 * repository.findByCompanyId(id, pageable)
        1 * bookedRangeService.availableForBooking(null, bookedRange)
        1 * bookedRangeService.availableForBooking(vehicle1BookedRanges, bookedRange)
        1 * bookedRangeService.availableForBooking(vehicle2BookedRanges, bookedRange)
        actual[0] == vehicle

    }
}
