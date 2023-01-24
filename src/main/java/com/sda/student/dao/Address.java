package com.sda.student.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Holds the address information.
 */
@Data
@AllArgsConstructor
public class Address {
    private final String line1;
    private final String line2;
    private final String country;
    private final String state;
    private final String city;
    private final String postalCode;
}
