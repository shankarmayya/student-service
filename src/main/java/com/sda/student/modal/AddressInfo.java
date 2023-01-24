package com.sda.student.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Models the address information.
 */
@AllArgsConstructor
@Data
public class AddressInfo {

    private final String line1;
    private final String line2;
    private final String country;
    private final String state;
    private final String city;
    private final String postalCode;
}
