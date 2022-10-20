package org.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @BelongsProject:
 * @BelongsPackage: org.bank
 * @Author: lizongle
 * @CreateTime: 2022-06-15  17:31
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaServer //开启 Eureka server,接受其他微服务的注册
public class BankServiceCloudEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankServiceCloudEurekaApplication.class, args);
    }

}
