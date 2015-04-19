package com.real.apps.shuttle.domain.model.service;

import com.real.apps.shuttle.domain.model.BookedRange;
import com.real.apps.shuttle.domain.model.Driver;

/**
 * Created by zorodzayi on 15/04/18.
 */
public interface DriverService {
    boolean book(Driver driver, BookedRange bookedRange);
}
