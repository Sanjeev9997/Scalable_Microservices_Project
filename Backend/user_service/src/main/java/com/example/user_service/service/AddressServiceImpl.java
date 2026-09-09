package com.example.user_service.service;

import com.example.user_service.dto.AddressRequest;
import com.example.user_service.dto.AddressResponse;
import com.example.user_service.model.Address;
import com.example.user_service.model.User;
import com.example.user_service.repository.AddressRepository;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private static final Logger log= LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public AddressResponse addAddress(Long userId, AddressRequest request) {
        log.info("add address called");
        log.info("fetching user");
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        log.info("user fetched");

        Address address=Address.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .houseNo(request.getHouseNo())
                .street(request.getStreet())
                .landmark(request.getLandmark())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .isDefault(request.isDefault())
                .user(user)
                .build();
        Address savedAddress=addressRepository.save(address);
        log.info("addAddress completed");
        return mapToResponse(savedAddress);
    }

    @Override
    public List<AddressResponse> getAddresses(Long userId) {
        log.info("get addresses called");
        List<AddressResponse> addresses=addressRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
        log.info("get addresses completed");
        return addresses;
    }

    @Override
    public AddressResponse getAddress(Long userId,Long addressId) {
        log.info("get address called");
        log.info("fetching user");
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        log.info("user fetched");
        for (Address address:user.getAddresses()){
            if (addressId==address.getId()){
                return mapToResponse(address);
            }
        }
        log.info("get address completed");
        return null;
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        log.info("update address called");
        log.info("fetching address");
        Address  address=addressRepository.findById(addressId).orElseThrow(()->new RuntimeException("Address not found"));
        log.info("address fetched");
        address.setFullName(request.getFullName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setHouseNo(request.getHouseNo());
        address.setStreet(request.getStreet());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(request.isDefault());

        Address saved=addressRepository.save(address);
        log.info("update address completed");
        return mapToResponse(saved);
    }

    @Override
    public void deleteAddress(Long addressId) {
        log.info("delete address called");
        log.info("fetching address");
        Address address=addressRepository.findById(addressId).orElseThrow(()->new RuntimeException("Address not found"));
        log.info("address fetched");
        log.info("delete address completed");
        addressRepository.delete(address);
    }

    @Override
    public AddressResponse setDefaultAddress(Long userId, Long addressId) {
        log.info("set default address called");
        log.info("fetching address");
        Address address=addressRepository.findById(addressId).orElseThrow(()->new RuntimeException("Address not found"));
        log.info("address fetched");
        address.setIsDefault(true);
        AddressResponse response=mapToResponse(addressRepository.save(address));
        log.info("set default address completed");
        return response;
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .fullName(address.getFullName())
                .id(address.getId())
                .phoneNumber(address.getPhoneNumber())
                .houseNo(address.getHouseNo())
                .street(address.getStreet())
                .landmark(address.getLandmark())
                .landmark(address.getLandmark())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .isDefault(address.getIsDefault())
                .build();
    }
}
