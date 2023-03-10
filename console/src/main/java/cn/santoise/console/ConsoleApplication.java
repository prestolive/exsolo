package cn.santoise.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wbingy
 * @date 2023/3/1
 **/
@SpringBootApplication(scanBasePackages={"cn.santoise.console","cn.santoise.batis"})
@EnableScheduling
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class,args);
    }
}
