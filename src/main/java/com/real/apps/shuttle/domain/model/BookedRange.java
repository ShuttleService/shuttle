package com.real.apps.shuttle.domain.model;

import org.apache.commons.lang3.Validate;

import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang3.time.DateUtils.*;

/**
 * Created by zorodzayi on 15/04/14.
 */
public class BookedRange {
    private Date from;
    private Date to;

    public BookedRange() {
    }

    public BookedRange(Date from, Date to) {
        Validate.notNull(from);
        Validate.notNull(to);
        if (to.before(from)) {
            throw new RuntimeException("The From Date Cannot Be After The To Date");
        }
        this.from = from;
        this.to = to;
    }

    public boolean coincide(BookedRange bookedRange) {

        if (bookedRange == null) {
            throw new RuntimeException("Cannot Check If A bookedRange Coincide With A Null Value");
        }

        if (isSameInstant(getTo(), bookedRange.getFrom()) || getTo().before(bookedRange.getFrom())) {
            return false;
        }

        if (isSameInstant(getFrom(), bookedRange.getTo()) || getFrom().after(bookedRange.getTo())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if ((object instanceof BookedRange) == false) {
            return false;
        }

        BookedRange bookedRange = (BookedRange) object;

        if (this == bookedRange) {
            return true;
        }
        Date to = setSeconds(this.to, 0);

        if (isSameInstant(truncate(bookedRange.getFrom(), Calendar.MINUTE),
                truncate(getFrom(), Calendar.MINUTE)) &&
                isSameInstant(truncate(bookedRange.getTo(), Calendar.MINUTE), truncate(to, Calendar.MINUTE))) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("{from:%s,to:%s}", from, to);
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
