package so.a56204082;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EulerCalculator2 {
	
    public static BigDecimal calculate(int k, int numberOfThreads) {
    	ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
    	List<Future<BigDecimal>> futures = new ArrayList<>(numberOfThreads);

    	BigDecimal denominator = BigDecimal.ONE;
    	for (int j = 1; j <= k; j++) {
    		denominator = denominator.multiply(BigDecimal.valueOf(4 * j * j + 2 * j));
    		Future<BigDecimal> future = executor.submit(computePartialSum(j, denominator));
    		futures.add(future);
    	}
    	
		return futures.stream().parallel()
			.map(getPartialSumComputed())
			.reduce(BigDecimal.ZERO, BigDecimal::add).add(BigDecimal.valueOf(3));
    }
    
	public static Callable<BigDecimal> computePartialSum(int curr, BigDecimal denominator) {
		return () -> {
			long numerator = 3 - 4 * curr * curr;
			return BigDecimal.valueOf(numerator).divide(denominator, 1000, RoundingMode.HALF_EVEN);
		};
	}

	private static Function<Future<BigDecimal>, BigDecimal> getPartialSumComputed() {
		return future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		};
	}

    public static void calculateAndLog(int k, int numberOfThreads, BiFunction<Integer, Integer, BigDecimal> calculator) throws Exception {
    	long startTime = System.currentTimeMillis();
    	BigDecimal result = calculate(k, numberOfThreads);
    	long duration = System.currentTimeMillis() - startTime;
    	System.out.println(String.format("k: %d, threads: %d, duration: %d ms, result: %s", k, numberOfThreads, duration, result));
//		System.gc();
    }
    
    public static void main(String[] args) throws Exception {
    	for (int idx = 1; idx <= 12; idx++) { 
//    		calculateAndLog(30000, idx, EulerCalculator::calculate);
    		calculateAndLog(50000, idx, EulerCalculator2::calculate);
        	System.out.println();
    	}
	}
}
