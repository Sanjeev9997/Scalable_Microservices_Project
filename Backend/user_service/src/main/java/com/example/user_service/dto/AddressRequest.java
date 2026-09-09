package com.example.user_service.dto;

import lombok.Data;

@Data
public class AddressRequest {

    private String fullName;

    private String phoneNumber;

    private String houseNo;

    private String street;

    private String landmark;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private boolean isDefault;

}