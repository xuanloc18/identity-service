package com.cxl.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String userName;
    @Size(min = 8,message = "PASSWORD_EXCEPION")
    String passWord;
    String firstName;
    String lastName;
    LocalDate dbo;

}

