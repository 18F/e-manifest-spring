package gov.epa.emanifest.standalone;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedServer {
	private static final String APP_CONTEXT_PATH = "/emanifest";
	private static final String JETTY_TEMP_DIRECTORY = "jettytmp";
	private static final String ALL_PATHS = "/*";
	
	private static final Logger logger = LoggerFactory.getLogger(EmbeddedServer.class);

	private static Server server;
	
	public static final void main(String[] args) {
		try {
			int port = 8180;
			startServer(port);
		} catch (Exception e) {
			logger.error("Failed to start server.", e);
		} catch (Throwable t) {
			logger.error("Server died.", t);
		}
	}
	
	private static void startServer(int port) throws IOException,
			Exception, InterruptedException {
		server = configureServer(port);
		server.setStopAtShutdown(true);
		server.setStopTimeout(10 * 1000L);
		server.start();
    logger.debug("emanifest app started");
		server.join();
	}

	private static Server configureServer(final int port) throws IOException {
		final Server server = new Server();
		
    configureWebAppContextHandler(server);
    configureHttpConnector(server, port);
		
		return server;
	}

	private static void configureHttpConnector(final Server server,
			final int port) {
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		configureStandardServerSettings(server, http, port);
	}

	private static void configureStandardServerSettings(final Server server,
			ServerConnector connector, final int port) {
		connector.setIdleTimeout(60000);
		connector.setPort(port);
		server.addConnector(connector);
	}
	
	private static void configureWebAppContextHandler(final Server server)
			throws IOException {
		WebAppContext context = new WebAppContext();
		
		context.setServer(server);
		context.setContextPath(APP_CONTEXT_PATH);
		
		ProtectionDomain protectionDomain = EmbeddedServer.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		context.setWar(location.toExternalForm());
		server.setHandler(context);
		configureTempDirectory(context);
	}
	
	private static void configureTempDirectory(WebAppContext context) throws IOException {
		File tempDirectory = new File("/tmp", JETTY_TEMP_DIRECTORY);
    tempDirectory.mkdir();
		context.setTempDirectory(tempDirectory);
	}
}

