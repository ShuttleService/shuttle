package com.real.apps.shuttle.domain.model;

import org.apache.commons.lang3.Validate;

import static org.apache.commons.lang3.time.DateUtils.*;

import java.util.Date;

/**
 * Created by zorodzayi on 15/04/14.
 */
public class BookingRange {
    private final Date from;
    private final Date to;

    public BookingRange(Date from, Date to) {
        Validate.notNull(from);
        if (to != null && to.before(from)) {
            throw new RuntimeException("The From Date Cannot Be After The To Date");
        }
        this.from = from;
        this.to = to;
    }

    public boolean available(BookingRange bookingRange) {
        if (to == null) {
            return false;
        }

        return bookingRange != null && (from.after(bookingRange.to) || isSameInstant(from, bookingRange.to)) ||
                bookingRange != null && (bookingRange.to.before(from) || isSameInstant(bookingRange.to, from));
    }

    @Override
    public String toString() {
        return String.format("{from:%,to:%s}", from, to);
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
