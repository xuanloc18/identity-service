package com.cxl.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

     String id;
     String userName;
     String passWord;
     String firstName;
     String lastName;
     LocalDate dbo;
     @ManyToMany
     Set<Role> roles;


}
