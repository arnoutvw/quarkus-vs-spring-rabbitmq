package nl.conspect.spring.demo.springrabbitmqdemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class SpringRabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitmqDemoApplication.class, args);
	}

}
