package com.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private Boolean isDefault=false;

    @Override
    public String toString() {
        return "AddressResponse{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", street='" + street + '\'' +
                ", landmark='" + landmark + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

}
