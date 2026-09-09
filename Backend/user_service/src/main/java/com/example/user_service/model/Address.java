package com.example.user_service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDefault=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}