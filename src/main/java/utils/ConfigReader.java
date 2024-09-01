package utils;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public Properties initializeProperties() {
        Properties prop = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                logger.error("Sorry, unable to find config.properties");
                return prop;
            }

            // Load the properties file from classpath
            logger.info("Loading properties from config/config.properties");
            prop.load(input);

        } catch (Exception e) {
            logger.error("Failed to load properties file", e);
        }

        return prop;
    }
}
