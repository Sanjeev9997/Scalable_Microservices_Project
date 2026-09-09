package com.example.user_service.controller;

import com.example.user_service.dto.AddressRequest;
import com.example.user_service.dto.AddressResponse;
import com.example.user_service.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getAllAddresses(@PathVariable Long userId)
    {
        return new ResponseEntity<>(addressService.getAddresses(userId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable Long userId, @RequestBody AddressRequest addressRequest){
        return new ResponseEntity<>(addressService.addAddress(userId, addressRequest), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable Long userId, @PathVariable Long addressId){
        return new ResponseEntity<>(addressService.getAddress(userId,addressId), HttpStatus.OK);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody AddressRequest addressRequest){
        return new ResponseEntity<>(addressService.updateAddress(addressId, addressRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>("Address deleted successfully.",HttpStatus.OK);
    }

    @PatchMapping("/user/{userId}/{addressId}")
    public ResponseEntity<AddressResponse> patchAddress(@PathVariable  Long userId,@PathVariable Long addressId){
        return new ResponseEntity<>(addressService.setDefaultAddress(userId,addressId), HttpStatus.OK);
    }
}
