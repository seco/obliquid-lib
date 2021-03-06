package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.ItalianPostCode;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate an Italian post code (must be 5 digits).
 * 
 * @author stivlo
 * 
 */
public class ItalianPostCodeImpl implements ItalianPostCode {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * String strategy (strategy pattern).
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Expected length of an Italian post code.
         */
        private static final int EXPECTED_LENGTH = 5;

        @Override
        public final boolean isValid(final String theData) {
                if (theData == null || theData.length() != EXPECTED_LENGTH) {
                        return false;
                }
                for (int i = 0; i < EXPECTED_LENGTH; i++) {
                        if (!Character.isDigit(theData.charAt(i))) {
                                return false;
                        }
                }
                return true;
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                return isValid(theData);
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                if (!isValid(theData)) {
                        throw new IllegalArgumentException("The postcode '" + theData + "' isn't valid");
                }
                stringStrategy.setData(theData);
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

        /**
         * Like formatData, but returns the empty string instead of throwing
         * IllegalStateException.
         * 
         * @return the post code or the empty string if no post code was set
         */
        @Override
        public final String toString() {
                if (!isAssigned()) {
                        return "";
                }
                return formatData(Locale.ENGLISH);
        }

}
