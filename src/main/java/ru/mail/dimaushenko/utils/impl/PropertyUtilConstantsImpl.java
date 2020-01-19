package ru.mail.dimaushenko.utils.impl;

import ru.mail.dimaushenko.utils.PropertyUtil;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.mail.dimaushenko.constants.ErrorConstants;

import static ru.mail.dimaushenko.constants.PropertyFiles.CONSTANTS_PROPERTY;

public class PropertyUtilConstantsImpl implements PropertyUtil {

    private static PropertyUtilConstantsImpl instance = null;

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private PropertyUtilConstantsImpl() {
    }

    public static PropertyUtilConstantsImpl getInstance() {
        if (instance == null) {
            instance = new PropertyUtilConstantsImpl();
        }
        return instance;
    }

    @Override
    public String getProperty(String propertyName) {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONSTANTS_PROPERTY)) {
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ErrorConstants.ERR_PROP_FILE_IS_NOT_FOUND);
        }
        return properties.getProperty(propertyName);
    }

    @Override
    public Integer getIntegerProperty(String propertyName) {
        return Integer.parseInt(getProperty(propertyName));
    }

}
