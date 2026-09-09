package com.example.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    private Long id;

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