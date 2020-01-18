package ru.mail.dimaushenko.utils.impl;

import ru.mail.dimaushenko.utils.PropertyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.dimaushenko.constants.ErrorConstants;

import static ru.mail.dimaushenko.constants.PropertyFiles.TASK_PROPERTIES;

public class PropertyUtilTaskImpl implements PropertyUtil{

    private static PropertyUtilTaskImpl instance = null;

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private PropertyUtilTaskImpl() {
    }

    public static PropertyUtilTaskImpl getInstance() {
        if (instance == null){
            instance = new PropertyUtilTaskImpl();
        }
        return instance;
    }

    public String getProperty(String propertyName) {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(TASK_PROPERTIES)) {
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_PROP_FILE_IS_NOT_FOUND);
        }
        return properties.getProperty(propertyName);
    }

    public Integer getIntegerProperty(String propertyName) {
        return Integer.parseInt(getProperty(propertyName));
    }
    
}
