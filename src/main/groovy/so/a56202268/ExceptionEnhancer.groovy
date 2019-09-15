package so.a56202268
import java.time.LocalDateTime

class ExceptionEnhancer {
	static void main(String[] args) {
		def logger = { println "${LocalDateTime.now()} - Context Data = [$it]" }
		doSomethingWithContextData logger
	}

	private static void doSomethingWithContextData(Closure contextDataHandler) throws IOException {
		try {
			throwsEnhancedException();
		} catch (IOException e) {
			// RETRIEVE `contextData` FROM `e` OR NULL IF THE PROPERTY DO NOT EXIST
			def contextData = e.hasProperty('contextData')?.getProperty(e)

			// DO SOMETHING WITH `contextData`
			contextDataHandler(contextData)
		}
	}
	
	private static void throwsEnhancedException() throws IOException {
		try {
			throwsBasicException()
		} catch (IOException e) {
			e.metaClass.contextData = "My context data"
			throw e;
		}
	}
	
	public static void throwsBasicException() throws IOException {
		throw new IOException();
	}
}
