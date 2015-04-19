package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;

import java.util.Set;

/**
 * Created by zorodzayi on 15/04/18.
 */
public interface BookedRangeService {
    boolean availableForBooking(Set<BookedRange> potentialBookedRanges, BookedRange bookedRange);
}
