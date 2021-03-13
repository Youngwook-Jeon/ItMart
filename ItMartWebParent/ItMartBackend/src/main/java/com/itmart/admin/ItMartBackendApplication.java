package com.itmart.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.itmart.itmartcommon.entity", "com.itmart.admin.user" })
public class ItMartBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItMartBackendApplication.class, args);
    }

}
