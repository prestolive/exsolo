package cn.exsolo.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author prestolive
 * @date 2023/3/1
 **/
@SpringBootApplication(scanBasePackages={
        "cn.exsolo.console",
        "cn.exsolo.batis",
        "cn.exsolo.builder",
        "cn.exsolo.springmvcext",
})
@EnableScheduling
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class,args);
    }
}
