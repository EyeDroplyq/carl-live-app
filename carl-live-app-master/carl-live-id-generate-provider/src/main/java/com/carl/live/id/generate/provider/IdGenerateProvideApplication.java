package com.carl.live.id.generate.provider;

import com.carl.live.id.generate.provider.rpc.IdGenerateRpcImp;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 20:53
 * @version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class IdGenerateProvideApplication implements CommandLineRunner {
    @Resource
    private IdGenerateRpcImp idGenerateRpcImp;
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(IdGenerateProvideApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 1800; i++) {
            Long seqId = idGenerateRpcImp.getSeqId(1);
            System.out.println(seqId);

        }
    }
}
