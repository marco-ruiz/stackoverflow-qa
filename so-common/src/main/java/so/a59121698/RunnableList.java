package so.a59121698;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RunnableList {
	
	private List<Invocable<?>> invocables = new ArrayList<Invocable<?>>();
	
	public <T> void add(T target, Consumer<T> invocation) {
		invocables.add(new Invocable<T>(target, invocation));
	}
	
	public void run() {
		invocables.forEach(Invocable::runInvocation);
	}

	static class Invocable<T> {
		private final T target;
		private final Consumer<T> invocation;
		
		public Invocable(T target, Consumer<T> invocation) {
			this.target = target;
			this.invocation = invocation;
		}
		
		public void runInvocation() {
			invocation.accept(target);
		}
	}
	
	// TEST
	
	public static void main(String[] args) {
		RunnableList runnableList = new RunnableList();
		runnableList.add(new ClassA(), o -> o.run1("hello from A1"));
		runnableList.add(new ClassB(), o -> o.run1("hello from B1"));
		runnableList.add(new ClassC(), o -> o.run1("hello from C1"));

		runnableList.add(new ClassA(), ClassA::run2);
		runnableList.add(new ClassB(), ClassB::run2);
		runnableList.add(new ClassC(), ClassC::run2);
		runnableList.run();
	}
	
	static class ClassA {
		public void run1(String msg) {
			System.out.println("A.run1: " + msg);
		}
		public void run2() { System.out.println("A.run2"); }
	}
	static class ClassB {
		public void run1(String msg) {
			System.out.println("B.run1: " + msg);
		}
		public void run2() { System.out.println("B.run2"); }
	}
	static class ClassC {
		public void run1(String msg) {
			System.out.println("C.run1: " + msg);
		}
		public void run2() { System.out.println("C.run2"); }
	}
}
