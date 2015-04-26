package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by zorodzayi on 15/04/18.
 */
@Service
public class BookedRangeServiceImpl implements BookedRangeService {
    @Override
    public boolean availableForBooking(Set<BookedRange> potentialBookedRanges, BookedRange bookedRange) {
        if (potentialBookedRanges == null || potentialBookedRanges.isEmpty()) {
            return true;
        }
        Iterator<BookedRange> iterator = potentialBookedRanges.iterator();

        while (iterator.hasNext()) {
            BookedRange subject = iterator.next();

            if (subject.coincide(bookedRange)) {
                return false;
            }
        }

        return true;
    }
}