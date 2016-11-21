package org.xuqi.geophysics.batch;

import com.google.common.base.Splitter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class Main {
    private static Log logger = LogFactory.getLog(Main.class);
    private final static String CONFIGS_CMD = "sc";

    private final static String SERVICE_NAME = "serviceName";

    private static Splitter serviceSplitter = Splitter.on(",");

    public static void main(String[] args) throws Exception {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext("org.xuqi.geophysics.batch");
            Map<String, Object> implServices = context.getBeansWithAnnotation(Service.class);
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addOption(CONFIGS_CMD, true, "service configurations");

            CommandLine commandLine = parser.parse(options, args);
            if (!commandLine.hasOption(CONFIGS_CMD)) {
                throw new Exception("service configurations can not be null");
            }
            Iterable<String> scList = serviceSplitter.omitEmptyStrings().split(commandLine.getOptionValue(CONFIGS_CMD));
            for (String sc : scList) {
                Properties properties = new Properties();
                String s = String.format("%s%s", "/", sc);
                InputStream inputStream = Main.class.getResourceAsStream(s);
                properties.load(inputStream);
                if (properties.containsKey(SERVICE_NAME)) {
                    if (implServices.containsKey(properties.get(SERVICE_NAME))) {
                        BatchService service = (BatchService) implServices.get(properties.get(SERVICE_NAME));
                        service.doService(properties);
                        System.exit(0);
                    } else {
                        logger.error(String.format("do not find the service %s in implServices %s",
                                properties.get(SERVICE_NAME), implServices.keySet()));
                    }
                } else {
                    logger.error(String.format("current config %s does not contain %s", sc, SERVICE_NAME));
                }
            }
            logger.info( "================ batch run successfully ========");
            System.exit(0);
        } catch (Throwable t) {
            logger.error("================ batch run fail ================", t);
            System.exit(-1);
        }
    }
}
