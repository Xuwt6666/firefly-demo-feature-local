package com.eastcom_sw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.eastcom_sw.firefly_cloud.common.security.annotation.EnableCustomConfig;
import com.eastcom_sw.firefly_cloud.common.security.annotation.EnableFireflyFeignClients;
import com.eastcom_sw.firefly_cloud.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 应用模块
 *
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableFireflyFeignClients
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        System.out.println(
                "  ______ _          ______ _               _____ _                 _ \n" +
                " |  ____(_)        |  ____| |             / ____| |               | |\n" +
                " | |__   _ _ __ ___| |__  | |_   _ ______| |    | | ___  _   _  __| |\n" +
                " |  __| | | '__/ _ \\  __| | | | | |______| |    | |/ _ \\| | | |/ _` |\n" +
                " | |    | | | |  __/ |    | | |_| |      | |____| | (_) | |_| | (_| |\n" +
                " |_|    |_|_|  \\___|_|    |_|\\__, |       \\_____|_|\\___/ \\__,_|\\__,_|\n" +
                "                              __/ |                                  \n" +
                "                             |___/                                   ");
    }

    //冒泡排序
}
