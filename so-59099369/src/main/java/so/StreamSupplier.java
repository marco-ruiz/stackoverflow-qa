package so;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Supplier;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

public class StreamSupplier implements Supplier<Flux<?>> {

	private static final String SPRING_CLOUD_STREAM_SENDTO_DESTINATION =
			"spring.cloud.stream.sendto.destination";

	public static <T> Message<?> createMessage(T payload, String destination) {
		MessageBuilder<T> builder = MessageBuilder.withPayload(payload);
		if (destination != null && !destination.isEmpty())
			builder.setHeader(SPRING_CLOUD_STREAM_SENDTO_DESTINATION, destination);
		return builder.build();
	}

	private String defaultDestination;
	private EmitterProcessor<? super Object> processor = EmitterProcessor.create();

	public StreamSupplier() {
		this(null);
	}

	public StreamSupplier(String defaultDestination) {
		this.defaultDestination = defaultDestination;
	}

	// SEND APIs

	public <T> Message<?> sendMessage(T payload) {
		return sendMessage(payload, defaultDestination);
	}

	public <T> Message<?> sendMessage(T payload, String destination) {
		return sendBody(createMessage(payload, destination));
	}

	public <T> T sendBody(T body) {
		processor.onNext(body);
		return body;
	}

	/**
	 * Returns {@link EmitterProcessor} used internally to programmatically publish messages onto
	 * the output binding associated with this {@link Supplier}. Such programmatic publications
	 * are available through the {@code sendXXX} API methods available in this class.
	 */
	@Override
	public Flux<?> get() {
		return processor;
	}
}
