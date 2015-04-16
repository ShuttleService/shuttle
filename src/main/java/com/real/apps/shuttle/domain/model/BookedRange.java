package com.real.apps.shuttle.domain.model;

import org.apache.commons.lang3.Validate;

import static org.apache.commons.lang3.time.DateUtils.*;

import java.util.Date;

/**
 * Created by zorodzayi on 15/04/14.
 */
public class BookedRange {
    private final Date from;
    private final Date to;

    public BookedRange(Date from, Date to) {
        Validate.notNull(from);
        if (to != null && to.before(from)) {
            throw new RuntimeException("The From Date Cannot Be After The To Date");
        }
        this.from = from;
        this.to = to;
    }

    public boolean available(BookedRange bookedRange) {
        if (to == null) {
            return false;
        }

        return bookedRange != null && (from.after(bookedRange.to) || isSameInstant(from, bookedRange.to)) ||
                bookedRange != null && (bookedRange.to.before(from) || isSameInstant(bookedRange.to, from));

    }
}
