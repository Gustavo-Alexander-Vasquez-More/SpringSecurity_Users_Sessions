package com.alexmore.springSecurity.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = "Username cannot be empty" )
    private String username;
    @NotEmpty(message = "Password cannot be empty" )
    private String password;
    @NotEmpty(message = "Email cannot be empty" )
    private String email;
    @Pattern(regexp = "ADMIN|USER|RESELLER|SUPERADMIN",
            message = "Role must be one of the following: ADMIN, USER, RESELLER, SUPERADMIN")
    @NotEmpty(message = "Role cannot be empty" )
    private String role;
}
