package org.obliquid.datatype.personaltaxid;

import org.obliquid.datatype.impl.PersonalTaxIdImpl;

/**
 * Check a French Personal Tax Id.
 * 
 * @author stivlo
 * 
 */
public class FrenchPersonalTaxId extends PersonalTaxIdImpl {

        @Override
        public final boolean isValid(final String data) {
                return true;
        }

}
