package com.carl.live.im.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 20:53
 * @version: 1.0
 */
@SpringBootApplication
public class ImServerProviderApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ImServerProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

}
