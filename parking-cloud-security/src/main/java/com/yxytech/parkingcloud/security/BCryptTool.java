package com.yxytech.parkingcloud.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTool {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("Admin@yxy"));
        System.out.println(encoder.encode("111111"));
    }
}
