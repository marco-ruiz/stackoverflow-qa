package so.a56138936;

public abstract class Animal {

	public abstract String hello();

	static class Dog extends Animal {
		
	    @Override
	    public String hello() {
	        return "I'm Dog";
	    }
	}
	
	static class Wolf extends Animal {
		
	    @Override
	    public String hello() {
	        return "I'm Wolf";
	    }
	}
	
    static class AnimalWrapper extends Animal {
    	
    	private Animal delegate = new Dog();
    	
    	public void delegateTo(String className) throws Exception {
    		this.delegate = (Animal) Class.forName(className).newInstance();
    	}

		@Override
		public String hello() {
			return delegate.hello();
		}
    }
    
    public static void main(String[] args) throws Exception {
    	AnimalWrapper animal = new AnimalWrapper();
    	System.out.println("Animal says: " + animal.hello());
    	animal.delegateTo(Wolf.class.getName());
    	System.out.println("Animal says: " + animal.hello());
    	animal.delegateTo(Dog.class.getName());
    	System.out.println("Animal says: " + animal.hello());
    }
}
