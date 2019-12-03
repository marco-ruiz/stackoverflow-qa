package so;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.function.Function;
import java.util.function.Supplier;

import reactor.core.publisher.Flux;

@SpringBootApplication
@Controller
public class MyApp {

	public static void main(String[] args) {
		SpringApplication.run(MyApp.class,
				"--spring.cloud.function.definition=streamSupplierFunction;webToStreamFunction");
	}

	// Functional Web Controller
	@Bean
	public Function<String, String> webToStreamFunction() {
		return msg -> streamSupplier().sendBody(msg);
	}

	// Functional Stream Supplier
	@Bean
	public Supplier<Flux<?>> streamSupplierFunction() {
		return new StreamSupplier();
	}

	// DOUBLE REGISTRATION TO AVOID POLLABLE CONFIGURATION
	// LIMITATION OF SPRING-CLOUD-FUNCTION
	@Bean
	public StreamSupplier streamSupplier() {
		return (StreamSupplier) streamSupplierFunction();
	}
}

