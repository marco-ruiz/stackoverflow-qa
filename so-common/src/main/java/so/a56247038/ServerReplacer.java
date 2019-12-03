package so.a56247038;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerReplacer {
	
	private static Logger logger = LoggerFactory.getLogger(ServerReplacer.class);
	
	public static void runReplacerService(int port) {
		searchOlderInstances(port);
		
		logger.debug("Server starting...");
		while (true) 
			runServer(port);
	}

	private static void searchOlderInstances(int port) {
		logger.debug("Attempting to find older instance...");
		try (Socket socket = new Socket("127.0.0.1", port)) {
			logOtherInstanceFound(socket.isConnected());
		} catch (Exception e) {
			logOtherInstanceFound(false);
		}
	}

	private static void logOtherInstanceFound(boolean otherInstanceFound) {
		logger.debug(otherInstanceFound ? 
			"FOUND ANOTHER INSTANCE RUNNING! It has been signaled to shut down." : 
			"No older instance found.");
	}

	private static void runServer(int port) {
		try (ServerSocket serverSocket = new ServerSocket(port, 1)) {
			logger.debug("Server started!");
			try (Socket clientSocket = serverSocket.accept()) {
				logger.debug("Signal to shutdown received. Shutting down.");
				System.exit(0);
			}
		} catch (IOException e) {
			logger.debug("The other application is still shutting down...");
		}
	}
	
	public static void main(String[] args) {
		runReplacerService(9665);
	}
}
