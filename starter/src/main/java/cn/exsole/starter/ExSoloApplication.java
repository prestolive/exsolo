package cn.exsole.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author prestolive
 * @date 2023/3/1
 **/
@SpringBootApplication(scanBasePackages={
        "cn.exsolo.console",
        "cn.exsolo.batis",
        "cn.exsolo.kit",
        "cn.exsolo.springmvcext",
})
@ServletComponentScan(basePackages = "cn.exsolo")
@EnableScheduling
public class ExSoloApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExSoloApplication.class,args);
    }
}
