package so.a56264144;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.beanutils.BeanUtils;

import lombok.Data;

public class DynamicBeanUtils {
	
	static Map<String, String> createFieldNameValueMap(String headerLine, String valuesLine) {
		String[] fieldNames = headerLine.split("\t");
		String[] fieldValues = valuesLine.split("\t");
		
		return IntStream.range(0, fieldNames.length)
			.mapToObj(Integer::new)
			.collect(Collectors.toMap(idx -> fieldNames[idx], idx -> fieldValues[idx]));
	}
	
	public static void main(String[] args) {
		String headerLine = "booleanValue\tintValue\tstringValue\tdoubleValue\totherValue";
		String valuesLine = "true\t12\tthis bean will be populated\t22.44\ttest string!!!";
		
		Object target = new MyBean();
		try {
			BeanUtils.populate(target, createFieldNameValueMap(headerLine, valuesLine));
		} catch (IllegalAccessException | InvocationTargetException e) {
			// HANDLE EXCEPTIONS!
		}

		System.out.println(target);
	}

	@Data
	public static class MyBean {
		private String stringValue;
		private double doubleValue;
		private int intValue;
		private boolean booleanValue;
		private String otherValue;
	}
}
