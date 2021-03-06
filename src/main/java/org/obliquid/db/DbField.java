package org.obliquid.db;

import com.google.common.collect.ImmutableMap;

/**
 * Represents a DB Field. It's Immutable.
 * 
 * @author stivlo
 */
public class DbField {

        /** Field name and type. */
        private String name, type;

        /** Whether is a primary key or not. */
        private boolean isPrimary;

        /** Set to true for text types, false for numeric and binary types. */
        private ImmutableMap<String, Boolean> isThisTypeText = new ImmutableMap.Builder<String, Boolean>()
                        .put("smallint", false).put("varchar", true).put("timestamp", false)
                        .put("tinyint", false).put("datetime", false).put("text", true).put("year", false)
                        .put("decimal", false).put("enum", false).put("set", false).put("mediumint", false)
                        .put("char", true).put("int", false).put("blob", false).put("date", false)
                        .put("mediumblob", false).put("double", false).put("longblob", false).build();

        /**
         * Builds an Immutable Field.
         * 
         * @param nameIn
         *                field name
         * @param typeIn
         *                field type including size information such as
         *                "VARCHAR(20)"
         * @param key
         *                MySQL raw key information such as "PRI", "", ...
         */
        public DbField(final String nameIn, final String typeIn, final String key) {
                name = nameIn;
                type = typeIn.split("\\(")[0];
                isPrimary = key.equals("PRI");
        }

        /**
         * Get the database field name.
         * 
         * @return field name
         */
        public final String getName() {
                return name;
        }

        /**
         * Get the database field type without size.
         * 
         * @return field type, such as VARCHAR
         */
        public final String getType() {
                return type;
        }

        /**
         * Return true if the field type is a text type (that can be recoded),
         * so for instance CHAR/VARCHAR/TEXT, but not int,enum,BLOB..
         * 
         * @return true if it's text
         * @throws IllegalArgumentException
         *                 for types that weren't added. I just added the types
         *                 I need for my DBs. If a type is missing the
         *                 RunTimeException will just let you know and it's easy
         *                 to add it in the ImmutableMap isThisTypeText
         */
        public final boolean isText() throws IllegalArgumentException {
                Boolean isText = isThisTypeText.get(type);
                if (isText != null) {
                        return isText;
                }
                throw new IllegalArgumentException("I don't know DB type '" + type + "', please add it");
        }

        /**
         * Is the field a primary key?
         * 
         * @return true if it is
         */
        public final boolean isPrimary() {
                return isPrimary;
        }

        /**
         * Return a String representation of the object as field name + an
         * asterisk if the field is part of the primary key + the type without
         * the size information.
         * 
         * @return String representation
         */
        @Override
        public final String toString() {
                String toString = getName();
                if (isPrimary) {
                        toString += '*';
                }
                toString += ' ' + getType();
                return toString;
        }

}
