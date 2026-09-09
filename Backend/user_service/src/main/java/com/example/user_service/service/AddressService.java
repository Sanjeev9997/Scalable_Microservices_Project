package com.example.user_service.service;

import com.example.user_service.dto.AddressRequest;
import com.example.user_service.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse addAddress(Long userId, AddressRequest request);

    List<AddressResponse> getAddresses(Long userId);

    AddressResponse getAddress(Long userId,Long addressId);

    AddressResponse updateAddress(Long addressId, AddressRequest request);

    void deleteAddress(Long addressId);

    AddressResponse setDefaultAddress(Long userId, Long addressId);
}