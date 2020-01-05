package so;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;

@SpringBootApplication
@EnableWebFlux
@RestController
public class EventStreamApp implements WebFluxConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EventStreamApp.class);
	}

	private FluxProcessor<Integer, Integer> processor = DirectProcessor.create();

	@PutMapping("/{number}")
	public void update(@PathVariable Integer number) {
		processor.onNext(number);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Integer> eventFlux() {
		return processor;
	}
}
