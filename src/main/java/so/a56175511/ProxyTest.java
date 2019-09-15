package so.a56175511;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

	static class MyProxiedSavedInvocationHandler extends ProxiedSavedInvocationHandler {
		public MyProxiedSavedInvocationHandler(Object proxied) {
			super(proxied);
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        	if (!method.getName().equals("equals"))
        		return method.invoke(proxied, args);
        	
            Object other = ProxiedSavedInvocationHandler.getProxied(args[0]);
            System.out.println("====");
            System.out.println("\tRunning 'equals' inside proxy with:");
            System.out.println("\tthis: " + proxied);
            System.out.println("\tother: " + other);
            System.out.println("====");
			return proxied.equals(other);
        }
	}
	
    static abstract class ProxiedSavedInvocationHandler implements InvocationHandler {
        public static Object getProxied(Object proxy) {
        	if (!Proxy.isProxyClass(proxy.getClass())) 
        		return null;
        	
            InvocationHandler handler = Proxy.getInvocationHandler(proxy);
			return (handler instanceof ProxiedSavedInvocationHandler) ? 
					((ProxiedSavedInvocationHandler)handler).proxied : null;
        }

    	protected final Object proxied;
    	
		public ProxiedSavedInvocationHandler(Object proxied) { 
			this.proxied = proxied; 
		}
		
		public Object getProxied() {
			return proxied;
		}

		public Object createProxy() {
			Class<? extends Object> clazz = proxied.getClass();
			return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
		}
    }

    // TO TEST EDGE SCENARIONS
	private static Object createProxy(Class<? extends Object> clazz, InvocationHandler handler) {
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
	}

	// MAIN
    public static void main(String[] args) {
    	// EDGE SCENARIOS
        Object proxiedFromNotEnhancedProxy = 
        		ProxiedSavedInvocationHandler.getProxied(createProxy(Object.class, (p, m, a) -> null));
        Object proxiedFromNotAProxy = 
        		ProxiedSavedInvocationHandler.getProxied(new Object());
        System.out.println("proxied from NOT ENHANCED PROXY: " + proxiedFromNotEnhancedProxy);
        System.out.println("proxied from NOT A PROXY: " + proxiedFromNotAProxy);
        System.out.println();
    	
        // FUNCTIONALITY DESIRED
    	Object target = new Object();
    	ProxiedSavedInvocationHandler handler = new MyProxiedSavedInvocationHandler(target); 

    	Object proxy = handler.createProxy();
		Object proxied1 = ProxiedSavedInvocationHandler.getProxied(proxy);
		Object proxied2 = handler.getProxied();
        
        System.out.println("target: " + target);
        System.out.println("proxied1: " + proxied1);
        System.out.println("target == proxied1: " + (target == proxied1));
		System.out.println("proxy.equals(proxy): " + proxy.equals(proxy));
    }
}