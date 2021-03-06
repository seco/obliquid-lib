package org.obliquid.datatype.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obliquid.datatype.DataType;
import org.obliquid.date.DateBuilder;

/**
 * Implement IsoDate Data Type behaviour for reuse. (Strategy Pattern)
 * 
 * @author stivlo
 * 
 */
public class IsoDateStrategy implements DataType<Date> {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** The value held. */
        private Date date;

        /**
         * Check an ISO Date in the format yyyy-MM-dd only.
         */
        private static final Pattern VALID_DATE = Pattern
                        .compile("^([0-9]{4})-(1[0-2]|0[1-9])-(3[0-1]|[1-2][0-9]|0[1-9])$");

        /**
         * ISO Date parser. I let it Serialize, because is not thread safe, so
         * it can't be static.
         */
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                DateTimeFormatter dtFormatter = DateTimeFormat.longDate().withLocale(locale);
                return dtFormatter.print(getDateTime());
        }

        @Override
        public final Date getData() throws IllegalStateException {
                if (date == null) {
                        throw new IllegalStateException();
                }
                return new Date(date.getTime());
        }

        @Override
        public final void setDataFromString(final String isoDate) throws IllegalArgumentException {
                if (isoDate == null) {
                        throw new IllegalArgumentException("ISO Date can't be null");
                }
                if (!VALID_DATE.matcher(isoDate).find()) {
                        throw new IllegalArgumentException("Date '" + isoDate + "' not in ISO format");
                }
                try {
                        date = formatter.parse(isoDate);
                } catch (ParseException ex) {
                        throw new IllegalArgumentException("Problem parsing Date '" + isoDate);
                }

        }

        @Override
        public final void setData(final Date theDate) throws IllegalArgumentException {
                if (theDate == null) {
                        throw new IllegalArgumentException("Date can't be null");
                }
                date = new Date(theDate.getTime());
        }

        /**
         * Build a Joda ReadableDateTime from the internal Date.
         * 
         * @return a Joda ReadableDateTime
         */
        private ReadableDateTime getDateTime() {
                return DateBuilder.buildReadableDateTimeFromJavaDate(getData());
        }

        @Override
        public final boolean isAssigned() {
                return date != null;
        }

}
