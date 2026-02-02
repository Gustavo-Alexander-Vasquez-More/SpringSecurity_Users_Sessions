package com.alexmore.springSecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncryptionTest {
    @Test
    public void BCryptTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode("HolaMundo");
        System.out.println(password);
        boolean check=bCryptPasswordEncoder.matches("HolaMundo",password);
        if(check){
            System.out.println("La contraseña coincide");
        }else{
            System.out.println("La contraseña no coincide");
        }
    }

}
