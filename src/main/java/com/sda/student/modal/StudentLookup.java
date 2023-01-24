package com.sda.student.modal;


import java.util.HashMap;
import java.util.Map;

/**
 * holds the lookup parameter for student.
 */
public final class StudentLookup {
    private static Map<String, String> query = new HashMap<>();
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    /**
     * new builder instance.
     *
     * @return the StudentLookup builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * StudentLookup builder
     */
    public static class Builder {
        private String firstName;
        private String lastName;
        private String phoneNumber;

        /**
         * Sets the first name.
         *
         * @param firstName the first name
         * @return builder instance
         */
        public Builder withFirstName(String firstName) {
            if(firstName!=null && !firstName.isBlank()) {
                this.firstName = firstName;
                query.put("firstName", firstName);
            }
            return this;
        }
        /**
         * Sets the last name.
         *
         * @param lastName the last name
         * @return builder instance
         */
        public Builder withLastName(String lastName) {
            if(lastName!=null && !lastName.isBlank()) {
                this.lastName = lastName;
                query.put("lastName", lastName);
            }
            return this;
        }

        /**
         * Sets the phone number.
         *
         * @param phoneNumber the phone number
         * @return builder instance
         */
        public Builder withPhoneNumber(String phoneNumber) {
            if(phoneNumber!=null && !phoneNumber.isBlank()) {
                this.phoneNumber = phoneNumber;
                query.put("phoneNumber", phoneNumber);
            }
            return this;
        }

        /**
         * Builds the student lookup.
         *
         * @return the instance of {@link StudentLookup}
         */
        public StudentLookup build() {
            return new StudentLookup(this);
        }
    }

    /**
     * Constructs the StudentLookup with given builder.
     *
     * @param builder the student lookup builder
     */
    private StudentLookup(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
    }

    /**
     * Gets the query.
     *
     * @return the map of query name and value
     */
    public Map<String, String> getQuery() {
        return query;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
