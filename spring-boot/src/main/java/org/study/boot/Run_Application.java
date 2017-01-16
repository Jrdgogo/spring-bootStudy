package org.study.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
* <p>Title: Run_Application</p>
* <p>Description: </p>
* <p>Company: 苏州朗动</p> 
* @author hxh
* @date 2016年12月5日 下午8:24:45
*/
@ComponentScan(basePackages = "org.study.boot")
@EnableAutoConfiguration
public class Run_Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Run_Application.class);
        app.run(args);
    }
}
