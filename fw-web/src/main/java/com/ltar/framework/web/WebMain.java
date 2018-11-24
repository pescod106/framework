package com.ltar.framework.web;

import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/23
 * @version: 1.0.0
 */
public class WebMain {
    private static final int DEFAULT_HTTP_PORT = 8080;
    private static final int DEFAULT_THREAD_SIZE = 20;
    private static final long DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT = 3 * 1000L;

    public static void main(String[] args) throws Exception {
        int port = DEFAULT_HTTP_PORT;
        int threadSize = DEFAULT_THREAD_SIZE;
        long gracefulShutdownTimeout = DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT;

        String war = "src/main/webapp";
        if ((new File("webapp")).exists()) {
            war = "webapp";
        }

        String contextPath = "/";

        Options options = new Options();
        options.addOption("p", "port", true, "Server port, default is " + DEFAULT_HTTP_PORT);
        options.addOption("m", "max-threads", true, "Max threads, default is " + DEFAULT_THREAD_SIZE);
        options.addOption("w", "war", true, "war directory");
        options.addOption("c", "context-path", true, "context path");
        options.addOption("g", "graceful-shutdown-timeout", true, "set graceful shutdown timeout(ms), default is " + DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT);
        options.addOption("h", "help", false, "show help message");
        DefaultParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("p")) {
                port = Integer.valueOf(commandLine.getOptionValue("p"));
            }
            if (commandLine.hasOption("m")) {
                threadSize = Integer.valueOf(commandLine.getOptionValue("m"));
            }
            if (commandLine.hasOption("w")) {
                war = commandLine.getOptionValue("w");
            }
            if (commandLine.hasOption("c")) {
                contextPath = commandLine.getOptionValue("c");
            }
            if (commandLine.hasOption("g")) {
                gracefulShutdownTimeout = Integer.valueOf(commandLine.getOptionValue("g"));
            }
            if (commandLine.hasOption("h")) {
                usage(options);
            }
        } catch (ParseException e) {
            usage(options);
        }

        Server server = new Server(new QueuedThreadPool(threadSize));
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(contextPath);
        webAppContext.setWar(war);
        server.setHandler(webAppContext);
        server.setStopAtShutdown(true);
        server.setStopTimeout(gracefulShutdownTimeout);
        server.start();
        server.join();
    }

    private static void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
        System.exit(1);
    }
}
