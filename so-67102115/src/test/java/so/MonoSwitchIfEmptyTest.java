package so;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoSwitchIfEmptyTest {

	@Test
	void whenRequestThrowsPrecompiledException_thenExpectDesiredStackTrace() {
		trySwitchIfEmptyWithDesiredStackTrace(createAppropriateSwitchIfEmptyMono(true), true);
	}

	@Test
	void whenRequestThrowsLiveException_thenExpectUndesiredStackTrace() {
		trySwitchIfEmptyWithDesiredStackTrace(createAppropriateSwitchIfEmptyMono(false), false);
	}

	// This is done here to force the creation of an extra stack element into the stack trace.
	// That stack element is the one that later will be checked to determine if the test passed or not.
	private Mono<Void> createAppropriateSwitchIfEmptyMono(boolean desiredStackTrace) {
		return desiredStackTrace ?
				Mono.error(new LoggedRuntimeException()) :
				Mono.defer(() -> Mono.error(new LoggedRuntimeException()));
	}

	private void trySwitchIfEmptyWithDesiredStackTrace(Mono<Void> switchIfEmptyMono, boolean desiredStackTrace) {
		Mono<Void> loggedError = Mono.fromRunnable(() -> System.out.println("Throwing exception..."))
				.then(switchIfEmptyMono);

		Mono<Object> testedPipeline = Mono.fromRunnable(() -> loggedBusyWork(1000))
				.switchIfEmpty(loggedError);

		StepVerifier.create(testedPipeline)
				.expectErrorMatches(e -> ((LoggedRuntimeException)e).isRelevantStackTrace() == desiredStackTrace)
				.verify();
	}

	private void loggedBusyWork(int millis) {
		long startTime = System.currentTimeMillis();
		System.out.println("Starting busy work @ " + startTime + "...");
		while (System.currentTimeMillis() - startTime < millis);
		System.out.println("End busy work @ " + System.currentTimeMillis());
	}

	static class LoggedRuntimeException extends RuntimeException {

		public LoggedRuntimeException() {
			System.out.println("Creating exception...");
			System.out.println("Stack trace: \n" + getStackTraceStr());
		}

		private String getStackTraceStr() {
			StringWriter writer = new StringWriter();
			printStackTrace(new PrintWriter(writer));
			return writer.toString();
		}

		public boolean isRelevantStackTrace() {
			return getStackTrace()[1].toString().contains(MonoSwitchIfEmptyTest.class.getName());
		}
	}
}
