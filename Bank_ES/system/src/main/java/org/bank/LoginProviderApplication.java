package org.bank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy
@MapperScan("org.bank.mapper")
public class LoginProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginProviderApplication.class, args);
    }
}
