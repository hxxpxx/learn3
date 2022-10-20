package org.bank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: PACKAGE_NAME
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy
@MapperScan("org.bank.mapper")
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class);
    }
}
