package com.example.user_service.model;

import com.example.user_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String gender;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}